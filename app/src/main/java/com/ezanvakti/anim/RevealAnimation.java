package com.ezanvakti.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.ezanvakti.utils.UnitConverter;

/**
 * Created by Furkan Tektas on 2/7/15.
 */
public class RevealAnimation extends Animation {
    private boolean show = true;
    private int desiredHeightPX = 0;
    private View v;

    public RevealAnimation(View v, boolean show, int desiredHeight) {
        this.v = v;
        this.show = show;
        this.desiredHeightPX = UnitConverter.dpToPx(desiredHeight);
        setDuration(AnimConstants.ANIM_TIME);
        setFillAfter(true);
        v.getLayoutParams().height = show ? 0 : desiredHeightPX;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        if (show) {
            newHeight = (int) (desiredHeightPX * interpolatedTime);
        } else {
            newHeight = (int) (desiredHeightPX * (1 - interpolatedTime));
        }
        v.getLayoutParams().height = newHeight;
        v.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
