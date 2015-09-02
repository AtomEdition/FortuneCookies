package com.atomEdition.FortuneCookies.settings;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.animation.CookieAnimation;
import com.atomEdition.FortuneCookies.services.CookieUtils;
import com.atomEdition.FortuneCookies.services.ProphecyUtils;

/**
 * Created by FruityDevil on 23.12.14.
 */
public class ButtonListener extends ContextWrapper {

    Activity activity;
    private float touchX, touchY;

    public ButtonListener(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public void setImageButtonsListeners(){
        ImageButton imageButton = (ImageButton)this.activity.findViewById(R.id.cookie_half_left);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    touchX = motionEvent.getX();
                    touchY = motionEvent.getY();
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if((motionEvent.getX()<touchX)&&(Utils.isNear(touchY, motionEvent.getY(), 200))){
                        ImageButton imageButtonHalfLeft = (ImageButton)activity.findViewById(R.id.cookie_half_left);
                        new CookieAnimation(ButtonListener.this).animationMoveOutLeft(imageButtonHalfLeft);
                        CookieUtils.halfCount--;
                        new ProphecyUtils(ButtonListener.this, activity).prophecyCheck();
                    }
                }
                return false;
            }
        });
        imageButton = (ImageButton)activity.findViewById(R.id.cookie_half_right);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    touchX = motionEvent.getX();
                    touchY = motionEvent.getY();
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if((motionEvent.getX()>touchX)&&(Utils.isNear(touchY, motionEvent.getY(), 200))){
                        ImageButton imageButtonHalfRight = (ImageButton)activity.findViewById(R.id.cookie_half_right);
                        new CookieAnimation(ButtonListener.this).animationMoveOutRight(imageButtonHalfRight);
                        CookieUtils.halfCount--;
                        new ProphecyUtils(ButtonListener.this, activity).prophecyCheck();
                    }
                }
                return false;
            }
        });
    }

}
