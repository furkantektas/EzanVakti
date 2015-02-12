package com.ezanvakti.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezanvakti.R;
import com.ezanvakti.anim.RemainingTimeRevealAnimation;
import com.ezanvakti.anim.RevealAnimation;
import com.ezanvakti.anim.RevealHideBySlideAnimation;
import com.ezanvakti.anim.WeightAnimation;
import com.ezanvakti.db.model.Vakit;
import com.ezanvakti.utils.ColorUtils;
import com.ezanvakti.utils.DBUtils;
import com.ezanvakti.utils.OnSwipeTouchListener;
import com.ezanvakti.utils.VakitUtils;
import com.innovattic.font.FontTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link VakitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VakitFragment extends Fragment {
    private static final SimpleDateFormat HHMM = new SimpleDateFormat("HH:mm");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String FRAGMENT_NAME = "VakitFragment";

    private static final int SHADOW_COLOR = Color.argb((int)(255*0.2),0,0,0);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private List<VakitRow> rows = new ArrayList<VakitRow>();
    private Vakit mVakit;
    private LinearLayout mVakitContainer;
    private FontTextView mSettingsButton;
    private boolean mIsSettingsButtonVisible = false;
    private View mRootView = null;
    private int[] mColors = new int[6];
    private int mActive = -1;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VakitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VakitFragment newInstance(String param1, String param2) {
        VakitFragment fragment = new VakitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VakitFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("VakitFragment","onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_vakit, container, false);
        mVakitContainer = (LinearLayout) mRootView.findViewById(R.id.vakit_container);
        mSettingsButton = (FontTextView) mRootView.findViewById(R.id.settings_icon);

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, UlkeListFragment.newInstance())
                        .addToBackStack(FRAGMENT_NAME)
                        .commit();
            }
        });
        populateVakitViews(inflater);
        return mRootView;
    }

    private void toggleSettings(final boolean show) {
        if(show == mIsSettingsButtonVisible)
            return;

        Animation anim;
        if(show)
            anim = new AlphaAnimation(1f,0.7f);
        else
            anim = new AlphaAnimation(0.7f,1f);

        anim.setDuration(270);
        anim .setFillAfter(true);
        mVakitContainer.startAnimation(anim);


        Animation reveal = new RevealAnimation(mSettingsButton,show,50);
        mSettingsButton.startAnimation(reveal);
        mIsSettingsButtonVisible = show;
    }

    private void populateVakitViews(LayoutInflater inflater) {
        mVakit = DBUtils.getTodaysVakit();
        if(mVakit == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, UlkeListFragment.newInstance())
                    .addToBackStack(FRAGMENT_NAME)
                    .commit();
            return;
        }

        mVakitContainer.removeAllViews();
        rows.clear();

        for(int i=0;i<6;++i) {
            View temp = inflater.inflate(R.layout.vakit_row, mVakitContainer, false);
            final VakitRow row = new VakitRow();
            row.container = (FrameLayout) temp.findViewById(R.id.vakit_row);
            row.infoContainer = (FrameLayout) temp.findViewById(R.id.vakit_info_container);
            row.alarmContainer = (LinearLayout) temp.findViewById(R.id.alarm_container);
            row.label = (FontTextView) temp.findViewById(R.id.vakit_label);
            row.time = (FontTextView) temp.findViewById(R.id.vakit_time);
            row.remainingTime = (FontTextView) temp.findViewById(R.id.vakit_remaining_time);

            row.label.setShadowLayer(0,1,1, SHADOW_COLOR);
            row.time.setShadowLayer(0,1,1, SHADOW_COLOR);
            row.remainingTime.setShadowLayer(0,1,1, SHADOW_COLOR);

            row.label.setText(mVakit.getVakitStringResource(i));
            row.time.setText(HHMM.format(mVakit.getVakit(i)));
            Log.i("inflater",row.time.getText()+"");
            final int pos = i;

            row.infoContainer.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    RevealHideBySlideAnimation.startAnimation(row.infoContainer,50);
                    toggleSettings(false);
                }

                @Override
                public void onSwipeDown() {
                    super.onSwipeDown();
                    toggleSettings(true);
                }

                @Override
                public void onSwipeUp() {
                    super.onSwipeUp();
                    toggleSettings(false);
                }

                @Override
                public void onClick() {
                    setActive(pos);
                    toggleSettings(false);
                    super.onClick();
                }
            });

            mVakitContainer.addView(temp);
            rows.add(row);
        }
        int active = VakitUtils.getNextVakit(mVakit);
        setActive(active);
    }

    private void setWeights(int active) {
        Log.i("setWeights",active+"");
        for(int i=0;i<6;++i) {
            int weight = 1;
            if (i == active)
                weight = 8;
            else if (i == (active + 1))
                weight = 3;
            else if (i == (active - 1))
                weight = 2;
            Animation anim = new WeightAnimation(rows.get(i).container,weight);
            anim.setDuration(230);
            rows.get(i).container.startAnimation(anim);
        }
        mVakitContainer.requestLayout();
    }

    private void setBackgrounds(int active) {
        int activeColor = ColorUtils.getColor(getActivity(),active);
        for(int i=0;i<6;++i) {
            Integer colorFrom = mColors[i];
            Integer colorTo = ColorUtils.shadeColor(activeColor, i * 0.1f);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            final View container = rows.get(i).infoContainer;
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    container.setBackgroundColor((Integer)animator.getAnimatedValue());
                }

            });
            colorAnimation.setDuration(230);
            colorAnimation.start();
            mColors[i] = colorTo;
            rows.get(i).alarmContainer.setBackgroundColor(ColorUtils.shadeColor(activeColor, i * 0.1f + 0.4f) );
        }
        mVakitContainer.requestLayout();
    }

    private void setActive(int pos) {
        if(mActive == pos)
            return;
        setBackgrounds(pos);
        setWeights(pos);
        if(mActive != -1)
            scaleDownRemainingTime(mActive);
        scaleUpRemainingTime(pos);
        mActive = pos;
    }

    private void remainingTimeAnimation(int pos, boolean show) {
        FontTextView v = rows.get(pos).remainingTime;
        Animation a = new RemainingTimeRevealAnimation(v, show);
        a.reset();
        v.clearAnimation();
        v.startAnimation(a);
    }

    private void scaleUpRemainingTime(int pos) {
        remainingTimeAnimation(pos,true);
    }

    private void scaleDownRemainingTime(int pos) {
        remainingTimeAnimation(pos,false);
    }

    public static class VakitRow {
        public FrameLayout container;
        public FrameLayout infoContainer;
        public LinearLayout alarmContainer;
        public FontTextView label;
        public FontTextView time;
        public FontTextView remainingTime;
    }
}
