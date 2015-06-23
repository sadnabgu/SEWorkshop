package org.bgu.communication.protocol;

import org.bgu.communication.stomp.StompFrame;

/*
 * represents an output that can write stomp frames
 */
public interface IStompOutput{
	public void write(StompFrame frame);
}