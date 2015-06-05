package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;

/**
 * activate all the forum services (non-users)
 *
 * !!! one object per connection !!!
 *
 * Created by hodai on 4/18/15.
 */
public class ForumService {
    //Fields
    private Forum forum;
    private UserService userService;

    public ForumService(String forumName, UserService us){
        //TODO - get the relevant forum object (singleton?)
        forum = ForumFacade.getForum(forumName);
        if (forum == null){
            throw new RuntimeException("forum not found");
        }
        userService = us;
    }

    public boolean addNewSubForum(String subForumName){
        //TODO - validate data and add more shit to parameters
        //TODO - check
        Member moderate = userService.getUserAsMember(); // find the moderator object;
        if(moderate == null)
            return false;

        boolean result =forum.addNewSubForum(subForumName, moderate);
        if(result == false)
            return false;
        return true;
    }
/* TODO
    public Collection<SubForum> getSubForums(){
        return forum.getSubForums();
    }
*/
    public boolean addNewThread(String threadName){
        //TODO - validate
        boolean result = forum.addNewThread(threadName);
        if(result == false)
            return false;
        return true;
    }

    /**
     * change the properties of this specific forum
     *
     * TODO - need to implement
     *
     * @return - true if changes success
     */
    public boolean setProperties(){
        //TODO - implement
        return false;
    }
}
