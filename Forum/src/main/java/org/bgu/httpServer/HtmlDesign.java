package org.bgu.httpServer;

import org.bgu.service.ServiceObjects.ServiceMessage;

import javax.swing.text.html.parser.Entity;
import java.util.Map;

/**
 * Created by hodai on 6/24/15.
 */
public class HtmlDesign {
    public static final String HTML_TOP =
            "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset= utf-8\">\n" +
            "<title>ForumSystem</title>\n" +
            "</head>\n" +
            "<body>\n";

    public static final String HTML_BOTTOM = "</body>\n" + "</html>\n";

    public static String createSubforumsForm(String forumName, String sid){
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(
                "<form action=\"/" + forumName + "\" method=\"get\">\n" +
                        "<input hidden name=\"sid\" value='"+sid+"'/>\n" +
                "  First name: <input type=\"text\" name=\"fname\"/><br>\n" +
                "  Last name: <input type=\"text\" name=\"lname\"/><br>\n" +
                "  <input type=\"submit\" value=\"Submit1\"/>\n" +
                "</form>");
        return stringBuilder.toString();
    }

    public static String makeSubForumLink(String text, Map<String, String> params, String destination, String subForum){
        return "<a href=\"/" + destination + buildParamString(params) + "subforum=" + subForum + "\">" + text + "</a>";

    }

    public static String buildParamString(Map<String, String> params){
        StringBuilder builder = new StringBuilder();
        builder.append("?");
        for (Map.Entry<String, String> e : params.entrySet()){
            builder.append(e.getKey().toString());
            builder.append("=");
            builder.append(e.getValue());
            builder.append("&");
        }
        return builder.toString();
    }


    public static String makeThreadLink(String title, Map<String, String> params, String forumName, int id) {
        return "<a href=\"/" + forumName + buildParamString(params) + "mid=" + id + "\">" + title + "</a>";

    }

    public static String makeThreadTreeLink(ServiceMessage message, Map<String, String> params, String forumName, int level) {
        StringBuilder builder = new StringBuilder();
        // the link

        builder.append("<table>\n" +
                "\t\t\t<col width=\"20\">\n" +
                "\t\t  <tr>\n" +
                "\t\t    <th></th>\n" +
                "\t\t    <td>");

        builder.append("<a href=\"/");
        builder.append(forumName);
        builder.append(buildParamString(params));
        builder.append("mid=");
        builder.append(message._id);
        builder.append("&newComment=1");
        builder.append("\">");
        builder.append(message._title);
        builder.append("</a> : ");

        // creator
        builder.append("<i>");
        builder.append(message._creator);
        builder.append("</i>\n<br>\n");

        // the content:
        builder.append(message._body);
        builder.append("<br>");

        //next level:
        for (ServiceMessage m : message._replies) {
            builder.append(makeThreadTreeLink(m, params,forumName, level+1));
        }

        // close the table
        builder.append("</td>\n" +
                "\t\t  </tr>\n" +
                "\t</table>");

        return builder.toString();

    }

    public static String makeLoginLink(String forumName, Map<String, String> params) {
        StringBuilder builder = new StringBuilder();

        builder.append("<a href=\"/");
        builder.append(forumName);
        builder.append(buildParamString(params));
        builder.append("login=1\">");
        builder.append("Lodin");
        builder.append("</a> <br> ");
        return builder.toString();
    }

    public static String buildNewThreadForm(String forumName, Map<String, String> params) {
        params.put("newThread", "1");

        StringBuilder builder = new StringBuilder();
        builder.append("\t<form action=\"");
        builder.append(forumName);
        builder.append("\">\n");

        for (Map.Entry<String, String> e : params.entrySet()){
            builder.append("<input type=\"hidden\" name=\"");
            builder.append(e.getKey());
            builder.append("\"  value=\"");
            builder.append(e.getValue());
            builder.append("\">\n");
        }

        builder.append("<br>\nadd new thread:<br>");
        builder.append("\tthread title:<br>\n");
        builder.append("\t<input type=\"text\" name=\"title\" >\n");
        builder.append("\t<br>\n");
        builder.append("\tbody:<br>\n");
        builder.append("\t<input type=\"text\" name=\"body\" >\n");
        builder.append("\t<br><br>\n");
        builder.append("\t<input type=\"submit\" value=\"post!\">\n");
        builder.append("\t</form>");


        return builder.toString();
    }

    public static String buildNewCommentForm(String forumName, Map<String, String> params) {

        StringBuilder builder = new StringBuilder();
        builder.append("\t<form action=\"");
        builder.append(forumName);
        builder.append("\">\n");

        for (Map.Entry<String, String> e : params.entrySet()){
            builder.append("<input type=\"hidden\" name=\"");
            builder.append(e.getKey());
            builder.append("\"  value=\"");
            builder.append(e.getValue());
            builder.append("\">\n");
        }

        builder.append("<br>\nadd new comment:<br>");
        builder.append("\tcomment title:<br>\n");
        builder.append("\t<input type=\"text\" name=\"title\" >\n");
        builder.append("\t<br>\n");
        builder.append("\tbody:<br>\n");
        builder.append("\t<input type=\"text\" name=\"body\" >\n");
        builder.append("\t<br><br>\n");
        builder.append("\t<input type=\"submit\" value=\"post!\">\n");
        builder.append("\t</form>");


        return builder.toString();
    }
}
