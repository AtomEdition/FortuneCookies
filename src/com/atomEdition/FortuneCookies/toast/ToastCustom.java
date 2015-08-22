package com.atomEdition.FortuneCookies.toast;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.R;

/**
 * Created by FruityDevil on 31.12.2014.
 */
public class ToastCustom extends ContextWrapper {
    private Toast toast;

    public ToastCustom(Context baseContext, Activity activity, String text, Integer duration){
        super(baseContext);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup)activity.findViewById(R.id.toast_layout));
        TextView textView = (TextView)view.findViewById(R.id.toast_text);
        textView.setText(text);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public void cancel(){
        toast.cancel();
    }
}
