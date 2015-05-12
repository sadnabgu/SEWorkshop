package controllers;

import java.util.HashMap;

/**
 * Created by gur on 13/05/2015.
 */
public class QueryString {
    HashMap<String,String> params;
    public QueryString(String queryString, String delimiter, String assigner){
        params = new HashMap<>();
        String[] couples = queryString.toLowerCase().split(delimiter);
        for (String couple: couples){
            String[] pair = couple.split(assigner);
            params.put(pair[0], pair[1]);
        }
    }

    public String get(String param){
        if (params.containsKey(param.toLowerCase())){
            return params.get(param.toLowerCase());
        }
        return "";
    }
}

