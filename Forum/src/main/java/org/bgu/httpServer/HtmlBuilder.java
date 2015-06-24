package org.bgu.httpServer;

import org.bgu.service.ServiceObjects.ServiceMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by hodai on 6/24/15.
 */
public class HtmlBuilder {
    String _forumName;
    Map<String, String> _params;
    Collection<ServiceMessage> _messages;
    Collection<String> _subForums;

    public HtmlBuilder(){
        _forumName = "default title";
        _messages = new ArrayList<>();
        _subForums = new ArrayList<>();
    }

    public void setParams(Map<String, String> p){
        _params = p;
    }
    public void set_forumName(String _forumName) {
        this._forumName = _forumName;
    }
    public String get_forumName() {
        return _forumName;
    }
    public Collection<ServiceMessage> get_messages() {
        return _messages;
    }
    public Collection<String> get_subForums() {
        return _subForums;
    }

    public void addMessage(ServiceMessage msg){
        _messages.add(msg);
    }
    public void addMessage(Collection<ServiceMessage> serviceMessages){
        for (ServiceMessage m : serviceMessages){
            addMessage(m);
        }
    }
    public void addSubForum(String sf){
        _subForums.add(sf);
    }
    public void addSubForum(Collection<String> sf){
        for (String s : sf){
            addSubForum(s);
        }
    }


    public String buildSubForumsPage(String memberName, String forumName){
        StringBuilder builder = new StringBuilder();
        builder.append(HtmlDesign.HTML_TOP);
        builder.append("\n<h1>Hello ");
        builder.append(memberName);
        builder.append("</h1>\n<br>\n");
        for (String s : _subForums){
            builder.append("\n");
            builder.append(HtmlDesign.makeSubForumLink(s, _params, forumName, s));
            builder.append("\n<br>\n");
        }
        builder.append(HtmlDesign.HTML_BOTTOM);

        return builder.toString();
    }
}
