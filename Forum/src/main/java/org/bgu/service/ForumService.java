package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;

import java.util.Collection;

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

    public ForumService(int forumID){
        //TODO - get the relevant forum object (singleton?)
        forum = ForumFacade.getForum(forumID);
        if (forum == null){
            //TODO exeption??
        }
    }

    public boolean addNewSubForum(String subForumName,
                            String moderateName){
        //TODO - validate data and add more shit to parameters
        //TODO - check
        Member moderate = null; //TODO - find the moderator object;
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
