package com.atomEdition.FortuneCookies.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.atomEdition.FortuneCookies.R;

/**
 * Created by FruityDevil on 24.12.2014.
 */
public class LayoutView extends ContextWrapper {

    Activity activity;

    public LayoutView(Context baseContext, Activity activity){
        super(baseContext);
        this.activity = activity;
    }

    public void toCalibrateLayout(){
        activity.setContentView(R.layout.calibrate);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.background_texture_1);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        RelativeLayout relativeLayout = (RelativeLayout)activity.findViewById(R.id.calibrate_layout);
        relativeLayout.setBackgroundDrawable(bitmapDrawable);
    }

    public void toMainLayout(){
        activity.setContentView(R.layout.main);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.background_texture);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        RelativeLayout relativeLayout = (RelativeLayout)activity.findViewById(R.id.main_layout);
        relativeLayout.setBackgroundDrawable(bitmapDrawable);
        bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.border_texture);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        ImageView imageView = (ImageView)activity.findViewById(R.id.border_top);
        imageView.setBackgroundDrawable(bitmapDrawable);
        imageView = (ImageView)activity.findViewById(R.id.border_bottom);
        imageView.setBackgroundDrawable(bitmapDrawable);
    }
}
