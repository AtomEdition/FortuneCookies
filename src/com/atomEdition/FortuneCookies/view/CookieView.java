package com.atomEdition.FortuneCookies.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.services.GetUtils;

/**
 * Created by FruityDevil on 17.12.14.
 */
public class CookieView extends ContextWrapper {

    Activity activity;

    public CookieView(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public void setClickable(int id) {
        View view = this.activity.findViewById(id);
        view.setClickable(true);
    }

    public void setUnclickable(int id) {
        View view = this.activity.findViewById(id);
        view.setClickable(false);
    }

    public void makeClearAndInvisible(int id){
        View view = this.activity.findViewById(id);
        view.clearAnimation();
        view.setClickable(true);
        view.setVisibility(View.INVISIBLE);
    }

    public void makeVisible(int id){
        View view = this.activity.findViewById(id);
        view.setVisibility(View.VISIBLE);
    }

    public void clearBackground(int id){
        View view = this.activity.findViewById(id);
        view.setBackgroundDrawable(null);
    }

    public void clearText(int id){
        TextView view = (TextView)this.activity.findViewById(id);
        view.setText("");
    }

    public void hideCookies(){
        for(int i=0; i< Utils.COOKIES_COUNT; i++)
            makeClearAndInvisible(new GetUtils(this).getCookie(i));
    }

    public void hideFinalCookies(){
        makeClearAndInvisible(R.id.cookie_final);
        makeClearAndInvisible(R.id.cookie_half_left);
        makeClearAndInvisible(R.id.cookie_half_right);
        makeClearAndInvisible(R.id.image_crumbs);
        setUnclickable(R.id.prophecy_image);
    }

    public void hideProphecy(){
        clearBackground(R.id.prophecy_image);
        clearText(R.id.prophecy_text);
        makeClearAndInvisible(R.id.prophecy_layout);
    }

    public void hideButtons(){
        makeClearAndInvisible(R.id.button_history);
        makeClearAndInvisible(R.id.button_settings);
        makeClearAndInvisible(R.id.button_follow);
        makeClearAndInvisible(R.id.button_video);
        makeClearAndInvisible(R.id.text_cooldown);
        makeClearAndInvisible(R.id.promotion_button);
    }

    public void showButtons(){
        makeVisible(R.id.button_history);
        makeVisible(R.id.button_settings);
        makeVisible(R.id.button_follow);
        makeVisible(R.id.button_video);
        makeVisible(R.id.text_cooldown);
        makeVisible(R.id.promotion_button);
    }

    public void setFont(){
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/SegoePrint.ttf");
        TextView textView = (TextView)activity.findViewById(R.id.prophecy_text);
        textView.setTypeface(type);
    }
}
