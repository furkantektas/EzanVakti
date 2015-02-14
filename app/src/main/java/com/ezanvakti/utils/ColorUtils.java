package com.ezanvakti.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.ezanvakti.R;

/**
 * Created by Furkan Tektas on 2/12/15.
 */
public class ColorUtils {
    public static int getColor(Context context, int pos) {
        int resId = getColorResource(pos);
        if(context == null || context.getResources() == null)
            return -1;
        return context.getResources().getColor(resId);
    }

    private static int getColorResource(int pos) {
        switch (pos) {
            case 0: return R.color.imsak;
            case 1: return R.color.gunes;
            case 2: return R.color.ogle;
            case 3: return R.color.ikindi;
            case 4: return R.color.aksam;
            case 5: return R.color.yatsi;
        }
        return -1;
    }

    public static int shadeColor(int color, float shadeAmt) {
        float[] hsv = new float[3];
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv);
        hsv[2] *= (1 - shadeAmt);

        return Color.HSVToColor(hsv);
    }
}
