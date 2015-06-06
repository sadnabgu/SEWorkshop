package org.bgu.communication.stomp;

/*
 * represents the stop connected frame
 */
public class StompConnected extends StompServerFrame {

	public StompConnected(String content) {
		super("CONNECTED");
		setContent(content);
	}

}
