package com.ahmed.customcounter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CounterView extends FrameLayout {

    ProgressBar progressBar;
    TextView txtProgress;
    LayerDrawable layerDrawable;
    public CounterView(@NonNull Context context) {
        super(context);
        init();
    }

    public CounterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CounterView);
        int progress = typedArray.getInteger(R.styleable.CounterView_progressAndText,0);
        setProgressAndText(String.valueOf(progress));

        int colour = typedArray.getColor(R.styleable.CounterView_progressBaseColour,getResources().getColor(R.color.black)  );
        setProgressBaseColour(colour);
    }

    public CounterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.counter_view, this);
        progressBar = view.findViewById(R.id.progressBar);
        txtProgress = view.findViewById(R.id.textView);
        layerDrawable =(LayerDrawable) progressBar.getProgressDrawable();
    }

    public void setText(String text) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,Integer.parseInt(text));
        valueAnimator.setDuration(2500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                txtProgress.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();
    }

    public void setProgressBar(int progress)    {
        ObjectAnimator.ofInt(progressBar,"progress", progress)
                .setDuration(2500)
                .start();
    }

    public void setProgressAndText(String progress) {
        setText(progress);
        setProgressBar(Integer.parseInt(progress));
    }

    public void setProgressBaseColour(int colour) {
        GradientDrawable drawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.base_progress);
        drawable.setColor(colour);
    }
}
