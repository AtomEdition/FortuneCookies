package com.atomEdition.FortuneCookies.promotion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.CookiesActivity;
import com.atomEdition.FortuneCookies.R;

/**
 * Created by FruityDevil on 07.08.2015.
 */
public class FollowActivity extends Activity {

    private static final String URL_FACEBOOK = "https://www.facebook.com/groups/853992711329970";
    private static final String URL_VK = "https://vk.com/public83167916";
    private static final String URL_TWITTER = "https://twitter.com/AtomEdition";
    private static final String URL_INSTAGRAM = "https://instagram.com/atom_edition";
    private static final String URL_YOUTUBE = "https://youtube.com/channel/UC-huKN9H1KP172bb4GaWRhg";
    private static final String URL_GOOGLE_PLAY = "https://play.google.com/store/search?q=atomEdition";

    public void follow(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        try{
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(getBaseContext(), R.string.connection_failure,
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_layout);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CookiesActivity.class);
        startActivity(intent);
        finish();
    }

    public void onButtonBackClick(View view){
        onBackPressed();
    }

    public void onButtonFacebookClick(View view){
        follow(URL_FACEBOOK);
    }

    public void onButtonVkClick(View view){
        follow(URL_VK);
    }

    public void onButtonTwitterClick(View view){
        follow(URL_TWITTER);
    }

    public void onButtonInstagramClick(View view){
        follow(URL_INSTAGRAM);
    }

    public void onButtonYouTubeClick(View view){
        follow(URL_YOUTUBE);
    }

    public void onButtonGooglePlayClick(View view){
        follow(URL_GOOGLE_PLAY);
    }
}
