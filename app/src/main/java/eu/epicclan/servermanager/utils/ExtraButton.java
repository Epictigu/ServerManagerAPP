package eu.epicclan.servermanager.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import java.io.IOException;

import eu.epicclan.servermanager.DelayedRunnable;
import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.manager.LayoutManager;

public class ExtraButton extends AppCompatImageButton{

    private Drawable icon;
    public boolean pressed = false;

    public ExtraButton(Context context, Drawable icon, String cmd){
        super(context);
        this.icon = icon;
        setId(generateViewId());

        if(cmd != null){
            setUpCommand(cmd);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint buttonPaint = new Paint();
        buttonPaint.setColor(Color.rgb(43, 43, 43));
        buttonPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, getLayoutParams().width, getLayoutParams().height, buttonPaint);
        icon.setBounds(0, 0, 140, 140);
        if(pressed){
            icon.setColorFilter(new LightingColorFilter(Color.rgb(0, 236, 6), Color.WHITE));
        } else {
            icon.setColorFilter(null);
        }
        icon.draw(canvas);
    }

    public void setUpCommand(final String cmd){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed = true;
                new DelayedRunnable(null, new Runnable() {
                    @Override
                    public void run() {
                        pressed = false;
                        invalidate();
                    }
                }).setDelay(200l).execute();

                try {
                    if(cmd.equalsIgnoreCase("start")){
                        MainActivity.manager.conM.exec("sh " + LayoutManager.clickedOn.s.startPath);
                    } else if(cmd.equalsIgnoreCase("stop")){
                        MainActivity.manager.conM.exec("sh " + LayoutManager.clickedOn.s.stopPath);
                    }
                } catch(IOException e){e.printStackTrace();}
            }
        });
    }

}
