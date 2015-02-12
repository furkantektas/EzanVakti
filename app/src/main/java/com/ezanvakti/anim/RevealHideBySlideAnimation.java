package com.ezanvakti.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.ezanvakti.utils.UnitConverter;

/**
 * Created by Furkan Tektas on 2/7/15.
 */
public class RevealHideBySlideAnimation  {

    public static void startAnimation(final View v, int marginDP) {
        final float scale = v.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        final float marginPX = UnitConverter.dpToPx(marginDP);

        TranslateAnimation translateAnimation1 = new TranslateAnimation(0.0f,-marginPX,0f,0.0f);
        translateAnimation1.setDuration(270); // in ms
        v.startAnimation(translateAnimation1);
        translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation translateAnimation2 = new TranslateAnimation(-marginPX,0f,0.0f,0.0f);
                translateAnimation2.setDuration(270); // in ms
                translateAnimation2.setStartOffset(1000);
                v.startAnimation(translateAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
