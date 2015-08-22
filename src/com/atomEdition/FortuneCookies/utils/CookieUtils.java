package com.atomEdition.FortuneCookies.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.settings.ButtonListener;
import com.atomEdition.FortuneCookies.view.CookieView;
import com.atomEdition.FortuneCookies.view.LayoutView;

/**
 * Created by FruityDevil on 23.12.14.
 */
public class CookieUtils extends ContextWrapper {

    private Activity activity;
    public static Integer halfCount = 2;
    public static boolean isCookiesPlaced = false;

    public CookieUtils(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public Integer getCookieImage(int categoryId){
        return getResources().getIdentifier(Utils.COOKIE_NAME + categoryId, "drawable", getPackageName());
    }

    private Integer getCookieHalf(int categoryId, String halfType){
        return getResources().getIdentifier(halfType + categoryId, "drawable", getPackageName());
    }

    private Integer getProphecyImage(int categoryId){
        return getResources().getIdentifier(Utils.PROPHECY_NAME + categoryId, "drawable", getPackageName());
    }

    private Integer getCrumbsImage(int categoryId){
        return getResources().getIdentifier(Utils.CRUMBS_NAME + categoryId, "drawable", getPackageName());
    }

    public void setCookieFinalCategory(int categoryId){
        ImageButton imageButton = (ImageButton)activity.findViewById(R.id.cookie_half_right);
        imageButton.setBackgroundResource(getCookieHalf(categoryId, Utils.COOKIE_HALF_RIGHT_NAME));
        imageButton = (ImageButton)activity.findViewById(R.id.cookie_half_left);
        imageButton.setBackgroundResource(getCookieHalf(categoryId, Utils.COOKIE_HALF_LEFT_NAME));
        imageButton = (ImageButton)activity.findViewById(R.id.prophecy);
        imageButton.setBackgroundResource(getProphecyImage(categoryId));
        ImageView imageView = (ImageView)activity.findViewById(R.id.image_crumbs);
        imageView.setBackgroundResource(getCrumbsImage(categoryId));
        TextView textView = (TextView)activity.findViewById(R.id.prophecy_text);
        textView.setText(Utils.PROPHECIES.getLast().getProphecy());
    }

    public Integer getCategoryId(int rId){
        switch (rId){
            case R.id.cookie_0:
                return Utils.COOKIES.get(0);
            case R.id.cookie_1:
                return Utils.COOKIES.get(1);
            case R.id.cookie_2:
                return Utils.COOKIES.get(2);
            case R.id.cookie_3:
                return Utils.COOKIES.get(3);
            case R.id.cookie_4:
                return Utils.COOKIES.get(4);
        }
        return 0;
    }

    public void setCookiesByCategories(){
        Utils.generateCookies();
        for(int i=0; i<Utils.COOKIES_COUNT; i++){
            ImageButton imageButton = (ImageButton)activity.findViewById(new GetUtils(this).getCookie(i));
            imageButton.setBackgroundResource(getCookieBackground(Utils.COOKIES.get(i)));
        }
    }


    public void prepareCookies(){
        new LayoutView(this, activity).toMainLayout();
        new ActivityUtils(this).setDefaultValues();
        ProphecyUtils.readProphecies(this);
        new CookieUtils(this, activity).setCookiesByCategories();
        new CookieView(this, activity).hideCookies();
        new CookieView(this, activity).hideFinalCookies();
        new CookieView(this, activity).hideButtons();
        new CookieView(this, activity).hideProphecy();
        new CookieView(this, activity).setFont();
        new ProphecyUtils(this, activity).checkProphecyAlreadyGot();
        new ButtonListener(this, activity).setImageButtonsListeners();
    }

    private Integer getCookieBackground(int backGroundId){
        return getResources().getIdentifier(Utils.COOKIE_NAME + backGroundId, "drawable", getPackageName());
    }
}
