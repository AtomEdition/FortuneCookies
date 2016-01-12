package com.atomEdition.FortuneCookies.promotion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.R;

/**
 * Created by FruityDevil on 11.01.2016.
 */
public class PromotionButtonController {

    private static PromotionButtonController instance;
    private static Activity currentActivity;

    private final String URL_CAT_CLICKER = "https://play.google.com/store/apps/details?id=com.AtomEdition.CatClicker";
    private final String URL_ADOPT = "https://play.google.com/store/apps/details?id=com.atomEdition.mexicanAdopt";
    private final String URL_HIT_THE_NAIL = "https://play.google.com/store/apps/details?id=com.AtomEdition.HitTheNail";
    private final String URL_MOMMY_BALLS = "https://play.google.com/store/apps/details?id=com.AtomEdition.MommyBalls";

    private final int TIMER_INTERVAL = 1000;
    CountDownTimer countDownTimer;
    private boolean timerCanBeRestarted = true;

    private int currentIndex = 0;
    private String[] urls = {URL_CAT_CLICKER, URL_ADOPT, URL_HIT_THE_NAIL, URL_MOMMY_BALLS};
    private int[] imageIds = {R.drawable.promotion_cat_clicker, R.drawable.promotion_adopt,
            R.drawable.promotion_hit_the_nail, R.drawable.promotion_mommy_balls};

    public static PromotionButtonController getInstance(Activity activity){
        if (instance == null) {
            instance = new PromotionButtonController();
        }
        currentActivity = activity;
        return instance;
    }

    private PromotionButtonController(){
        countDownTimer = new CountDownTimer(TIMER_INTERVAL + 50, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentIndex = currentIndex >= urls.length - 1
                        ? 0
                        : currentIndex + 1;
                switchImage(currentIndex);
            }

            @Override
            public void onFinish() {
                if (timerCanBeRestarted) {
                    this.start();
                } else {
                    timerCanBeRestarted = true;
                }
            }
        };
    }

    private void switchImage(int index){
        ImageButton imageButton = (ImageButton)currentActivity.findViewById(R.id.promotion_button);
        imageButton.setBackgroundResource(imageIds[index]);
    }

    public void makeUsFamous(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urls[currentIndex]));
        try{
            currentActivity.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(currentActivity.getBaseContext(), R.string.connection_failure,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void startTimer(){
        timerCanBeRestarted = true;
        countDownTimer.start();
    }

    public void breakTimer(){
        timerCanBeRestarted = false;
    }
}
