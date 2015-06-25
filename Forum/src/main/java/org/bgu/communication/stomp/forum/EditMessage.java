package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.ForumService;

import java.util.HashMap;
import java.util.UUID;

public class EditMessage extends StompClientFrame {
    private final String sid;
    private final String subforum;
    private final String msgid;
    private final String title;

    public EditMessage(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.msgid = headers.get("msgid");
        this.title = headers.get("title");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = ForumService.editMessage(UUID.fromString(sid), subforum, Integer.parseInt(msgid), title, getContent());
        return GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
    }
}
