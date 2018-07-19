package com.rohitsavant.gorentjoy;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by rohitsavant on 19/06/18.
 */

public class MyApplication extends MultiDexApplication {
    public static final String SERVER_URL="https://gorentjoy.com/api/v1/";
    public static final String CAT_URL=SERVER_URL+"categories";
    public static final String SUB_CAT_URL=SERVER_URL+"categories";
    public static final String LISTINGS=SERVER_URL+"listings";
}
