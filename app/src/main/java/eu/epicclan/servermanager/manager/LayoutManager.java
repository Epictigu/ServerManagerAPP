package eu.epicclan.servermanager.manager;

import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.ParamsBuilder;
import eu.epicclan.servermanager.R;
import eu.epicclan.servermanager.utils.Category;
import eu.epicclan.servermanager.utils.ExtraButton;
import eu.epicclan.servermanager.utils.Server;
import eu.epicclan.servermanager.utils.ServerLayout;

public class LayoutManager {

    private static Map<Server, ServerLayout> availableTasks = new HashMap<Server, ServerLayout>();

    public static ServerLayout clickedOn = null;
    private static ExtraButton stopButton = null;
    private static ExtraButton startButton = null;


    public static void setLastClicked(ServerLayout clickedOn){
        if(clickedOn == LayoutManager.clickedOn){
            LayoutManager.clickedOn = null;
            return;
        }
        LayoutManager.clickedOn = clickedOn;
    }

    public static void displayAvailableTasks(){
        int serverAmount = 0;
        int seperatorAmount = 0;

        for(String str : MainActivity.categories.keySet()){
            Category c = MainActivity.categories.get(str);
            c.sep.setY(serverAmount * 115 + seperatorAmount * 80 + 30);
            MainActivity.layout.addView(c.sep);
            seperatorAmount++;

            for(Server s : c.servers){
                ServerLayout sLayout = new ServerLayout(MainActivity.main, s);
                sLayout.buildServer();
                sLayout.setY(serverAmount * 115 + seperatorAmount * 80 + 20);
                MainActivity.layout.addView(sLayout);
                availableTasks.put(s, sLayout);

                serverAmount++;
            }
        }
    }

    public static void repaintAvailableTasks(){
        int serverAmount = 0;
        int seperatorAmount = 0;
        int openTasks = 0;

        for(String str : MainActivity.categories.keySet()){
            Category c = MainActivity.categories.get(str);
            c.sep.setY(serverAmount * 115 + seperatorAmount * 80 + 30 + openTasks * 200);
            seperatorAmount++;

            for(Server s : c.servers){
                ServerLayout sLayout = availableTasks.get(s);
                sLayout.setY(serverAmount * 115 + seperatorAmount * 80 + 20 + openTasks * 200);
                serverAmount++;

                if(clickedOn == sLayout) {
                    openTasks++;
                    stopButton.setY(serverAmount * 115 + seperatorAmount * 80 + 40);
                    stopButton.setVisibility(View.VISIBLE);
                    startButton.setY(serverAmount * 115 + seperatorAmount * 80 + 40);
                    startButton.setVisibility(View.VISIBLE);
                }
            }
        }
        if(clickedOn == null){
            stopButton.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    public static void initializeButtons(RelativeLayout layout){
        stopButton = new ExtraButton(MainActivity.main, MainActivity.main.getResources().getDrawable(R.drawable.button_stop), "stop");
        stopButton.setX(170F);
        stopButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.CENTER_HORIZONTAL).build());
        stopButton.setVisibility(View.INVISIBLE);
        layout.addView(stopButton);

        startButton = new ExtraButton(MainActivity.main, MainActivity.main.getResources().getDrawable(R.drawable.button_start), "start");
        startButton.setX(-170F);
        startButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.CENTER_HORIZONTAL).build());
        startButton.setVisibility(View.INVISIBLE);
        layout.addView(startButton);
    }

}
