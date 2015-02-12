package com.ezanvakti.utils;

import android.content.res.Resources;

/**
 * Ref: http://stackoverflow.com/a/19953871/1360267
 * Created by Furkan Tektas on 2/7/15.
 */
public class UnitConverter {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
