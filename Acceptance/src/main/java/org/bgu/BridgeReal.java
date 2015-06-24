package org.bgu;

import org.bgu.domain.model.User;
import org.bgu.service.AdminService;
import org.bgu.service.ForumService;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class BridgeReal implements BridgeAPI {
        RetObj ans;
        UUID sid;
        protected String forumName;


    @Override
    public boolean initSystem(String adminName, String adminPass) {
        ans = AdminService.initializeSystem(adminName, adminPass);
        return ans._result==Result.SUCCESS;
    }

    @Override
    public boolean createNewForum(String forumName, String managerName, String managerPass) {
        ans = AdminService.createForum(sid, forumName, managerName, managerPass);
        return ans._result==Result.SUCCESS;

    }

    @Override
    public boolean deleteForum(String forumName) {
        ans = AdminService.removeForum(sid,forumName);
        return ans._result==Result.SUCCESS;
    }

    @Override
    public boolean loginAdmin(String name, String pass) {
        RetObj<UUID> ans = AdminService.loginSystem(name, pass);
        if (ans._result == Result.SUCCESS) {
            sid = ans._value;
            return true;
        }
        return false;
    }
    @Override
    public boolean createSubForum(String subforumName, Collection moderates) {
        ans = ForumService.addNewSubForum(sid, subforumName, moderates);
        return ans._result==Result.SUCCESS;

    }

    @Override
    public boolean deleteSubForum(String forumName, String subforumName) {
        ans = ForumService.removeSubForum(sid,subforumName);
        return ans._result==Result.SUCCESS;
    }

    @Override
    public boolean addModerate(String forumName, String subforumName, String moderateName) {
        ans = UserService.addModerator(sid, subforumName, moderateName);
        return ans._result==Result.SUCCESS;
    }

    @Override
    public boolean removeModerate(String forumName, String subforumName, String moderateName) {
        ans = UserService.removeModerator(sid,subforumName, moderateName);
        return ans._result==Result.SUCCESS;
    }

    @Override
    public boolean register(String forumName, String memberName, String memberPass) {
        ans = UserService.registerMember(sid,memberName,memberPass);
        return ans._result.equals(Result.SUCCESS);

    }

    @Override
    public boolean login(String forumName, String name, String pass) {
        this.forumName = forumName;
        return UserService.logInMember(sid,name,pass)._result.equals(Result.SUCCESS);
    }

    @Override
    public boolean logout() {
        if(sid == null){
            return false;
        }
        ans = UserService.logOut(sid);
        if (!ans._result.equals(Result.SUCCESS)){
            return false;
        }
        sid=UserService.logInGuest(forumName)._value;
        return false;
    }

    @Override
    public int createNewThread(String messageTitle, String messageBody) {
        return 0;
    }

    @Override
    public int createNewComment(int newThreadId, String commentTitle, String commentBody) {
        return 0;
    }

    @Override
    public boolean editMessage(int messageId, String commentTitle, String commentBody) {
        return false;
    }

    @Override
    public boolean deleteMessage(int messageId) {
        return false;
    }
}
