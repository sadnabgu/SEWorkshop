package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;

import java.util.HashMap;

/**
 * Created by gur on 09/06/2015.
 */
public class GeneralStompFrame extends StompClientFrame {
    public GeneralStompFrame(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.addHeaders(headers);
        this.setContent(content);
    }

    /*
    ** here we will implement our code
    **
     */
    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        System.out.println(toString());
        return this;
    }
}
