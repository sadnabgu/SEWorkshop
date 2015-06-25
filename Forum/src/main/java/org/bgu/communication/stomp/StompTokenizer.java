package org.bgu.communication.stomp;
 
import org.bgu.communication.stomp.admin.*;
import org.bgu.communication.stomp.forum.*;
import org.bgu.communication.stomp.user.*;
import org.bgu.communication.tokenizer.MessageTokenizer;

import java.nio.charset.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Vector;
 
/*
 * tokenizer for stomp messages
 */
public class StompTokenizer implements MessageTokenizer<StompFrame> {
 
	private static final char ESCAPE_CHAR = '\u0000';
   private final StringBuffer _stringBuf = new StringBuffer();
   /**
     * the fifo queue, which holds data coming from the socket. Access to the
     * queue is serialized, to ensure correct processing order.
     */
   private final Vector<ByteBuffer> _buffers = new Vector<ByteBuffer>();
   private final CharsetDecoder _decoder;
   private final CharsetEncoder _encoder;
 
   public StompTokenizer(Charset charset) {
      _decoder = charset.newDecoder();
      _encoder = charset.newEncoder();
   }
 
   public StompTokenizer() {
	   this(StandardCharsets.UTF_8);
   }

/**
    * Add some bytes to the message.  
    * Bytes are converted to chars, and appended to the internal StringBuffer.
    * Complete messages can be retrieved using the nextMessage() method.
    *
    * @param bytes an array of bytes to be appended to the message.
    */
   public synchronized void addBytes(ByteBuffer bytes) {
       _buffers.add(bytes);
   }
 
   /**
    * Is there a complete message ready?.
    * @return true the next call to nextMessage() will not return null, false otherwise.
    */
   public synchronized boolean hasMessage() {
       while(_buffers.size() > 0) {
           ByteBuffer bytes = _buffers.remove(0);
           CharBuffer chars = CharBuffer.allocate(bytes.remaining());
           _decoder.decode(bytes, chars, false); // false: more bytes may follow. Any unused bytes are kept in the decoder.
           chars.flip();
           _stringBuf.append(chars);
       }
       return _stringBuf.indexOf(new String(new char[]{ ESCAPE_CHAR })) > -1;
   }
 
   /**
    * Get the next complete message if it exists, advancing the tokenizer to the next message.
    * @return the next complete message, and null if no complete message exist.
    */
   public synchronized StompFrame nextMessage() {
      String messageString = null;
      int messageEnd = _stringBuf.indexOf(new String(new char[]{ ESCAPE_CHAR }));
      if (messageEnd > -1) {
         messageString = _stringBuf.substring(0, messageEnd);
         _stringBuf.delete(0, messageEnd + 1);
      }
      
      return fromString(messageString);
   }
 
   /**
    * Convert the String message into bytes representation, taking care of encoding and framing.
    *
    * @return a ByteBuffer with the message content converted to bytes, after framing information has been added.
    */
   public ByteBuffer getBytesForMessage(StompFrame msg)  throws CharacterCodingException {
      StringBuilder builder = new StringBuilder(msg.toString());
      builder.append(ESCAPE_CHAR);
      ByteBuffer buffer = this._encoder.encode(CharBuffer.wrap(builder));
      return buffer;
   }
   
   private StompFrame fromString(String frameString){
	   int commandLength = frameString.indexOf('\n');
	   String command = frameString.substring(0, commandLength);
	   
	   int headerStart = commandLength + 1;
	   HashMap<String, String> headers = new HashMap<String, String>();
	   while (headerStart < frameString.length() && frameString.charAt(headerStart) != '\n'){
		   int headerEnd = frameString.indexOf(':', headerStart);
		   String header = frameString.substring(headerStart, headerEnd);
		   int valueEnd = frameString.indexOf('\n', headerEnd);
		   String value = frameString.substring(headerEnd + 1, valueEnd);
		   headers.put(header, value);
		   headerStart = valueEnd + 1;
	   }
	   
	   String content = "";
	   int contentStart = headerStart + 1;
	   if (contentStart < frameString.length()){
		   content = frameString.substring(contentStart);
	   }
	   
	   return buildStompFrame(command, headers, content);
   }
   
   private StompFrame buildStompFrame(String command, HashMap<String, String> headers, String content){

	   switch (command.toLowerCase()){
           case "login_member": return new LoginMember(command, headers, content);
           case "get_forums": return new GetForumsRequest(command);
           case "init_system": return new InitSystem(command, headers, content);
           case "login_admin": return new LoginAdmin(command, headers, content);
           case "create_forum": return new CreateForum(command, headers, content);
           case "login_guest": return new LoginGuest(command, headers, content);
           case "remove_forum": return new RemoveForum(command, headers, content);
           case "add_new_sub_forum": return new CreateSubForum(command, headers, content);
           case "remove_sub_forum": return new RemoveSubForum(command, headers, content);
           case "add_new_thread": return new AddNewThread(command, headers, content);
           case "remove_message": return new RemoveMessage(command, headers, content);
           case "post_comment": return new PostNewMessage(command, headers, content);
           case "edit_message": return new EditMessage(command, headers, content);
           case "add_moderator": return new AddModerator(command, headers, content);
           case "add_friend": return new AddFriend(command, headers, content);
           case "remove_friend": return new RemoveFriend(command, headers, content);
           case "register": return new Register(command, headers, content);
           case "is_system_initialized": return new IsSystemInitialized(command, headers, content);
           case "get_sub_forums": return new GetSubForums(command, headers, content);    //TODO - need to fix
           case "log_out_member": return new LogOutMember(command, headers, content);
           case "get_threads": return new GetAllThreads(command, headers, content);
	       default: return null;
	   }
   }
}












