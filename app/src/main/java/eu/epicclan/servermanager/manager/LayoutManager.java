package eu.epicclan.servermanager.manager;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import eu.epicclan.servermanager.DelayedRunnable;
import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.ParamsBuilder;
import eu.epicclan.servermanager.R;
import eu.epicclan.servermanager.utils.Category;
import eu.epicclan.servermanager.utils.ExtraButton;
import eu.epicclan.servermanager.utils.Server;
import eu.epicclan.servermanager.utils.ServerLayout;

public class LayoutManager {

    private static Map<Server, ServerLayout> availableTasks = new HashMap<Server, ServerLayout>();
    public static LinkedHashMap<String, Category> categories = new LinkedHashMap<String, Category>();;

    public static LinearLayout layout;
    public static ServerLayout clickedOn = null;
    private static ExtraButton stopButton = null;
    private static ExtraButton startButton = null;

    private static MainActivity a = MainActivity.main;



    public static void setLastClicked(ServerLayout clickedOn){
        if(clickedOn == LayoutManager.clickedOn){
            LayoutManager.clickedOn = null;
            return;
        }
        LayoutManager.clickedOn = clickedOn;
    }



    public static void buildLayout(){
        a.setContentView(R.layout.layout_main);
        layout = a.findViewById(R.id.serverView);

//        layout = new RelativeLayout(a);
//        layout.setBackgroundColor(a.getResources().getColor(R.color.colorBackground));
//
//        a.setContentView(layout);
//
        ImageButton reloadButton = (ImageButton) a.findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                reloadConfig();
            }
        });

        categories.put("General", new Category("General"));

        for(Server s : a.manager.serverList){
            if(!categories.containsKey(s.category)){
                categories.put(s.category, new Category(s.category));
            }
            categories.get(s.category).servers.add(s);
        }

        LayoutManager.initializeAvailableTasks();
//        LayoutManager.initializeButtons(layout);
    }

    public static void reloadConfig(){
        new DelayedRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    ConManager.getInstance().reloadConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                for(String str : categories.keySet()) {
                    Category c = categories.get(str);
                    layout.removeView(c.sep);
                    for(Server s : c.servers) {
                        ServerLayout sLayout = availableTasks.get(s);
                        layout.removeView(sLayout);
                    }
                }
                //stopButton.setVisibility(View.INVISIBLE);
                //startButton.setVisibility(View.INVISIBLE);

                categories.clear();
                categories.put("General", new Category("General"));

                for(Server s : a.manager.serverList){
                    if(!categories.containsKey(s.category)){
                        categories.put(s.category, new Category(s.category));
                    }
                    categories.get(s.category).servers.add(s);
                }

                LayoutManager.initializeAvailableTasks();
            }
        }).execute();
    }

    public static void buildLogin(){
        a.setContentView(R.layout.layout_password);
        a.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.login();
            }
        });

        LoginManager.automatedLogin();
    }



    private static void displayAvailableTasks(boolean initialize){
        int serverAmount = 0;
        int seperatorAmount = 0;
        int openTasks = 0;

        for(String str : categories.keySet()){
            Category c = categories.get(str);
            if(initialize){
                layout.addView(c.sep);
            }

            for(Server s : c.servers){
                ServerLayout sLayout = availableTasks.get(s);
                if(initialize){
                    sLayout = new ServerLayout(a.main, s);
                    sLayout.buildServer();
                    availableTasks.put(s, sLayout);

                    layout.addView(sLayout);
                } else {
                    if(clickedOn == sLayout) {
//                        stopButton.setY((serverAmount + 1) * 115 + seperatorAmount * 80 + 40);
//                        stopButton.setVisibility(View.VISIBLE);
//                        startButton.setY((serverAmount + 1) * 115 + seperatorAmount * 80 + 40);
//                        startButton.setVisibility(View.VISIBLE);
                    }
                }
            }

//            for(Server s : c.servers){
//                ServerLayout sLayout = availableTasks.get(s);
//                if(initialize){
//                    sLayout = new ServerLayout(a.main, s);
//                    sLayout.buildServer();
//                    availableTasks.put(s, sLayout);
//
//                    sLayout.setY(serverAmount * 115 + seperatorAmount * 80 + 20 + openTasks * 200);
//                    layout.addView(sLayout);
//                } else {
//                    sLayout.setY(serverAmount * 115 + seperatorAmount * 80 + 20 + openTasks * 200);
//
//                    if(clickedOn == sLayout) {
//                        openTasks++;
//                        stopButton.setY((serverAmount + 1) * 115 + seperatorAmount * 80 + 40);
//                        stopButton.setVisibility(View.VISIBLE);
//                        startButton.setY((serverAmount + 1) * 115 + seperatorAmount * 80 + 40);
//                        startButton.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                serverAmount++;
//            }

//            if(!initialize){
//                if(clickedOn == null){
//                    stopButton.setVisibility(View.INVISIBLE);
//                    startButton.setVisibility(View.INVISIBLE);
//                }
//            }
        }
    }
    public static void initializeAvailableTasks(){
        displayAvailableTasks(true);
    }
    //public static void repaintAvailableTasks(){
    //    displayAvailableTasks(false);
    //}

    public static void initializeButtons(RelativeLayout layout){
        stopButton = new ExtraButton(a.main, a.main.getResources().getDrawable(R.drawable.button_stop), "stop");
        stopButton.setX(170F);
        stopButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.CENTER_HORIZONTAL).build());
        stopButton.setVisibility(View.INVISIBLE);
        layout.addView(stopButton);

        startButton = new ExtraButton(a.main, a.main.getResources().getDrawable(R.drawable.button_start), "start");
        startButton.setX(-170F);
        startButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.CENTER_HORIZONTAL).build());
        startButton.setVisibility(View.INVISIBLE);
        layout.addView(startButton);
    }

}
