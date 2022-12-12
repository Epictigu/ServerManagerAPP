package eu.epicclan.servermanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import eu.epicclan.servermanager.DelayedRunnable;
import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.ParamsBuilder;
import eu.epicclan.servermanager.R;
import eu.epicclan.servermanager.components.OnlineStatus;
import eu.epicclan.servermanager.manager.ConManager;
import eu.epicclan.servermanager.manager.LayoutManager;

public class ServerLayout extends RelativeLayout implements View.OnClickListener{

    public ExtraButton startButton;
    public ExtraButton stopButton;

    public ImageView pic;
    public TextView name;
    public OnlineStatus onlineStatus;
    public Server s;

    public Button startB;
    public Button stopB;

    public RoundedBitmapDrawable headPic;

    public boolean clicked = false;

    public ServerLayout(Context context, Server server){
        super(context);
        this.s = server;
        this.setClickable(true);
        this.setOnClickListener(this);

        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        this.setLayoutParams(layoutParams);
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
                DisplayMetrics displayMetrics = new DisplayMetrics();
                MainActivity.main.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                int img_size = displayMetrics.widthPixels / 10;

                pic = new ImageView(MainActivity.main);
                pic.setImageDrawable(headPic);
                pic.setId(generateViewId());
                pic.setLayoutParams(new ParamsBuilder(img_size , img_size).alignTo(-1, ALIGN_PARENT_LEFT).build());
                pic.setX(40);
                addView(pic);

                name = new TextView(MainActivity.main);
                name.setText(s.name);
                name.setTextColor(Color.WHITE);
                name.setTypeface(name.getTypeface(), Typeface.BOLD);
                name.measure(0, 0);
                name.setY((img_size - name.getMeasuredHeight()) / 2);
                name.setX(80);
                LayoutParams lParams = new ParamsBuilder(1000, LayoutParams.WRAP_CONTENT).alignTo(pic.getId(), RIGHT_OF).build();
                name.setLayoutParams(lParams);
                addView(name);

                int statusColor = ContextCompat.getColor(getContext(), R.color.status_offline);

                if(s.status.equals("Online")){
                    statusColor = ContextCompat.getColor(getContext(), R.color.status_online);
                }
                onlineStatus = new OnlineStatus(MainActivity.main, null, s.status, statusColor);
                onlineStatus.setLayoutParams(new ParamsBuilder(200, 60).alignTo(-1, ALIGN_PARENT_RIGHT).build());
                onlineStatus.setX(-40);
                onlineStatus.setY((img_size - 60) / 2);
                addView(onlineStatus);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.main);
        builder.setTitle(s.name);
        builder.setMessage(s.desc);
        builder.setPositiveButton("Starten",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ConManager.getInstance().startServer(s.uuid.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        dialogInterface.cancel();
                    }
                });
        builder.setNegativeButton("Stoppen",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ConManager.getInstance().stopServer(s.uuid.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        dialogInterface.cancel();
                    }
                });
        builder.setNeutralButton("Abbrechen",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.WHITE);
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });
        alertDialog.show();
//        LayoutManager.repaintAvailableTasks();
    }

    public void hideButtons(){
    }

    public void showButtons(){

    }

}
