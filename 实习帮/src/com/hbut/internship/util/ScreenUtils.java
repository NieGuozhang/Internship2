package com.hbut.internship.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * ScreenUtils
 */
public class ScreenUtils {
    private static int screenW;
    private static int screenH;

    public static int getScreenW(Activity mActivity){
        if (screenW == 0){
            initScreen(mActivity);
        }
        return screenW;
    }

    public static int getScreenH(Activity mActivity){
        if (screenH == 0){
            initScreen(mActivity);
        }
        return screenH;
    }

    private static void initScreen(Activity mActivity){
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
    }
}
