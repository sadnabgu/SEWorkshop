package org.bgu.communication.stomp;


import org.bgu.communication.protocol.StompProtocol;

/*
 * represents the stomp subscribe command
 */
public class StompSubscribe extends StompClientFrame {
	public static final String COMMAND_NAME = "SUBSCRIBE";
	
	public StompSubscribe(String destination, String id) {
		super(COMMAND_NAME);
		addHeader("destination", destination);
		addHeader("id", id);
	}
	
	@Override
	public StompFrame acceptProcess(StompProtocol processor) {
		return processor.processMessage(this);
	}
	
	public String getDestination(){
		return getHeader("destination");
	}
	
	public String getId(){
		return getHeader("id");
	}
}