package com.atomEdition.FortuneCookies;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import com.atomEdition.FortuneCookies.ad.AdService;
import com.atomEdition.FortuneCookies.animation.CookieAnimation;
import com.atomEdition.FortuneCookies.model.Position;
import com.atomEdition.FortuneCookies.model.Prophecy;
import com.atomEdition.FortuneCookies.promotion.FollowActivity;
import com.atomEdition.FortuneCookies.promotion.PromotionButtonController;
import com.atomEdition.FortuneCookies.services.*;
import com.atomEdition.FortuneCookies.settings.Accelerometer;
import com.atomEdition.FortuneCookies.settings.ButtonListener;
import com.atomEdition.FortuneCookies.view.CookieView;
import com.atomEdition.FortuneCookies.view.LayoutView;
import com.atomEdition.FortuneCookies.view.TutorialView;
import com.atomEdition.FortuneCookies.widget.CookieWidget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vungle.publisher.VunglePub;

import java.net.ConnectException;
import java.util.Date;

public class CookiesActivity extends Activity implements SensorEventListener {

    private final VunglePub vunglePub = VunglePub.getInstance();
    private final String vungleAppId = "com.atomEdition.FortuneCookies";
    private ActivityUtils activityUtils;

    /**
     *
     * Called when the activity is first created.
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LayoutView(this, this).toCalibrateLayout();
        initialization();
        vunglePub.init(this, vungleAppId);
    }

    private void initialization() {
        ActivityUtils.activity = this;
        setActivityUtils(new ActivityUtils(this));
        new Accelerometer(this).setSensors();
        getActivityUtils().setVibrators();
        getActivityUtils().setTimers();
        new CookieUtils(this, this).prepareCookies();
        getActivityUtils().setScreenSize();
        new Accelerometer(this).loadCalibration();
        new TutorialView(this, this).checkIsTutorialNeeded();
        sendBroadcast(CookieWidget.updateWidget(getApplicationContext(), getApplication()));
        if (Utils.SHAKE_THRESHOLD == 0d) {
            ActivityUtils.toastCustomBottom.showText(getString(R.string.calibrate_start), Toast.LENGTH_LONG);
            new LayoutView(this, this).toCalibrateLayout();
        } else {
            adInitialization();
            PromotionButtonController.getInstance(this).startTimer();
        }
    }

    private void adInitialization(){
        AdService adService = AdService.getInstance();
        adService.showBanner(this);
        if (Utils.PROPHECIES.size() > 0 &&
                new Date().getTime() - Utils.PROPHECIES.getLast().getDate().getTime() > Utils.COOLDOWN_BANNER &&
                Utils.PROPHECIES.size() > 0) {
            adService.loadInterstitialAndShow(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Accelerometer(this).registerSensorListener(this);
        vunglePub.onResume();
    }

    @Override
    public void onPause() {
        try {
            new Accelerometer(this).unRegisterSensorListener(this);
            ActivityUtils.toastCustomBottom.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
        vunglePub.onPause();
    }

    @Override
    public void onBackPressed() {
        if (ActivityUtils.toastCustomTop != null) {
            ActivityUtils.toastCustomTop.cancel();
        }
        if (ActivityUtils.toastCustomBottom != null) {
            ActivityUtils.toastCustomBottom.cancel();
        }
        super.onBackPressed();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (Utils.SHAKE_THRESHOLD == 0d)
            new Accelerometer(this).calibrate(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        else if (sensorEvent.sensor.getType() == SensorManager.SENSOR_DELAY_GAME) {
            if (new Accelerometer(this).isShakeEnough(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2])) {
                onShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Must be overridden.
    }

    private void onShake() {
        if (!CookieUtils.isCookiesPlaced) {
            if (!ActivityUtils.flagTicking) {
                new TutorialView(this, this).checkAndShow(R.string.tutorial_choose);
                ActivityUtils.countDownTimer.start();
            }
            ImageButton imageButton = (ImageButton) findViewById(new GetUtils(this).getCookie
                    (ActivityUtils.cookiesOnTableAvailable));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                    (new ActivityUtils(this).convertToPx(Utils.COOKIE_SIZE),
                            new ActivityUtils(this).convertToPx(Utils.COOKIE_SIZE));
            Position position = Utils.placeCookies();
            layoutParams.setMargins(position.getX(), position.getY(), 0, 0);

            ScaleAnimation animation = new ScaleAnimation(3, 1, 3, 1,
                    new ActivityUtils(this).convertToPx(Utils.COOKIE_SIZE) / 2,
                    new ActivityUtils(this).convertToPx(Utils.COOKIE_SIZE) / 2);
            animation.setDuration(Utils.ANIMATION_APPEAR_DURATION);
            imageButton.setLayoutParams(layoutParams);
            imageButton.startAnimation(animation);
            imageButton.setVisibility(View.VISIBLE);
            ActivityUtils.cookiesOnTableAvailable--;
            if (ActivityUtils.cookiesOnTableAvailable < 0)
                CookieUtils.isCookiesPlaced = true;
            ActivityUtils.vibrator.vibrate(Utils.VIBRATE_FALL_TIME);
        }
    }

    private void moveCookiesOut(int exceptValue) {
        for (int i = 0; i < Utils.COOKIES_COUNT; i++) {
            if (new GetUtils(this).getCookie(i) != exceptValue) {
                ImageButton imageButton = (ImageButton) findViewById(new GetUtils(this).getCookie(i));
                if (imageButton.getVisibility() == View.VISIBLE)
                    imageButton.startAnimation(new CookieAnimation(CookiesActivity.this).getMoveOutAnimation(findViewById(new GetUtils(this).getCookie(i))));
            }
        }
    }

    public void onCookieClick(View view) {
        int categoryId = new CookieUtils(this, this).getCategoryId(view.getId());
        ActivityUtils.stopCountDownTimer();
        Utils.PROPHECIES.add(new Prophecy(new Date(), new ProphecyUtils(this, this).getProphecy(categoryId), categoryId));
        moveCookiesOut(view.getId());
        ImageButton imageButtonFinal = (ImageButton) findViewById(R.id.cookie_final);
        view.startAnimation(new CookieAnimation(CookiesActivity.this).getMoveToCenterAnimation(view, imageButtonFinal));
        new CookieUtils(this, this).setCookieFinalCategory(categoryId);
    }

    public void onCookieFinalClick(View view) {
        new CookieView(this, this).makeClearAndInvisible(view.getId());
        new CookieView(this, this).makeVisible(R.id.cookie_half_left);
        new CookieView(this, this).makeVisible(R.id.cookie_half_right);
        new CookieView(this, this).makeVisible(R.id.image_crumbs);
        ImageView imageView = (ImageView) findViewById(R.id.image_crumbs);
        imageView.startAnimation(new CookieAnimation(this).animationCrumbs());
        new TutorialView(this, this).checkAndShow(R.string.tutorial_move_out);
        new CookieAnimation(this).setProphecyIdleAnimation();
        ActivityUtils.vibrator.vibrate(Utils.VIBRATE_CRACK_TIME);
        new CookieView(this, this).makeVisible(R.id.prophecy_layout);
    }

    public void onProphecyClick(View view) {
        if (!ActivityUtils.flagCooldown) {
            new ProphecyUtils(this, this).updateProphecies();
            sendBroadcast(CookieWidget.updateWidget(getApplicationContext(), getApplication()));
        }
        new CookieView(this, this).hideCookies();
        new CookieView(this, this).hideFinalCookies();
        new CookieView(this, this).showButtons();
        new TutorialView(this, this).checkAndShow(R.string.tutorial_last);
        Utils.IS_TUTORIAL_NEEDED = false;
        view.setClickable(false);
        view.setVisibility(View.VISIBLE);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.prophecy_zoom);
        scaleAnimation.setDuration(Utils.ANIMATION_PROPHECY_DURATION);
        scaleAnimation.setFillAfter(true);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.prophecy_layout);
        frameLayout.startAnimation(scaleAnimation);
        ActivityUtils.coolDownTimer.start();
        ActivityUtils.setFlagCooldown(true);
        if (getActivityUtils().isVideoCanBeShown()) {
            ActivityUtils.toastCustomTop.showText(getString(R.string.video_available), Toast.LENGTH_LONG);
        }
        PromotionButtonController.getInstance(this).startTimer();
    }

    public void onButtonHistoryClick(View view) {
        PromotionButtonController.getInstance(this).breakTimer();
        setContentView(R.layout.history);
        new ActivityUtils(this).setListHistory();
    }

    public void onButtonSettingsClick(View view) {
        PromotionButtonController.getInstance(this).breakTimer();
        setContentView(R.layout.settings);
    }

    public void backToMenu(View view) {
        new LayoutView(this, this).toMainLayout();
        new CookieUtils(this, this).prepareCookies();
        new ButtonListener(this, this).setImageButtonsListeners();
        PromotionButtonController.getInstance(this).startTimer();
    }

    public void onClickOther(View view) {
        Intent intent = new Intent(this, FollowActivity.class);
        startActivity(intent);
        finish();
    }

    public void onPromotionClick(View view) {
        PromotionButtonController.getInstance(this).makeUsFamous();
    }

    public void onCalibrateLayoutClick(View view) {
        new Accelerometer(this).setCalibration(Utils.SHAKE_THRESHOLD_DEFAULT);
        new LayoutView(this, this).toMainLayout();
        new CookieUtils(this, this).prepareCookies();
        if (!CookieUtils.isCookiesPlaced)
            ActivityUtils.toastCustomBottom.showText(getResources().getString(R.string.tutorial_shake), Toast.LENGTH_SHORT);
    }

    public void onSettingsCalibrateClick(View view) {
        PromotionButtonController.getInstance(this).breakTimer();
        new LayoutView(this, this).toCalibrateLayout();
        ActivityUtils.toastCustomBottom.showText(getResources().getString(R.string.calibrate_start), Toast.LENGTH_LONG);
    }

    public void onSettingsAboutClick(View view) {
        setContentView(R.layout.about);
        AboutService aboutService = new AboutService(this);
        for (int i = 0; i < Utils.PROPHECY_CATEGORIES_COUNT_TOTAL; i++) {
            int aboutTextId = getResources().getIdentifier(Utils.ABOUT_TEXT_VIEW_IDENTIFIER + i, "id", getPackageName());
            TextView textView = (TextView) findViewById(aboutTextId);
            textView.setText(aboutService.getAboutTextByCategory(i));
        }
    }

    public void onWatchVideoClick(View view) {
        try {
            if (getActivityUtils().isVideoCanBeShown()
                    && Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO
                    && getActivityUtils().isNetworkConnected()) {
                try {
                    vunglePub.playAd();
                    getActivityUtils().dropCoolDown();
                } catch (Exception e) {
                    ActivityUtils.toastCustomTop.showText(getString(R.string.connection_failure), Toast.LENGTH_SHORT);
                }
            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
                ActivityUtils.toastCustomTop.showText(getString(R.string.video_error), Toast.LENGTH_SHORT);
            } else {
                ActivityUtils.toastCustomTop.showText(getString(R.string.video_already_watched), Toast.LENGTH_SHORT);
            }
        } catch (ConnectException e) {
            ActivityUtils.toastCustomTop.showText(getString(R.string.video_error), Toast.LENGTH_SHORT);
        }
    }

    public void onButtonTestClick(View view) {
        onShake();
    }

    public ActivityUtils getActivityUtils() {
        return activityUtils;
    }

    public void setActivityUtils(ActivityUtils activityUtils) {
        this.activityUtils = activityUtils;
    }
}
