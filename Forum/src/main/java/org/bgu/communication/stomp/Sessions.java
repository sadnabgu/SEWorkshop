package org.bgu.communication.stomp;

import org.bgu.communication.reactor.ProtocolTask;

import java.util.Hashtable;

/**
 * Created by gur on 22/06/2015.
 */
public class Sessions {
    private static Sessions instance = null;
    private Hashtable<String, ProtocolTask<StompFrame>> sessions;

    public static Sessions getInstance(){
        if (instance == null){
            synchronized (Sessions.class){
                if (instance == null){
                    instance = new Sessions();
                }
            }
        }
        return instance;
    }

    private Sessions(){
        sessions = new Hashtable<>();
    }

    public void add(String id, ProtocolTask<StompFrame> session){
        sessions.put(id, session);
    }

    public void remove(String id){
        sessions.remove(id);
    }
}
