package com.ezanvakti.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.ezanvakti.utils.UnitConverter;
import com.innovattic.font.FontTextView;

/**
 * Created by Furkan Tektas on 2/12/15.
 */
public class RemainingTimeRevealAnimation extends Animation {
    private boolean show = true;
    private View v;
    private int desiredHeightPX = 0;


    public RemainingTimeRevealAnimation(View v, boolean show) {
        this.v = v;
        this.show = show;
        setDuration(270);
        setFillAfter(true);
        this.desiredHeightPX = UnitConverter.dpToPx(75);
        v.getLayoutParams().height = show ? 0 : desiredHeightPX;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float scale;
        int newHeight;

        if (show) {
            scale = interpolatedTime;
            newHeight = (int) (desiredHeightPX * interpolatedTime);
        } else {
            scale = (1 - interpolatedTime);
            newHeight = (int) (desiredHeightPX * (1 - interpolatedTime));
        }
        v.setAlpha(scale);
        v.getLayoutParams().height = newHeight;
        v.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
