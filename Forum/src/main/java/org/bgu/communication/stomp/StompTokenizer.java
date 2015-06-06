package org.bgu.communication.stomp;
 
import org.bgu.communication.tokenizer.MessageTokenizer;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharacterCodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Vector;
 
/*
 * tokenizer for stomp messages
 */
public class StompTokenizer implements MessageTokenizer<StompFrame> {
 
	private static final char ESCAPE_CHAR = '\u0000';
   private final StringBuffer _stringBuf = new StringBuffer();
   /**
     * the fifo queue, which holds data coming from the socket. Access to the
     * queue is serialized, to ensure correct processing order.
     */
   private final Vector<ByteBuffer> _buffers = new Vector<ByteBuffer>();
   private final CharsetDecoder _decoder;
   private final CharsetEncoder _encoder;
 
   public StompTokenizer(Charset charset) {
      _decoder = charset.newDecoder();
      _encoder = charset.newEncoder();
   }
 
   public StompTokenizer() {
	   this(Charset.defaultCharset());
   }

/**
    * Add some bytes to the message.  
    * Bytes are converted to chars, and appended to the internal StringBuffer.
    * Complete messages can be retrieved using the nextMessage() method.
    *
    * @param bytes an array of bytes to be appended to the message.
    */
   public synchronized void addBytes(ByteBuffer bytes) {
       _buffers.add(bytes);
   }
 
   /**
    * Is there a complete message ready?.
    * @return true the next call to nextMessage() will not return null, false otherwise.
    */
   public synchronized boolean hasMessage() {
       while(_buffers.size() > 0) {
           ByteBuffer bytes = _buffers.remove(0);
           CharBuffer chars = CharBuffer.allocate(bytes.remaining());
           _decoder.decode(bytes, chars, false); // false: more bytes may follow. Any unused bytes are kept in the decoder.
           chars.flip();
           _stringBuf.append(chars);
       }
       return _stringBuf.indexOf(new String(new char[]{ ESCAPE_CHAR })) > -1;
   }
 
   /**
    * Get the next complete message if it exists, advancing the tokenizer to the next message.
    * @return the next complete message, and null if no complete message exist.
    */
   public synchronized StompFrame nextMessage() {
      String messageString = null;
      int messageEnd = _stringBuf.indexOf(new String(new char[]{ ESCAPE_CHAR }));
      if (messageEnd > -1) {
         messageString = _stringBuf.substring(0, messageEnd);
         _stringBuf.delete(0, messageEnd + 1);
      }
      
      return fromString(messageString);
   }
 
   /**
    * Convert the String message into bytes representation, taking care of encoding and framing.
    *
    * @return a ByteBuffer with the message content converted to bytes, after framing information has been added.
    */
   public ByteBuffer getBytesForMessage(StompFrame msg)  throws CharacterCodingException {
      StringBuilder builder = new StringBuilder(msg.toString());
      builder.append(ESCAPE_CHAR);
      ByteBuffer buffer = this._encoder.encode(CharBuffer.wrap(builder));
      return buffer;
   }
   
   private StompFrame fromString(String frameString){
	   int commandLength = frameString.indexOf('\n');
	   String command = frameString.substring(0, commandLength);
	   
	   int headerStart = commandLength + 1;
	   HashMap<String, String> headers = new HashMap<String, String>();
	   while (headerStart < frameString.length() && frameString.charAt(headerStart) != '\n'){
		   int headerEnd = frameString.indexOf(':', headerStart);
		   String header = frameString.substring(headerStart, headerEnd);
		   int valueEnd = frameString.indexOf('\n', headerEnd);
		   String value = frameString.substring(headerEnd + 1, valueEnd);
		   headers.put(header, value);
		   headerStart = valueEnd + 1;
	   }
	   
	   String content = "";
	   int contentStart = headerStart + 1;
	   if (contentStart < frameString.length()){
		   content = frameString.substring(contentStart);
	   }
	   
	   return buildStompFrame(command, headers, content);
   }
   
   private StompFrame buildStompFrame(String command, HashMap<String, String> headers, String content){
	   switch (command){
	   case StompConnect.COMMAND_NAME:
		   return buildStompConnect(headers, content);
	   case StompDisconnect.COMMAND_NAME:
		   return buildStompDisconnect(headers, content);
	   case StompSend.COMMAND_NAME:
		   return buildStompSend(headers, content);
	   case StompSubscribe.COMMAND_NAME:
		   return buildStompSubscribe(headers, content);
	   case StompUnsubscribe.COMMAND_NAME:
		   return buildStompUnsubscribe(headers, content);
	   default:
		   return null;
	   }
   }
   
   private StompConnect buildStompConnect(HashMap<String, String> headers, String content){
	   String version = takeHeader(headers, "accept-version");
	   String host = takeHeader(headers, "host");
	   String user = takeHeader(headers, "login");
	   String password = takeHeader(headers, "passcode");
	   
	   StompConnect frame = new StompConnect(version, host, user, password);
	   frame.addHeaders(headers);
	   
	   return frame;
   }
   
   private StompDisconnect buildStompDisconnect(HashMap<String, String> headers, String content){
	   String receipt = takeHeader(headers, "reciept");
	   
	   StompDisconnect frame = new StompDisconnect(receipt);
	   frame.addHeaders(headers);
	   
	   return frame;
   }
   
   private StompSend buildStompSend(HashMap<String, String> headers, String content){
	   String destination = takeHeader(headers, "destination");
	   
	   StompSend frame = new StompSend(destination, content);
	   frame.addHeaders(headers);
	   
	   return frame;
   }
   
   private StompSubscribe buildStompSubscribe(HashMap<String, String> headers, String content){
	   String destination = takeHeader(headers, "destination");
	   String id = takeHeader(headers, "id");
	   
	   StompSubscribe frame = new StompSubscribe(destination, id);
	   frame.addHeaders(headers);
	   
	   return frame;
   }
   
   private StompUnsubscribe buildStompUnsubscribe(HashMap<String, String> headers, String content){
	   String id = takeHeader(headers, "id");
	   
	   StompUnsubscribe frame = new StompUnsubscribe(id);
	   frame.addHeaders(headers);
	   
	   return frame;
   }
   
   private String takeHeader(HashMap<String, String> headers, String key){
	   String value = headers.get(key);
	   headers.remove(key);
	   return value;
   }
}












