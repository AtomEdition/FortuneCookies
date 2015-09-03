package com.atomEdition.FortuneCookies.services;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Display;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.row.RowHistoryAdapter;
import com.atomEdition.FortuneCookies.toast.ToastCustom;

import java.util.Date;

/**
 * Created by FruityDevil on 23.12.14.
 */
public class ActivityUtils extends ContextWrapper {

    public static Activity activity;
    public static Vibrator vibrator;
    public static ToastCustom toastCustomTop;
    public static ToastCustom toastCustomBottom;
    public static CountDownTimer countDownTimer;
    public static CountDownTimer coolDownTimer;
    public static boolean flagTicking = false;
    public static boolean flagCooldown = false;
    public static int cookiesOnTableAvailable = Utils.COOKIES_COUNT-1;

    public ActivityUtils(Context baseContext){
        super(baseContext);
        toastCustomBottom = new ToastCustom(this, activity);
        toastCustomTop = new ToastCustom(this, activity);
        toastCustomTop.getToast().setGravity(Gravity.TOP, 0, 0);
    }

    public static void stopCountDownTimer() {
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    public static boolean isFlagCooldown() {
        return flagCooldown;
    }

    public static void setFlagCooldown(boolean flagCooldown) {
        ActivityUtils.flagCooldown = flagCooldown;
    }

    public int convertToPx(Integer valueDp){
        return (int)(valueDp * getApplicationContext().getResources().getDisplayMetrics().density);
    }

    public void setScreenSize(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Utils.SCREEN_HEIGHT = (display.getHeight() - convertToPx(Utils.COOKIE_SIZE) - convertToPx(Utils.BORDER_SIZE));
        Utils.SCREEN_WIDTH = (display.getWidth() - convertToPx(Utils.COOKIE_SIZE));
        Utils.SCREEN_LIMIT = Utils.SCREEN_HEIGHT + Utils.SCREEN_WIDTH;
    }

    public void setListHistory(){
        ListView listView = (ListView)activity.findViewById(R.id.list_view);
        listView.setAdapter(new RowHistoryAdapter(this, activity, Utils.descendingList(Utils.PROPHECIES)));
    }

    public void setDefaultValues(){
        CookieUtils.isCookiesPlaced = false;
        flagTicking = false;
        setFlagCooldown(false);
        cookiesOnTableAvailable = Utils.COOKIES_COUNT-1;
        CookieUtils.halfCount = 2;
    }

    public void setVibrators(){
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setTimers(){
        countDownTimer = new CountDownTimer(Utils.SHAKE_TIME, 1) {
            @Override
            public void onTick(long l) {
                flagTicking = true;
                // Need to be overridden.
            }

            @Override
            public void onFinish() {
                CookieUtils.isCookiesPlaced = true;
                flagTicking = false;
                Utils.clearPositions();
            }
        };
        coolDownTimer = new CountDownTimer(Utils.COOLDOWN, 1000) {
            @Override
            public void onTick(long l) {
                try {
                    TextView textView = (TextView)activity.findViewById(R.id.text_cooldown);
                    textView.setText(getCoolDown());
                } catch (Exception e){}
            }

            @Override
            public void onFinish() {
                new CookieUtils(ActivityUtils.this, activity).prepareCookies();
                setFlagCooldown(false);
            }
        };
    }

    private String getCoolDown(){
        Long time = Utils.COOLDOWN - (new Date().getTime() - Utils.PROPHECIES.get(Utils.PROPHECIES.size() - 1).getDate().getTime());
        if (time <= 0) {
            coolDownTimer.cancel();
            coolDownTimer.onFinish();
        }
        time /= 1000;
        return format(time / 3600) + ":" + format (time / 60 % 60) + ":" + format(time % 60);
    }

    private String format(long value){
        return (value < 10) ? "0" + value : "" + value;
    }

    public void showTextWithToast(String string) {
        toastCustomBottom.showText(string, Toast.LENGTH_SHORT);
    }

    public void dropCoolDown() {
        coolDownTimer.cancel();
        coolDownTimer.onFinish();
        CookieUtils cookieUtils = new CookieUtils(getBaseContext(), activity);
        cookieUtils.prepareValues();
        cookieUtils.hideScreenObjects();
        showTextWithToast(getResources().getString(R.string.cookies_ready));
    }

    public boolean isVideoCanBeShown() {
        return Utils.PROPHECIES.size() < 2
                || (Utils.PROPHECIES.get(Utils.PROPHECIES.size() - 1).getDate().getTime()
                - Utils.PROPHECIES.get(Utils.PROPHECIES.size() - 2).getDate().getTime()
                > Utils.COOLDOWN);
    }
}
