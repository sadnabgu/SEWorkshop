package org.bgu.communication.server;

import org.bgu.communication.protocol.AsyncServerProtocol;
import org.bgu.communication.protocol.Cancellation;
import org.bgu.communication.protocol.ServerProtocolFactory;
import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.reactor.Reactor;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.communication.stomp.StompTokenizer;
import org.bgu.communication.tokenizer.MessageTokenizer;
import org.bgu.communication.tokenizer.TokenizerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Server {
    static Reactor<StompFrame> reactor;
	/*
     * Main program, used for demonstration purposes. Create and run a
     * Reactor-based/Thread-per-client server for the tweeter protocol. 
     * "Usage: port pool_size reactor/tpc"
     */
    public static void start(String args[]) {
    	if (args.length != 3) {
            System.err.println("Usage: <port> <pool_size> <reactor/tpc>");
            System.exit(1);
        }
    	
    	try{
    		int port = Integer.parseInt(args[0]);
            int poolSize = Integer.parseInt(args[1]);
            String type = args[2].toLowerCase();
    		
	    	if (type.equals("reactor")){
				operateAsReactor(port, poolSize);
	    	}
	    	else {
	    		System.err.println("Usage: <port> <pool_size> <reactor/tpc>");
	            System.exit(1);
	    	}
    	}
    	catch(Exception ex){
    		System.err.println("Usage: <port> <pool_size> <reactor/tpc>");
    	}
    }

    public static void stop(){
        reactor.stopReactor();
        try {
            reactor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private static void operateAsReactor(int port, int poolSize) throws InterruptedException {
		System.out.println("Running as reactor");
		reactor = createReactor(port, poolSize);
		Cancellation.instance().register(reactor);
 
		Thread thread = new Thread(reactor);
		thread.start();
		thread.join();
		System.out.println("Server was shutdown");
	}
 
    private static Reactor<StompFrame> createReactor(int port, int poolSize){
        ServerProtocolFactory<StompFrame> protocolMaker = new ServerProtocolFactory<StompFrame>() {
            public AsyncServerProtocol<StompFrame> create() {
            	return new StompProtocol();
            }
        };
 
        final Charset charset = Charset.forName("UTF-8");
        TokenizerFactory<StompFrame> tokenizerMaker = new TokenizerFactory<StompFrame>() {
            public MessageTokenizer<StompFrame> create() {
                return new StompTokenizer(charset);
            }
        };
 
        Reactor<StompFrame> reactor = new Reactor<StompFrame>(port, poolSize, protocolMaker, tokenizerMaker);
        return reactor;
    }
}
