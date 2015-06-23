package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.ForumService;

import java.util.ArrayList;

public class GetForumsRequest extends StompClientFrame{
    public GetForumsRequest(String command) {
        super(command);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<ArrayList<String>> retObj =ForumService.getAllForums();
        if (retObj._result != Result.SUCCESS){
            //TODO - send fail
            GeneralStompFrame gfs = new GeneralStompFrame(getCommand(), null, "fail");
            gfs.addHeader("error", retObj._result.toString());
            return gfs;
        }

        ArrayList<String> forums = retObj._value;
        StringBuilder forumString = new StringBuilder();
        for (String current: forums){
            forumString.append(current + "\n");
        }

        return new GeneralStompFrame(getCommand(), null, forumString.toString().trim());
    }
}
