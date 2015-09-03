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
    private TextView textView;

    public ToastCustom(Context baseContext, Activity activity) {
        super(baseContext);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup)activity.findViewById(R.id.toast_layout));
        setTextView((TextView) view.findViewById(R.id.toast_text));
        setToast(new Toast(activity.getApplicationContext()));
        getToast().setGravity(Gravity.BOTTOM, 0, 0);
        getToast().setView(view);
    }

    public void showText(String text, Integer duration) {
        getTextView().setText(text);
        getToast().setDuration(duration);
        getToast().show();
    }

    public void cancel(){
        toast.cancel();
    }

    public Toast getToast() {
        return toast;
    }

    public void setToast(Toast toast) {
        this.toast = toast;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
