package org.bgu.communication.reactor;
 
import org.apache.log4j.Logger;
import org.bgu.communication.protocol.AsyncServerProtocol;
import org.bgu.communication.protocol.IStompOutput;
import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.communication.stomp.StompTokenizer;
import org.bgu.communication.tokenizer.MessageTokenizer;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.util.Vector;

/**
 * Handles messages from clients
 */
public class ConnectionHandler<T extends StompFrame> implements IStompOutput {
    private static final int BUFFER_SIZE = 1024;
    private static final Logger logger = Logger.getLogger("edu.spl.reactor");
    
    private ProtocolTask<T> _task = null;
    
    protected final SocketChannel _sChannel;
    protected final ReactorData<T> _data;
    protected final AsyncServerProtocol<T> _protocol;
    protected final MessageTokenizer<T> _tokenizer;
 
    protected final SelectionKey _skey;
    protected Vector<ByteBuffer> _outData = new Vector<ByteBuffer>();
    
    /**
     * Creates a new ConnectionHandler object
     * 
     * @param sChannel
     *            the SocketChannel of the client
     * @param data
     *            a reference to a ReactorData object
     */
    private ConnectionHandler(SocketChannel sChannel, ReactorData<T> data, SelectionKey key) {
        _sChannel = sChannel;
        _data = data;
        _protocol = _data.getProtocolMaker().create();
       
        _tokenizer = _data.getTokenizerMaker().create();
        _skey = key;
    }
 
    // make sure 'this' does not escape before the object is fully constructed!
    private void initialize() {
        _skey.attach(this);
        _task = new ProtocolTask<T>(_protocol, _tokenizer, this);
    }
 
    public static <T extends StompFrame> ConnectionHandler<T> create(SocketChannel sChannel, ReactorData<T> data, SelectionKey key) {
        ConnectionHandler<T> handler = new ConnectionHandler<T>(sChannel, data, key);
        ((StompProtocol)handler._protocol).init(handler);
        handler.initialize();
        return handler;
    }
 
    public synchronized void addOutData(ByteBuffer buf) {
        _outData.add(buf);
        switchToReadWriteMode();
    }
 
    private void closeConnection() {
        // remove from the selector.
        _skey.cancel();
        try {
            _sChannel.close();
        } catch (IOException ignored) {
            ignored = null;
        }
    }
 
    /**
     * Reads incoming data from the client:
     * <UL>
     * <LI>Reads some bytes from the SocketChannel
     * <LI>create a protocolTask, to process this data, possibly generating an
     * answer
     * <LI>Inserts the Task to the ThreadPool
     * </UL>
     * 
     * @throws
     * 
     * @throws java.io.IOException
     *             in case of an IOException during reading
     */
    public void read() {
        // do not read if protocol has terminated. only write of pending data is
        // allowed
        if (_protocol.shouldClose()) {
            return;
        }
 
        SocketAddress address = _sChannel.socket().getRemoteSocketAddress();
        logger.trace("Reading from " + address);
 
        ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
        int numBytesRead = 0;
        try {
            numBytesRead = _sChannel.read(buf);
        } catch (IOException e) {
            numBytesRead = -1;
        }
        // is the channel closed??
        if (numBytesRead == -1) {
            // No more bytes can be read from the channel
            logger.trace("client on " + address + " has disconnected");
            closeConnection();
            // tell the protocol that the connection terminated.
            _protocol.connectionTerminated();
            return;
        }
 
        //add the buffer to the protocol task
        buf.flip();
        _task.addBytes(buf);
        // add the protocol task to the reactor
        _data.getExecutor().execute(_task);
    }
 
    /**
     * attempts to send data to the client<br/>
     * if all the data has been successfully sent, the ConnectionHandler will
     * automatically switch to read only mode, otherwise it'll stay in it's
     * current mode (which is read / write).
     * 
     * @throws java.io.IOException
     *             if the write operation fails
     * @throws java.nio.channels.ClosedChannelException
     *             if the channel have been closed while registering to the Selector
     */
    public synchronized void write() {
        if (_outData.size() == 0) {
            // if nothing left in the output string, go back to read mode
            switchToReadOnlyMode();
            return;
        }
        // if there is something to send
        ByteBuffer buf = _outData.remove(0);
        if (buf.remaining() != 0) {
            try {
                _sChannel.write(buf);
            } catch (IOException e) {
                // this should never happen.
                e.printStackTrace();
            }
            // check if the buffer contains more data
            if (buf.remaining() != 0) {
                _outData.add(0, buf);
            }
        }
        // check if the protocol indicated close.
        if (_protocol.shouldClose()) {
            switchToWriteOnlyMode();
            if (buf.remaining() == 0) {
                closeConnection();
                SocketAddress address = _sChannel.socket().getRemoteSocketAddress();
                logger.trace("disconnecting client on " + address);
            }
        }
    }
 
    /**
     * switches the handler to read / write TODO Auto-generated catch blockmode
     * 
     * @throws java.nio.channels.ClosedChannelException
     *             if the channel is closed
     */
    public void switchToReadWriteMode() {
        _skey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        _data.getSelector().wakeup();
    }
 
    /**
     * switches the handler to read only mode
     * 
     * @throws java.nio.channels.ClosedChannelException
     *             if the channel is closed
     */
    public void switchToReadOnlyMode() {
        _skey.interestOps(SelectionKey.OP_READ);
        _data.getSelector().wakeup();
    }
 
    /**
     * switches the handler to write only mode
     * 
     * @throws java.nio.channels.ClosedChannelException
     *             if the channel is closed
     */
    public void switchToWriteOnlyMode() {
        _skey.interestOps(SelectionKey.OP_WRITE);
        _data.getSelector().wakeup();
    }
    
    /*
     * @see protocol.IStompOutput#write(stomp.StompFrame)
     */
    @Override
    public void write(StompFrame frame){
    	ByteBuffer bytes;
		try {
			bytes = ((StompTokenizer)_tokenizer).getBytesForMessage(frame);
			this.addOutData(bytes);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
    }
}