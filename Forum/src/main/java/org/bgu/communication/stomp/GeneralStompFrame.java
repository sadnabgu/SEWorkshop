package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.HashMap;

/**
 * Created by gur on 09/06/2015.
 */
public class GeneralStompFrame extends StompClientFrame {
    private GeneralStompFrame(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.addHeaders(headers);
        this.setContent(content);
    }

    public static GeneralStompFrame create(String command, HashMap<String, String> headers, String content, RetObj ret) {
        if (ret._result != Result.SUCCESS) {
            GeneralStompFrame frame = new GeneralStompFrame(command, headers, content);
            frame.addHeader("error", ret._result.name());
            return frame;
        }
        else {
            return new GeneralStompFrame(command, headers, content);
        }
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
