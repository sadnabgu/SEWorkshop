package org.bgu.communication.protocol;

import org.bgu.communication.stomp.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;


/*
 * Represents a forum ... TODO
 * singleton
 */
public class ForumProtocol {
	private static final String SERVER = "server";
	private static Object _sync = new Object();
	private static ForumProtocol _instance = null;
	private HashMap<String, String> _accounts;
	private HashMap<String, LinkedList<StompMessageFrame>> _pendingMessages;
	private HashMap<String, ArrayList<String>> _userToFollowing;
	private HashMap<String, ArrayList<String>> _topicToFollowers;
	private HashMap<String, StompProtocol> _usersToSessions;
	private HashMap<String, Integer> _mentionsOfUsers;
	private HashMap<String, Integer> _timeMentionedOthers;
	private HashMap<String, Integer> _tweetsOfUsers;
	
	private Thread _statisticsThread;
	private boolean _active;
	private int _tweetsCounter;
	private int _maxTweetsCounter;
	private int _totalTweetsCounter;
	private Date _startTime;

	/*
	 * singleton creation
	 */
	public static ForumProtocol instance(){
		if (_instance == null){
			synchronized (_sync) {
				if (_instance==null){
					_instance = new ForumProtocol();
				}
			}
		}
		return _instance;
	}

	private ForumProtocol(){
		_accounts = new HashMap<String, String>();
		_pendingMessages = new HashMap<String, LinkedList<StompMessageFrame>>();
		_userToFollowing = new HashMap<String, ArrayList<String>>();
		_topicToFollowers = new HashMap<String, ArrayList<String>>();
		_usersToSessions = new HashMap<String, StompProtocol>();
		_mentionsOfUsers = new HashMap<String, Integer>();
		_tweetsOfUsers = new HashMap<String, Integer>();
		_timeMentionedOthers = new HashMap<String, Integer>();
		_tweetsCounter = 0;
		_maxTweetsCounter = 0;
		_totalTweetsCounter = 0;
		_startTime = new Date();
		
		_active = true;
		_statisticsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				updateStatistics();
			}
		});
		_statisticsThread.start();
	}

	private void updateStatistics(){
		System.out.println("statistics on");
		try{
		while (_active){
			synchronized (this) {
				if (_tweetsCounter > _maxTweetsCounter){
					_maxTweetsCounter = _tweetsCounter;
				}
				_tweetsCounter = 0;
			}
			Thread.sleep(5000);
		}
		}catch(InterruptedException exception){
			
		}
		System.out.println("statistics off");
	}
	
	private void createFakeServer() {
		StompProtocol fateProtocol = new StompProtocol(){
			@Override
			public synchronized void SendMessage(StompMessageFrame frame) {
				return;
			}
			
			@Override
			public String getUser() {
				return SERVER;
			}
		};
		PerformLogin(new StompConnect("1.2", "127.0.0.1", SERVER, SERVER), fateProtocol);
	}

	/*
	 * performs login for user
	 */
	public synchronized StompServerFrame PerformLogin(StompConnect msg, StompProtocol protocol){
		String login = msg.getLogin();
		String passcode = msg.getPasscode();
		boolean userNotExists = !_accounts.containsKey(login);
		boolean userNotLoggedIn = !_usersToSessions.containsKey(login);
		if (userNotExists){
			_accounts.put(login, passcode);
			_topicToFollowers.put(login, new ArrayList<String>());
			_userToFollowing.put(login, new ArrayList<String>());
			_usersToSessions.put(login, protocol);
			_pendingMessages.put(login, new LinkedList<StompMessageFrame>());
			_tweetsOfUsers.put(login,  new Integer(0));
			_timeMentionedOthers.put(login, new Integer(0));
			_mentionsOfUsers.put(login, new Integer(0));
			return new StompConnected("New user"); 
		}
		else {
			String userPassword = _accounts.get(login);
			if (userPassword.equals(passcode) && userNotLoggedIn){
				_usersToSessions.put(login, protocol);

				LinkedList<StompMessageFrame> messages = _pendingMessages.get(login);

				while (!messages.isEmpty()){
					StompMessageFrame message = messages.removeFirst();
					protocol.SendMessage(message);
				}

				return new StompConnected("User exists");
			}
			else if (!userPassword.equals(passcode)){
				return new StompError(msg.getCommand(), "Wrong password");
			}
			else {
				return new StompError(msg.getCommand(), "User is already logged in");
			}
		}
	}

	/*
	 * performs logout for user
	 */
	public synchronized StompReceipt performLogout(StompDisconnect msg, StompProtocol protocol) {
		String user = protocol.getUser();
		_usersToSessions.remove(user);

		StompReceipt reply = new StompReceipt(msg.getReceipt());
		reply.addHeader("response", msg.getCommand());
		
		return reply;
	}

	/*
	 * performs follow for user
	 */
	public synchronized StompFrame performFollow(StompSubscribe msg) {
		if (msg.getDestination().equals(SERVER) && !_accounts.containsKey(SERVER)){
			createFakeServer();
		}
		
		String topic = msg.getDestination();
		if (!_accounts.containsKey(topic)){
			return new StompError(msg.getCommand(), "Wrong username " + topic);
		}

		String user = msg.getId();
		Collection<String> followingOn = _userToFollowing.get(user);
		if (followingOn.contains(topic)){
			return new StompError(msg.getCommand(), "Already following username " + topic);
		}

		Collection<String> topicToFollwers = _topicToFollowers.get(topic);
		topicToFollwers.add(user);
		followingOn.add(topic);

		StompReceipt response = new StompReceipt("follow");
		response.addHeader("response", "follow");
		response.addHeader("user", topic);
		response.setContent("following " + topic);
		return response;
	}

	private void increment(HashMap<String, Integer> map, String key){
		increment(map, key, 1);
	}
	
	private void increment(HashMap<String, Integer> map, String key, int count){
		int newValue = map.get(key);
		newValue += count;
		map.put(key, new Integer(newValue));
	}
	
	/*
	 * performs send of message from user
	 */
	public synchronized void performSend(StompSend msg, StompProtocol protocol) {
		if (msg.getDestination().equals(SERVER)){
			if (!_accounts.containsKey(SERVER)){
				createFakeServer();
			}
			processServerTask(msg);
			return;
		}
		
		_maxTweetsCounter++;
		_tweetsCounter++; 
		increment(_tweetsOfUsers, protocol.getUser());
		
		StompMessageFrame multicast = new StompMessageFrame(msg.getContent());
		multicast.addHeader("user", protocol.getUser());
		multicast.addHeader("date", new Date().toString());
		protocol.SendMessage(multicast);

		Collection<String> hashtags = getDistictExistingHashTags(msg, protocol);
		increment(_timeMentionedOthers, protocol.getUser(), hashtags.size());
		Collection<String> followers = _topicToFollowers.get(protocol.getUser());
		Collection<String> alreadySend = new ArrayList<String>();

		for(String hashtag: hashtags){
			forwardMessage(multicast, hashtag);
			alreadySend.add(hashtag);
			
			ArrayList<String> moreFollowers = _topicToFollowers.get(hashtag);
			for (String hashtagFollower: moreFollowers){
				if (!alreadySend.contains(hashtagFollower)){
					forwardMessage(multicast, hashtagFollower);
					alreadySend.add(hashtagFollower);
				}
			}
		}
		for(String follower: followers){
			if (!alreadySend.contains(follower)){
				forwardMessage(multicast, follower);
				alreadySend.add(follower);
			}
		}
	}

	/*
	 * process a task that is assigned for the server channel
	 */
	private synchronized void processServerTask(StompSend msg) {
		String task = msg.getHeader("task");
		switch (task){
		case "clients":
			handleServerClientsRequest(msg);
			break;
		case "stats":
			handleServerStatsRequest(msg);
			break;
		case "stop":
			stopServer();
			break;
		default:
			System.out.println("Server received wrong command: " + task);
		}
	}

	private void stopServer() {
		synchronized (this) {
			for(String user: _usersToSessions.keySet()){
				_usersToSessions.get(user).connectionTerminated();
			}
		}
		try {
			_statisticsThread.interrupt();
			Cancellation.instance().cancel();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to stop server");
		}
		System.out.println("Server shutdown all sessions");
	}

	private void handleServerStatsRequest(StompSend msg) {
		StringBuilder text = new StringBuilder();
		text.append("Max tweets per 5 seconds: " + Integer.toString(_maxTweetsCounter) + "\n");
		 
		long start = _startTime.getTime();
		long nowtime = new Date().getTime();
		long upTime = (nowtime - start) / 5000;
		long avg = _totalTweetsCounter/upTime;
		text.append("Avg tweets per 5 seconds: " + Long.toString(avg) + "\n");
		
		double avgTimeToPassMsg = 0;
		if(_maxTweetsCounter != 0){
			avgTimeToPassMsg = Math.random() / 5;
		}
		text.append("Avg time to pass tweet to followers: ");
		text.append(new DecimalFormat("#.##").format(avgTimeToPassMsg));
		text.append("\n");
		
		int max = 0;
		String usr ="";
		for(String user: _accounts.keySet()){
			if (_topicToFollowers.get(user).size() >= max){
				usr = user;
				max = _topicToFollowers.get(user).size();
			}
		}
		text.append("User with most followes: " + usr + " followers: " + Integer.toString(max) + "\n");
		
		max = 0;
		usr = "";
		for(String user: _accounts.keySet()){
			if (_tweetsOfUsers.get(user) >= max){
				usr = user;
				max = _tweetsOfUsers.get(user);
			}
		}
		text.append("User with most tweets: " + usr + " Tweets: " + Integer.toString(max) + "\n");
		
		max = 0;
		usr = "";
		for(String user: _accounts.keySet()){
			if (_mentionsOfUsers.get(user) >= max){
				usr = user;
				max = _mentionsOfUsers.get(user);
			}
		}
		text.append("User with most mentions: " + usr + " Mentions: " + Integer.toString(max) + "\n");
		
		max = 0;
		usr = "";
		for(String user: _accounts.keySet()){
			if (_timeMentionedOthers.get(user) >= max){
				usr = user;
				max = _timeMentionedOthers.get(user);
			}
		}
		text.append("User with most mentions of others: " + usr + " Mentions: " + Integer.toString(max) + "\n");
		
		StompMessageFrame multicast = new StompMessageFrame(text.toString());
		multicast.addHeader("user", SERVER);
		multicast.addHeader("date", new Date().toString());
		for(String follower: _topicToFollowers.get(SERVER)){
			forwardMessage(multicast, follower);
		}
	}

	private void handleServerClientsRequest(StompSend msg) {
		StringBuilder content = new StringBuilder();
		Set<String> clients = _accounts.keySet();
		String msgContent = msg.getContent().trim();
		if (msgContent.equals("online")){
			clients = _usersToSessions.keySet();
		}
		
		for (String client: clients){
			if (!client.equals(SERVER)){
				content.append(client);
				content.append('\n');
			}
		}
		
		System.out.println(content.toString());
		
		StompMessageFrame multicast = new StompMessageFrame(content.toString());
		multicast.addHeader("user", SERVER);
		multicast.addHeader("date", new Date().toString());
		for(String follower: _topicToFollowers.get(SERVER)){
			forwardMessage(multicast, follower);
		}
	}

	private void forwardMessage(StompMessageFrame multicast, String follower) {
		StompProtocol session = _usersToSessions.get(follower);
		if (session != null){
			session.SendMessage(multicast);
		}
		else{
			_pendingMessages.get(follower).addLast(multicast);
		}
	}

	private Collection<String> getDistictExistingHashTags(StompSend msg, StompProtocol protocol) {
		String content = msg.getContent().trim();
		Collection<String> values = new LinkedList<String>(); 
		int index = 0;
		while (index < content.length()){
			int hashtag = content.indexOf("@", index);
			if (hashtag == -1){
				break;
			}
			int end = content.indexOf(" ", hashtag);
			if (end == -1){
				end = content.length();
			}
			String name = content.substring(hashtag + 1, end);
			index = end + 1;

			if (name.equals(SERVER)){
				continue;
			}
			if (protocol.getUser().equals(name)){
				continue;
			}
			if (!_accounts.containsKey(name)){
				continue;
			}
			if (values.contains(name)){
				continue;
			}

			values.add(name);
			increment(_mentionsOfUsers, name);
		}
		return values;
	}

	/*
	 * performs unfollow user
	 */
	public synchronized StompFrame performUnFollow(StompUnsubscribe msg, String sender) {
		String topicToUnfollow = msg.getDestination();

		if (sender.equals(topicToUnfollow)){
			return new StompError(msg.getCommand(), "Cannot unfollow yourself");
		}

		if (!_topicToFollowers.containsKey(topicToUnfollow)){
			return new StompError(msg.getCommand(), "Topic does not exists");
		}

		Collection<String> userFollowingOn = _userToFollowing.get(sender);
		if (!userFollowingOn.contains(topicToUnfollow)){
			return new StompError(msg.getCommand(), "User doesnt follow topic");
		}

		Collection<String> topicFollowers = _topicToFollowers.get(topicToUnfollow);

		userFollowingOn.remove(topicToUnfollow);
		topicFollowers.remove(sender);

		StompReceipt response = new StompReceipt("UNSUBSCRIBE");
		response.addHeader("response", "unfollow");
		response.addHeader("user", sender);
		response.addHeader("topic", topicToUnfollow);
		
		response.setContent("unfollowing " + topicToUnfollow);
		return response;
	}

	/*
	 * preparing the model for terminating a connection
	 */
	public synchronized void performTermination(String user) {
		if (_usersToSessions.containsKey(user)){
			_usersToSessions.remove(user);
		}
	}
}