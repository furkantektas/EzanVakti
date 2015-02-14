package com.ezanvakti.ui;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;

import com.ezanvakti.R;
import com.innovattic.font.FontTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Custom CountDownTimer widget which countdowns to given date.
 *
 * Created by Furkan Tektas on 11/20/14.
 */
public class CountDownTimerWidget extends FontTextView {
    private CountDownTimer mCountDownTimer;
    private int mUpdateInterval = 1000; // in msec
    private CountdownTimerInterface mListener;
    private SimpleDateFormat mTimeFormatter = new SimpleDateFormat("HH:mm:ss");
    private Date mTargetDate = new Date();

    public CountDownTimerWidget(Context context) {
        super(context);
        initTimer();
    }

    public CountDownTimerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        extractAttrs(context, attrs);
        initTimer();
    }

    public CountDownTimerWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        extractAttrs(context, attrs);
        initTimer();
    }

    private void extractAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountDownTimerWidget,
                0, 0);

        try {
            mUpdateInterval = a.getInt(R.styleable.CountDownTimerWidget_update_interval, 1000);
        } finally {
            a.recycle();
        }
    }

    public void initTimer() {
        long now = System.currentTimeMillis();

        mCountDownTimer = new CountDownTimer(getTargetDate().getTime()-now, getUpdateInterval()) {
            @Override
            public void onTick(long l) {
                CountDownTimerWidget.this.onTick(l);
            }

            @Override
            public void onFinish() {
                CountDownTimerWidget.this.onFinish();
            }
        };
        mCountDownTimer.start();
    }

    public void onTick(long l) {
        if(mListener != null)
            mListener.onTick(l);
        updateText(l);
    }

    public void onFinish() {
        if(mListener != null)
            mListener.onFinish();
    }

    public CountdownTimerInterface getListener() {
        return mListener;
    }

    public void setListener(CountdownTimerInterface mListener) {
        this.mListener = mListener;
    }

    public int getUpdateInterval() {
        return mUpdateInterval;
    }

    public void setUpdateInterval(int mUpdateInterval) {
        this.mUpdateInterval = mUpdateInterval;
        if(mCountDownTimer != null)
            mCountDownTimer.cancel();
        initTimer();
    }

    public Date getTargetDate() {
        return mTargetDate;
    }

    public void setTargetDate(Date mTargetDate) {
        this.mTargetDate = mTargetDate;
        if(mCountDownTimer != null)
            this.mCountDownTimer.cancel();
        initTimer();
    }

    public interface CountdownTimerInterface {
        public void onTick(long l);
        public void onFinish();
    }

    /**
     * Formats remaining time in millis to HH:mm:ss
     * Ref: http://stackoverflow.com/a/9027379/1360267
     * @param remainingTime
     * @return
     */
    private String formatRemainingTime(long remainingTime) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(remainingTime),
                TimeUnit.MILLISECONDS.toMinutes(remainingTime) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(remainingTime) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void updateText(long remainingTime) {
        SpannableString ss1 =  new SpannableString(formatRemainingTime(remainingTime));
        ss1.setSpan(new RelativeSizeSpan(0.5f), ss1.length()-3,ss1.length(), 0); // set seconds' size to half
        setText(ss1);
    }
}