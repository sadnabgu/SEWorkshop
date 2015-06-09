package org.bgu.communication.management;

import org.bgu.service.AdminService;

import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by gur on 10/06/2015.
 */
public class ForumContainer {
    static {
        sessions = new Hashtable<>();
    }

    public static Hashtable<String, Session> sessions;
    public static String adminSessionId;
    public static AdminService adminService;
}
