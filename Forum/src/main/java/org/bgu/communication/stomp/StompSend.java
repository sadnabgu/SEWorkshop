package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;

/*
 * represents the stomp send
 */
public class StompSend extends StompClientFrame {
	public static final String COMMAND_NAME = "SEND";
	
	public StompSend(String destination, String content) {
		super(COMMAND_NAME);
		addHeader("destination", destination);
		setContent(content);
	}
	
	@Override
	public StompFrame acceptProcess(StompProtocol processor) {
		return processor.processMessage(this);
	}
	
	public String getDestination(){
		return getHeader("destination");
	}
}