package com.atomEdition.FortuneCookies.ad;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import com.atomEdition.FortuneCookies.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by FruityDevil on 11.01.2016.
 */
public class AdService {
    private static AdService instance;
    private InterstitialAd interstitialAd;

    private AdService(){
    }

    public static AdService getInstance(){
        if (instance == null) {
            instance = new AdService();
        }
        return instance;
    }

    public void showBanner(Activity activity){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            AdView adView = (AdView) activity.findViewById(R.id.adView);
            adView.loadAd(new AdRequest.Builder().build());
        }
    }

    public void loadInterstitial(Context context){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId("ca-app-pub-9550981282535152/8759177420");
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    public void loadInterstitialAndShow(Context context){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    displayInterstitial();
                }
            });
            interstitialAd.setAdUnitId("ca-app-pub-9550981282535152/8759177420");
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    public void displayInterstitial(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            interstitialAd.show();
        }
    }
}
