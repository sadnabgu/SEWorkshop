package org.bgu.communication.stomp;

/*
 * represents the error frame
 */
public class StompError extends StompServerFrame {

	public StompError(String response, String content) {
		super("ERROR");
		addHeader("response", response);
		setContent(content);
	}

}
