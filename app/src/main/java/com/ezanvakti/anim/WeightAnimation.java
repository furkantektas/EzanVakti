package com.ezanvakti.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by Furkan Tektas on 2/7/15.
 */
public class WeightAnimation extends Animation {
    private final View mView;
    private final float mStartWeight;
    private final float mDeltaWeight;

    public WeightAnimation(View v, float endWeight) {
        mView = v;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mView.getLayoutParams();
        mStartWeight = lp.weight;
        mDeltaWeight = endWeight - mStartWeight;
        setFillAfter(true);
        setDuration(AnimConstants.ANIM_TIME);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mView.getLayoutParams();
        lp.weight = (mStartWeight + (mDeltaWeight * interpolatedTime));
        mView.setLayoutParams(lp);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}