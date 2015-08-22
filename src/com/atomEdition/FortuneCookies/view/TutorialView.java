package com.atomEdition.FortuneCookies.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.toast.ToastCustom;
import com.atomEdition.FortuneCookies.utils.ActivityUtils;

import java.util.Date;

/**
 * Created by FruityDevil on 18.12.14.
 */
public class TutorialView extends ContextWrapper {

    private Activity activity;

    public TutorialView(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public boolean checkIsTutorialNeeded(){
        Utils.IS_TUTORIAL_NEEDED = !(Utils.PROPHECIES.size() > 0);
        return !(Utils.PROPHECIES.size() > 0);
    }

    public void checkAndShow(Integer messageId){
        if(Utils.IS_TUTORIAL_NEEDED)
            if((Utils.PROPHECIES.size() < 1) || (new Date().getTime() - Utils.PROPHECIES.getLast().getDate().getTime() < Utils.COOLDOWN)){
                ActivityUtils.toastCustom = new ToastCustom(this, activity, getResources().getString(messageId),
                        Toast.LENGTH_SHORT);
            }
    }
}