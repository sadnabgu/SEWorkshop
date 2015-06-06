package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;

/*
 * represents the connect frame
 */
public class StompConnect extends StompClientFrame {
	public static final String COMMAND_NAME = "CONNECT";
	
	public StompConnect(String version, String host, String user, String password) {
		super(COMMAND_NAME);
		addHeader("accept-version", version);
		addHeader("host", host);
		addHeader("login", user);
		addHeader("passcode", password);
	}

	/*
	 * @see stomp.StompClientFrame#acceptProcess(protocol.StompProtocol)
	 */
	@Override
	public StompFrame acceptProcess(StompProtocol processor) {
		return processor.processMessage(this);
	}
	
	public String getLogin(){
		return getHeader("login");
	}
	
	public String getPasscode(){
		return getHeader("passcode");
	}
}