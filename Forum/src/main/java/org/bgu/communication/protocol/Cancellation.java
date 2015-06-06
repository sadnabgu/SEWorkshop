package org.bgu.communication.protocol;

import java.io.Closeable;
import java.io.IOException;
import java.util.Stack;

/*
 * Class that once cancellation is required invokes the event for cancelling all registered entities
 */
public class Cancellation {
	private static Object _sync = new Object();
	private static Cancellation _instance = null;
	
	private Stack<Closeable> _listeners;
	
	private Cancellation() {
		_listeners = new Stack<Closeable>();
	}
	
	/*
	 * singleton instance
	 */
	public static Cancellation instance(){
		if (_instance == null){
			synchronized (_sync) {
				if (_instance == null){
					_instance = new Cancellation();
				}
			}
		}
		return _instance;
	}
	
	/*
	 * registers an entity for future cancellation
	 */
	public void register(Closeable listener){
		_listeners.add(listener);
	}
	
	/*
	 * canceling all registered entities 
	 */
	public void cancel() throws IOException{
		for(Closeable listener: _listeners){
			listener.close();
		}
	}
}
