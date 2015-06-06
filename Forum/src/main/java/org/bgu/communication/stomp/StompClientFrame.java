package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;

/*
 * represents a frame that comes from the client
 * implements the Visitor pattern as visited
 */
public abstract class StompClientFrame extends StompFrame {

	/*
	 * constructor
	 */
	public StompClientFrame(String command) {
		super(command);
	}
	
	/*
	 * double-dispatch method
	 * accept must be abstract to enforce calling it in the concrete class
	 */
	public abstract StompFrame acceptProcess(StompProtocol processor);

}