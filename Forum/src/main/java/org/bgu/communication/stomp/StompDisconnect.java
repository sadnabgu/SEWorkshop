package org.bgu.communication.stomp;


import org.bgu.communication.protocol.StompProtocol;

/*
 * represents the stomp disconnect
 */
public class StompDisconnect extends StompClientFrame {
	public static final String COMMAND_NAME = "DISCONNECT";
	
	public StompDisconnect(String receipt) {
		super(COMMAND_NAME);
		addHeader("receipt", receipt);
	}
	
	/*
	 * @see stomp.StompClientFrame#acceptProcess(protocol.StompProtocol)
	 */
	@Override
	public StompFrame acceptProcess(StompProtocol processor) {
		return processor.processMessage(this);
	}
	
	public String getReceipt(){
		return getHeader("receipt");
	}
}