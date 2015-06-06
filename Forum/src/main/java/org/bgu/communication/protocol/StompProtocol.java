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
		_model = ForumProtocol.instance();
		_output = null;
	}
	
	protected ForumProtocol _model;
	private String _relatedUser;
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
	public synchronized void SendMessage(StompMessageFrame frame){
		_output.write(frame);
	}
	
	/*
	 * gets the user that is binded to the protocol
	 */
	public String getUser(){
		return _relatedUser;
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
		_model.performTermination(_relatedUser);
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
	
	/*
	 * handles connect frame
	 */
	public StompFrame processMessage(StompConnect msg){
		_relatedUser = msg.getLogin();
		return _model.PerformLogin(msg, this);
	}
	
	/*
	 * handles disconnect frame
	 */
	public StompFrame processMessage(StompDisconnect msg){
		return _model.performLogout(msg, this);
	}
	
	/*
	 * handles subscribe frame
	 */
	public StompFrame processMessage(StompSubscribe msg){
		return _model.performFollow(msg);
	}
	
	/*
	 * handles send frame
	 */
	public StompFrame processMessage(StompSend msg){
		_model.performSend(msg, this);
		return null;
	}
	
	/*
	 * handles unsubscribe frame
	 */
	public StompFrame processMessage(StompUnsubscribe msg){
		return _model.performUnFollow(msg, this.getUser());
	}
}