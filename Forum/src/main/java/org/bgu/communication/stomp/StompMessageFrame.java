package org.bgu.communication.stomp;

/*
 * represents the stomp message frame
 */
public class StompMessageFrame extends StompServerFrame {
	
	public StompMessageFrame(String content) {
		super("MESSAGE");
		setContent(content);
	}

}
