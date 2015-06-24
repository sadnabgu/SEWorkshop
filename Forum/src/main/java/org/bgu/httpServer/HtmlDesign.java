package org.bgu.httpServer;

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


}
