package org.bgu;

import org.bgu.domain.model.SubForum;

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

    /************************************ FORUM
     * @param subforumName
     * @param moderates**********************/

    public boolean createSubForum(String subforumName, Collection moderates);
    public boolean deleteSubForum();
    public boolean addModerate();
    public boolean deleteModerate();

    /************************************ USER
     * @param memberName
     * @param memberPass**********************/

    public boolean register(String memberName, String memberPass);
    public boolean login(String name, String pass);
    public boolean logout();
    public boolean createNewThread();
    public boolean createNewComment();
    public boolean editMessage();
    public boolean deleteMessage();

}
