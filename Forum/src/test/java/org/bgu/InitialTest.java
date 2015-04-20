package org.bgu;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by gur on 18/04/2015.
 */
public class InitialTest {
    // test example
    @Test
    public void what_how_expected(){
    }

    @Test
    @Ignore
    public void what_how_fail(){
        Assert.assertTrue(false);
    }

}
