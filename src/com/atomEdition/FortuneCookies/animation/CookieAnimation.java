package com.atomEdition.FortuneCookies.animation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.utils.ActivityUtils;
import com.atomEdition.FortuneCookies.view.TutorialView;

/**
 * Created by FruityDevil on 17.12.14.
 */
public class CookieAnimation extends ContextWrapper{

    public CookieAnimation(Context baseContext){
        super(baseContext);
    }

    public TranslateAnimation getMoveToCenterAnimation(View view, ImageButton imageButtonFinal){
        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                0, Utils.SCREEN_WIDTH/2 - originalPos[0],
                0, (Utils.SCREEN_HEIGHT/2 + new ActivityUtils(this).convertToPx(Utils.BORDER_SIZE/2)) - originalPos[1]);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(Utils.ANIMATION_MOVE_TO_CENTER_DURATION);
        final View viewFinal = view;
        final ImageButton imageButton = imageButtonFinal;
        translateAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewFinal.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageButton.setVisibility(View.VISIBLE);
                viewFinal.clearAnimation();
                viewFinal.setVisibility(View.INVISIBLE);
                imageButton.setBackgroundDrawable(viewFinal.getBackground());
                new TutorialView(CookieAnimation.this.getApplicationContext(), ActivityUtils.activity).checkAndShow(R.string.tutorial_crack);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return translateAnimation;
    }

    public TranslateAnimation getMoveOutAnimation(View view){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, Utils.SCREEN_LIMIT);
        translateAnimation.setFillAfter(false);
        translateAnimation.setDuration(Utils.ANIMATION_MOVE_OUT_DURATION);
        final View viewFinal = view;
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewFinal.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewFinal.clearAnimation();
                viewFinal.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return translateAnimation;
    }

    public void animationMoveOutLeft(ImageButton imageButtonHalfLeft){
        final ImageButton imageButton = imageButtonHalfLeft;
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0 - Utils.SCREEN_LIMIT, 0, 0);
        translateAnimation.setFillAfter(false);
        translateAnimation.setDuration(Utils.ANIMATION_MOVE_OUT_SIDES_DURATION);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageButton.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageButton.clearAnimation();
                imageButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageButton.startAnimation(translateAnimation);
    }

    public void animationMoveOutRight(ImageButton imageButtonHalfRight){
        final ImageButton imageButton = imageButtonHalfRight;
        TranslateAnimation translateAnimation = new TranslateAnimation(0, Utils.SCREEN_LIMIT, 0, 0);
        translateAnimation.setFillAfter(false);
        translateAnimation.setDuration(Utils.ANIMATION_MOVE_OUT_SIDES_DURATION);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageButton.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageButton.clearAnimation();
                imageButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageButton.startAnimation(translateAnimation);
    }

    public ScaleAnimation animationCrumbs(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, 50, 50);
        scaleAnimation.setDuration(Utils.ANIMATION_CRUMBS_DURATION);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }

    public void setProphecyIdleAnimation(){
        final FrameLayout frameLayout = (FrameLayout)ActivityUtils.activity.findViewById(R.id.prophecy_layout);
        frameLayout.setVisibility(View.INVISIBLE);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.prophecy_idle);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setFillBefore(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        frameLayout.startAnimation(scaleAnimation);
    }
}
