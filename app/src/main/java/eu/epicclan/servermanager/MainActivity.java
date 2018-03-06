package eu.epicclan.servermanager;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import eu.epicclan.servermanager.manager.ServerManager;
import eu.epicclan.servermanager.utils.Server;
import eu.epicclan.servermanager.utils.ExtraButton;
import eu.epicclan.servermanager.utils.ServerLayout;

public class MainActivity extends AppCompatActivity {

    public static ServerManager manager;
    public static MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        new ServerManager();
    }

    public static void buildLayout(){
        RelativeLayout layout = new RelativeLayout(main);
        int count = 0;

        for(final Server s : manager.serverList){
            ServerLayout sLayout = new ServerLayout(main);
            buildServer(sLayout, s);

            sLayout.setY(count * 145);
            layout.addView(sLayout);

            count++;
        }

        main.setContentView(layout);
    }





    public static void buildServer(ServerLayout layout, Server s){
        buildButtons(layout, s);
        buildText(layout, s);
        buildLines(layout);
    }

    public static void buildButtons(ServerLayout layout, Server s){
        layout.stopButton = new ExtraButton(main, main.getResources().getDrawable(R.drawable.button_stop), "sh " + s.stopPath);
        layout.stopButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.ALIGN_PARENT_RIGHT).build());
        layout.addView(layout.stopButton);

        layout.startButton = new ExtraButton(main, main.getResources().getDrawable(R.drawable.button_start), "sh " + s.startPath);
        layout.startButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(layout.stopButton.getId(), RelativeLayout.LEFT_OF).build());
        layout.addView(layout.startButton);
    }

    public static void buildText(ServerLayout layout, Server s){
        layout.desc = new TextView(main);
        layout.desc.setText(s.desc);
        layout.desc.setLayoutParams(new ParamsBuilder(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).alignTo(layout.startButton.getId(), RelativeLayout.ALIGN_BOTTOM).build());
        layout.desc.setId(RelativeLayout.generateViewId());

        layout.name = new TextView(main);
        layout.name.setText(s.name);
        layout.name.setTypeface(layout.name.getTypeface(), Typeface.BOLD);
        layout.name.setLayoutParams(new ParamsBuilder(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).alignTo(layout.desc.getId(), RelativeLayout.ABOVE).build());

        layout.addView(layout.name);
        layout.addView(layout.desc);
    }

    public static void buildLines(ServerLayout layout){
        layout.partition = new View(main);
        layout.partition.setBackgroundColor(Color.BLACK);
        layout.partition.setLayoutParams(new ParamsBuilder(RelativeLayout.LayoutParams.MATCH_PARENT, 5).alignTo(layout.startButton.getId(), RelativeLayout.BELOW).build());
        layout.addView(layout.partition);
    }

}
