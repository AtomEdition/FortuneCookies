package com.atomEdition.FortuneCookies.services;

import android.content.Context;
import android.content.ContextWrapper;
import com.atomEdition.FortuneCookies.Utils;

/**
 * Created by FruityDevil on 18.12.14.
 */
public class GetUtils extends ContextWrapper {

    public GetUtils(Context baseContext){
        super(baseContext);
    }

    public Integer getCookie(int cookieId){
        return getResources().getIdentifier(Utils.COOKIE_NAME + cookieId, "id", getPackageName());
    }
}
