package org.bgu.communication.protocol;

import org.bgu.communication.stomp.*;

/**
 * a simple implementation of the server protocol interface
 * THIS IS WRONG INTERFACE BECAUSE THE FRAMES SHOULD NEVER BE ONLY ONE TYPE!!
 * THE RIGHT THING IS TO IMPLEMENT
 * implements AsyncServerProtocol<TClientMessage, TServerMessage>
 */
public class StompProtocol implements AsyncServerProtocol<StompFrame>{
	public StompProtocol() {
		_model = null;
		_output = null;
	}
	
	protected ForumProtocol _model;
	private boolean _shouldClose = false;
	private IStompOutput _output;
	private boolean _isEnd = false; 

	/*
	 * initializes the protocol by binding it to an output
	 */
	public void init(IStompOutput output){
		_output = output;
	}
	
	/*
	 * sends a frame to the output
	 */
	public synchronized void SendMessage(StompFrame frame){
		_output.write(frame);
	}
	
	/**
	 * In STOMP the user ends the session
	 */
	@Override
	public boolean isEnd(StompFrame msg) {
		return _isEnd;
	}

	@Override
	public boolean shouldClose() {
		return this._shouldClose;
	}

	@Override
	public void connectionTerminated() {
		_isEnd = true;
		_shouldClose = true;
	}

	/*
	 * implements the visitor pattern for handing different message types
	 * @see protocol.AsyncServerProtocol#processMessage(java.lang.Object)
	 */
	@Override
	public StompFrame processMessage(StompFrame msg) {
		// THIS CAST IS ONLY BECAUSE THE INTERFACE PROVIDED IS NOT GENERIC ENOUGH!!
		// ALL SERVER-CLIENT PROTOCOL NEED DOUBLE GENERIC TYPE OF REQUES-RESPONSE GENERIC TYPES
		// AND NOT ONLY ONE GENERIC TYPE OF MESSAGE
		System.out.println("Handling frame: "+msg.getCommand());
		return ((StompClientFrame)msg).acceptProcess(this);
	}
}