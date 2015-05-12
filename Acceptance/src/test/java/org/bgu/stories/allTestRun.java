package org.bgu.stories;

import org.bgu.stories.usersStories.EmailVerification;
import org.bgu.stories.usersStories.Login;
import org.bgu.stories.usersStories.SignUp;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class allTestRun {
    SignUp signUp = new SignUp();
    Login login = new Login();
    EmailVerification emailVerification = new EmailVerification();

    @Test
    public void run(){
        signUp.init();
        signUp.SignUp_UserSignUpWithCorrectData_UserFoundInSystem();
        signUp.UserLoginWithIncorrectData_UserIsSignedUp_UserIsAGuest();
        login.UserLoginWithIncorrectData_UserIsSignedUp_UserIsAGuest();
        login.UserLoginWithCorrectData_UserIsSignedUp_UserIsActive();
        emailVerification.InsertCorrectPassword_UserIsSignedUp_UserIsActive();
        emailVerification.InsertIncorrectPassword_UserIsSignedUp_UserIsNotActive();
    }

}
