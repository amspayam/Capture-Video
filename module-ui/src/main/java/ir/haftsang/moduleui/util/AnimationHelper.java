package ir.haftsang.moduleui.util;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

/**
 * Created by p.kokabi on 7/11/2017.
 */

public class AnimationHelper {

    public static long SHORT_DELAY = 200;
    public static long MEDIUM_DELAY = 500;
    public static long LONG_DELAY = 2000;

    public static void fadIn(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            ObjectAnimator animAlphaVisible = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(animAlphaVisible);
            animatorSet.setDuration(1000);
            animatorSet.start();
            view.setEnabled(true);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void fadOut(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            ObjectAnimator animAlphaInVisible = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(animAlphaInVisible);
            animatorSet.setDuration(1000);
            animatorSet.start();
            view.setEnabled(false);
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void rotateUp(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(SHORT_DELAY);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public static void rotateDown(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(SHORT_DELAY);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public static void changeTextColor(Context context, TextView textView, int color) {
        ObjectAnimator textAnim = ObjectAnimator.ofInt(textView, "textColor"
                , textView.getCurrentTextColor(), ContextCompat.getColor(context, color));
        textAnim.setEvaluator(new ArgbEvaluator());
        textAnim.setDuration(500);
        textAnim.start();
    }

    public static void translate(View view, float translateTo, long duration) {
        view.animate().setDuration(duration).translationY(translateTo);
    }

    public static void changeImageViewColor(Context context, AppCompatImageView view, int color) {
        ObjectAnimator viewAnim = ObjectAnimator.ofInt(view, "colorFilter"
                , view.getSolidColor(), ContextCompat.getColor(context, color));
        viewAnim.setEvaluator(new ArgbEvaluator());
        viewAnim.setDuration(500);
        viewAnim.start();
    }

    public static void gradientAnim(View root) {
        AnimationDrawable anim = (AnimationDrawable) root.getBackground();
        anim.setEnterFadeDuration(500);
        anim.setExitFadeDuration(1000);
        anim.start();
    }
}