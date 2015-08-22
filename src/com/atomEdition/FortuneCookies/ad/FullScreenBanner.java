package com.atomEdition.FortuneCookies.ad;

/**
 * Created by FruityDevil on 23.12.14.
 */
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class FullScreenBanner extends ContextWrapper {

    private InterstitialAd interstitialAd;
    private Activity activity;

    public FullScreenBanner(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public void setAd(){
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9550981282535152/8759177420");
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        interstitialAd.loadAd(adRequest);
    }

    public void displayInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    activity.onBackPressed();
                }
            });
            interstitialAd.show();
        } else
            activity.onBackPressed();
    }
}
