package org.bgu;

import org.bgu.service.AdminService;
import org.junit.Before;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 23/06/2015.
 */
public class InitializedTestBase extends TestBase {
    public static final String MANAGER_NAME = "MELKI'S";
    public static final String MANAGER_PASS = "MOM";
    public static final String FORUM_NAME = "milk and more";
    public static final String MEMBER_NAME = "GUSH";
    public static final String MEMBER_PASS = "KARI";


    @Before
    public void init() {
        super.init();
        AdminService.resetSystem();
        assertTrue("system is not initialized", bridge.initSystem(ADMIN1_NAME, ADMIN1_PASS));
        assertTrue("could not log in admin", bridge.loginAdmin(ADMIN1_NAME, ADMIN1_PASS));
    }

}
