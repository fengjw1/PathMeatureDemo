package com.fengjw.pathmeaturedemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView m1Iv;
    private ImageView m2Iv;
    private Button mBtn;
    private RelativeLayout mRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        m1Iv = (ImageView) findViewById(R.id.iv_1);
        m2Iv = (ImageView) findViewById(R.id.iv_2);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mRl = (RelativeLayout) findViewById(R.id.rl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
        mRl.addView(imageView, layoutParams);

        int[] parentLoc = new int[2];
        int[] startLoc = new int[2];
        int[] endLoc = new int[2];

        mRl.getLocationOnScreen(parentLoc);
        m1Iv.getLocationOnScreen(startLoc);
        m2Iv.getLocationOnScreen(endLoc);

        final int startX = startLoc[0] - parentLoc[0];
        final int startY = startLoc[1] - parentLoc[1];

        final int toX = endLoc[0] - parentLoc[0];
        final int toY = endLoc[1] - parentLoc[1];

        final int quadX = (startX + toX) / 2;
        final int quadY = (startY + toY) / 2;

        Path path = new Path();
        path.moveTo(startX, startY);
        path.quadTo(quadX, quadY, toX, toY);//控制点

        final PathMeasure pathMeasure = new PathMeasure(path, false);//false的目的是为了不闭合
        final float[] currentPath = new float[2];

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(value, currentPath, null);
                imageView.setTranslationX(currentPath[0]);
                imageView.setTranslationY(currentPath[1]);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRl.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                init();
                break;
            default:
                break;
        }
    }
}
