package com.atomEdition.FortuneCookies.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.animation.CookieAnimation;
import com.atomEdition.FortuneCookies.model.Prophecy;
import com.atomEdition.FortuneCookies.toast.ToastCustom;
import com.atomEdition.FortuneCookies.view.CookieView;
import com.atomEdition.FortuneCookies.view.TutorialView;

import java.util.Date;

/**
 * Created by FruityDevil on 23.12.14.
 */
public class ProphecyUtils extends ContextWrapper {

    Activity activity;
    private static SharedPreferences sharedPreferences;

    public ProphecyUtils(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public void prophecyCheck(){
        ImageButton imageButton = (ImageButton)activity.findViewById(R.id.prophecy);
        if(CookieUtils.halfCount<=0){
            new TutorialView(this, activity).checkAndShow(R.string.tutorial_zoom);
            imageButton.setClickable(true);
        }
    }

    public void updateProphecies(){
        Utils.PROPHECIES.getLast().setDate(new Date());
        saveProphecies();
    }

    private void saveProphecies(){
        sharedPreferences = this.getSharedPreferences(Utils.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Utils.PREFERENCES_LIST_SIZE, Utils.PROPHECIES.size());
        for(int i=0; i<Utils.PROPHECIES.size(); i++)
            editor.putString(Utils.PREFERENCES_LIST_ELEMENT + i, Utils.PROPHECIES.get(i).getStringToSave());
        editor.commit();
    }

    public String getProphecy(Integer category){
        int prophecyCategory;
        if(category!=0)
            prophecyCategory = getProphecyCategory(category);
        else
            prophecyCategory = getProphecyCategory(Utils.random.nextInt(Utils.PROPHECY_CATEGORIES_COUNT) + 1);
        int randomProphecy = Utils.random.nextInt(getResources().getStringArray(prophecyCategory).length);
        return getResources().getStringArray(prophecyCategory)[randomProphecy];
    }

    private Integer getProphecyCategory(Integer categoryId){
        return getResources().getIdentifier(Utils.PROPHECY_CATEGORY_NAME + categoryId, "array", getPackageName());
    }

    public static void readProphecies(Context context){
        sharedPreferences = context.getSharedPreferences(Utils.PREFERENCES_NAME, 0);
        int size = sharedPreferences.getInt(Utils.PREFERENCES_LIST_SIZE, 0);
        Utils.PROPHECIES.clear();
        if(size > 0)
            for(int i=0; i<size; i++)
                Utils.PROPHECIES.add(new Prophecy(sharedPreferences.getString(Utils.PREFERENCES_LIST_ELEMENT + i, "")));
    }

    public void checkProphecyAlreadyGot(){
           if(isProphecyAlreadyGot(this)){
                CookieUtils.isCookiesPlaced = true;
                showProphecyAlreadyGot();
                new CookieView(this, activity).showButtons();
                ActivityUtils.coolDownTimer.start();
                new CookieAnimation(this).setProphecyIdleAnimation();
            }
            else
                ActivityUtils.toastCustom = new ToastCustom(this, activity, getResources().getString(R.string.cookies_ready),
                        Toast.LENGTH_SHORT);
    }

    public static boolean isProphecyAlreadyGot(Context context){
        readProphecies(context);
        if(Utils.PROPHECIES.size() > 0)
            if(new Date().getTime() - Utils.PROPHECIES.getLast().getDate().getTime() < Utils.COOLDOWN)
                return true;
        return false;
    }

    private void showProphecyAlreadyGot(){
        new CookieUtils(this, activity).setCookieFinalCategory(Utils.PROPHECIES.getLast().getProphecyType());
        new CookieView(this, activity).makeVisible(R.id.prophecy_layout);
    }

}
