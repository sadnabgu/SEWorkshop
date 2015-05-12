package controllers;

import org.bgu.service.AdminService;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    static QueryString createQueryString(String queryString){
        return new QueryString(queryString, "&", "=");
    }

    public static Result login(String params){
        QueryString queryString = createQueryString(params);
        String forumName = queryString.get("forum");
        String userName = queryString.get("username");
        String password = queryString.get("password");

        boolean success = true;

        if (success){
            return ok(String.format("login successful\nforum=%s\nuser=%s\npassword=%s", forumName, userName, password));
        }
        else {
            return badRequest("login failed");
        }
    }

    public static Result isInitialized(String params){
        AdminService service = new AdminService();
        return ok("isInitialize not yet implemented");
        //return ok(Boolean.toString(service.isInitialized()));
    }

    public static Result initializeSystem(String params){
        QueryString queryString = createQueryString(params);
        String userName = queryString.get("username");
        String password = queryString.get("password");

        AdminService service = new AdminService();
        boolean result = service.initializeSystem(userName, password);
        if (result) {
            return ok(String.format("system was initialized. admin: %s password: %s", userName, password));
        }
        else {
            return badRequest();
        }
    }

    public static Result system_login(String params){
        QueryString queryString = createQueryString(params);
        String userName = queryString.get("username");
        String password = queryString.get("password");

        AdminService service = new AdminService();
        boolean result = service.loginSystem(userName, password);
        if (result) {
            return ok("");
        }
        else {
            return badRequest();
        }
    }
}
