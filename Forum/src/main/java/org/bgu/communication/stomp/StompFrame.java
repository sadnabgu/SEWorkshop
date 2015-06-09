package org.bgu.communication.stomp;

import org.bgu.communication.tokenizer.Message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/*
 * implements a message as a stomp frame
 */
public abstract class StompFrame implements Message<StompFrame> {
	private String _command;
	private HashMap<String, String> _headers;
	private String _content;
	
	/*
	 * constructor
	 */
	protected StompFrame(String command) {
		_command = command;
		_headers = new HashMap<String, String>();
		_content = "";
	}
	
	/*
	 * gets the content
	 */
	public String getContent(){
		return _content;
	}
	
	/*
	 * sets the content
	 */
	public void setContent(String content){
		_content = content;
	}
	
	/*
	 * adds an header
	 */
	public void addHeader(String header, String value){
		_headers.put(header, value);
	}
	
	/*
	 * gets an header
	 */
	public String getHeader(String header){
		return _headers.get(header);
	}
	
	/*
	 * gets the command
	 */
	public String getCommand(){
		return _command;
	}
	
	/*
	 * adds headers
	 */
	public void addHeaders(HashMap<String, String> headers){
		Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
		while (iterator.hasNext()){
			Entry<String, String> header = iterator.next();
			_headers.put(header.getKey(), header.getValue());
		}
	}
	
	/*
	 * represents the frame textually
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(_command);
		builder.append('\n');
		
		Iterator<Entry<String, String>> iterator = _headers.entrySet().iterator();
		while (iterator.hasNext()){
			Entry<String, String> header = iterator.next();
			builder.append(header.getKey());
			builder.append(':');
			builder.append(header.getValue());
			builder.append('\n');
		}
		builder.append('\n');
		
		builder.append(_content);
		builder.append('\n');
		
		return builder.toString();
	}

    public HashMap<String, String> getHeaders() {
        return _headers;
    }
}