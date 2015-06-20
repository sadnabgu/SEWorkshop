package org.bgu.communication.protocol;

/*
 * Represents a forum ... TODO
 * singleton
 */
public class ForumProtocol {
    /*
    TODO: USE THIS FOR PUSH NOTIFICATIONS
	private void forwardMessage(StompMessageFrame multicast, String follower) {
		StompProtocol session = _usersToSessions.get(follower);
		if (session != null){
			session.SendMessage(multicast);
		}
		else{
			_pendingMessages.get(follower).addLast(multicast);
		}
	}
	*/

	/*
	 * preparing the model for terminating a connection
	 */
	public synchronized void performTermination(String user) {

	}
}