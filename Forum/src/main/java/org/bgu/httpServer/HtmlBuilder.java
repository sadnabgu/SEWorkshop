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
        builder.append(HtmlDesign.makeLoginLink(forumName, _params));
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

    public String buildTreadsPage(String memberName, String forumName, String subForumName) {
        StringBuilder builder = new StringBuilder();
        builder.append(HtmlDesign.HTML_TOP);

        builder.append(HtmlDesign.makeLoginLink(forumName, _params));
        builder.append("\n<h1>Hello ");
        builder.append(memberName);
        builder.append("</h1>\n<br>\n<h2>");
        builder.append(forumName);
        builder.append(":</h2>\n<br>\n<h3>sub-forum: ");
        builder.append(subForumName);
        builder.append("</h3>\n");
        builder.append("threads:<br>");

        for (ServiceMessage m : _messages){
            builder.append("\n");
            builder.append(HtmlDesign.makeThreadLink(m._title, _params, forumName, m._id));
            builder.append("\n<br>\n");
        }

        // new thread form
        builder.append(HtmlDesign.buildNewThreadForm(forumName, _params));

        builder.append(HtmlDesign.HTML_BOTTOM);

        return builder.toString();
    }

    public String buildTread(String memberName, String forumName, String subForumName, ServiceMessage message) {
        StringBuilder builder = new StringBuilder();
        builder.append(HtmlDesign.HTML_TOP);
        builder.append(HtmlDesign.makeLoginLink(forumName, _params));
        builder.append("\n<h1>Hello ");
        builder.append(memberName);
        builder.append("</h1>\n<br>\n<h2>");
        builder.append(forumName);
        builder.append(":</h2>\n<br>\n<h3>sub-forum: ");
        builder.append(subForumName);
        builder.append("</h3>\n");

        builder.append(
                HtmlDesign.makeThreadTreeLink(message, _params, forumName, 0));

        builder.append(HtmlDesign.HTML_BOTTOM);

        return builder.toString();
    }

    public String buildLoginPage(String forumName) {
        StringBuilder builder = new StringBuilder();
        builder.append(HtmlDesign.HTML_TOP);
        builder.append("\t<form action=\"");
        builder.append(forumName);
        builder.append("\">\n");
        for (Map.Entry<String, String> e : _params.entrySet()){
            builder.append("<input type=\"hidden\" name=\"");
            builder.append(e.getKey());
            builder.append("\"  value=\"");
            builder.append(e.getValue());
            builder.append("\">\n");
        }
        builder.append("\tuser name:<br>\n");
        builder.append("\t<input type=\"text\" name=\"user\" >\n");
        builder.append("\t<br>\n");
        builder.append("\tpassword:<br>\n");
        builder.append("\t<input type=\"password\" name=\"pass\" >\n");
        builder.append("\t<br><br>\n");
        builder.append("\t<input type=\"submit\" value=\"login\">\n");
        builder.append("\t</form>");

        builder.append(HtmlDesign.HTML_BOTTOM);

        return builder.toString();
    }

    public String buildNewCommentPage(String forumName) {
        StringBuilder builder = new StringBuilder();
        builder.append(HtmlDesign.HTML_TOP);

        builder.append(HtmlDesign.buildNewCommentForm(forumName,_params));

        builder.append(HtmlDesign.HTML_BOTTOM);

        return builder.toString();
    }
}
