package org.bgu.communication.stomp;

/*
 * represents the stomp receipt
 */
public class StompReceipt extends StompServerFrame {

	public StompReceipt(String reciept) {
		super("RECEIPT");
		addHeader("receipt", reciept);
	}

}
