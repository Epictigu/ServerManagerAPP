package eu.epicclan.servermanager.utils;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ServerLayout extends RelativeLayout{

    public ExtraButton startButton;
    public ExtraButton stopButton;

    public TextView name;
    public TextView desc;

    public View partition;

    public ServerLayout(Context context){
        super(context);
    }

}
