package org.bgu;


import org.bgu.BridgeAPI;
import org.junit.Before;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class TestBase {

    public static final String ADMIN1_NAME = "SPONGE";
    public static final String ADMIN1_PASS = "BOB123";
    protected BridgeAPI bridge;

    public void setRealBridge(String whichBridge){
        if(whichBridge.equals("real")){
            bridge = new BrideReal();
        }
        else {
            bridge = new org.bgu.BridgeProxy();
        }
    }

    @Before
    public void init(){
        setRealBridge("real");
    }

}
