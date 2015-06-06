package org.bgu.communication.stomp;


import org.bgu.communication.protocol.StompProtocol;

/*
 * represents the stomp unsubscribe frame
 */
public class StompUnsubscribe extends StompClientFrame {
	public static final String COMMAND_NAME = "UNSUBSCRIBE";
	
	public StompUnsubscribe(String id) {
		super(COMMAND_NAME);
		addHeader("id", id);
	}
	
	@Override
	public StompFrame acceptProcess(StompProtocol processor) {
		return processor.processMessage(this);
	}
	
	public String getId(){
		return getHeader("id");
	}

	public String getDestination() {
		return getHeader("destination");
	}
}