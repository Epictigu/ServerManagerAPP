package eu.epicclan.servermanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import eu.epicclan.servermanager.DelayedRunnable;
import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.ParamsBuilder;
import eu.epicclan.servermanager.manager.LayoutManager;

public class ServerLayout extends RelativeLayout implements View.OnClickListener{

    public ExtraButton startButton;
    public ExtraButton stopButton;

    public ImageView pic;
    public TextView name;
    public Server s;

    public RoundedBitmapDrawable headPic;

    public ServerLayout(Context context, Server server){
        super(context);
        this.s = server;
        this.setClickable(true);
        this.setOnClickListener(this);
    }

    public void buildServer(){
        new DelayedRunnable(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = convertUrlToBitmap(s.picURL);
                headPic = RoundedBitmapDrawableFactory.create(getResources(), bm);
                headPic.setCornerRadius(60F);
                headPic.setCircular(true);
            }
        }, new Runnable() {
            @Override
            public void run() {
                pic = new ImageView(MainActivity.main);
                pic.setImageDrawable(headPic);
                pic.setId(generateViewId());
                pic.setLayoutParams(new ParamsBuilder(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT).alignTo(-1, ALIGN_PARENT_LEFT).build());
                pic.setX(40);
                addView(pic);

                name = new TextView(MainActivity.main);
                name.setText(s.name);
                name.setTextColor(Color.WHITE);
                name.setTypeface(name.getTypeface(), Typeface.BOLD);
                name.setY(30); name.setX(80);
                LayoutParams lParams = new ParamsBuilder(1000, LayoutParams.WRAP_CONTENT).alignTo(pic.getId(), RIGHT_OF).build();
                name.setLayoutParams(lParams);
                addView(name);
            }
        }).execute();
    }

    public Bitmap convertUrlToBitmap(String src){
        try {
            return BitmapFactory.decodeStream(new URL(src).openConnection().getInputStream());
        } catch(IOException e){
            System.out.println("An error has occurred trying to load the icon for the \"" + s.name + "\" - Server!");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        LayoutManager.setLastClicked(this);
        LayoutManager.repaintAvailableTasks();
    }
}
