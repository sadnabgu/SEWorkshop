package org.bgu;

import org.bgu.domain.model.SubForum;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Sharon Kerzman on 23/06/2015.
 */
public interface BridgeAPI {

    /************************************ ADMIN
     * @param adminName
     * @param adminPass**********************/

    public boolean initSystem(String adminName, String adminPass);
    public boolean createNewForum(String forumName, String managerName, String managerPass);
    public boolean deleteForum(String forumName);
    public boolean loginAdmin(String name, String pass);

    /************************************ FORUM
     * @param subforumName
     * @param moderates**********************/

    public boolean createSubForum(String subforumName, Collection moderates);
    public boolean deleteSubForum(String forumName, String subforumName);
    public boolean addModerate(String forumName, String subforumName, String moderateName);
    public boolean removeModerate(String forumName, String subforumName, String moderateName);

    /************************************ USER
     * @param forumName
     * @param memberName
     * @param memberPass **********************/

    public boolean register(String forumName, String memberName, String memberPass);
    public boolean login(String forumName, String name, String pass);
    public boolean logout();
    public int createNewThread(String subforumName, String messageTitle, String messageBody);
    public int createNewComment(String subforumName, int newThreadId, String commentTitle, String commentBody);
    public boolean editMessage(int messageId, String commentTitle, String commentBody);
    public boolean deleteMessage(int messageId);

}
