package eu.epicclan.servermanager.manager;

<<<<<<< HEAD
import android.app.ActionBar;
=======
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;
<<<<<<< HEAD
import java.util.LinkedHashMap;
=======
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
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
<<<<<<< HEAD
    public static LinkedHashMap<String, Category> categories = new LinkedHashMap<String, Category>();;

    public static RelativeLayout layout;
=======

>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
    public static ServerLayout clickedOn = null;
    private static ExtraButton stopButton = null;
    private static ExtraButton startButton = null;

<<<<<<< HEAD
    private static MainActivity a = MainActivity.main;


=======
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be

    public static void setLastClicked(ServerLayout clickedOn){
        if(clickedOn == LayoutManager.clickedOn){
            LayoutManager.clickedOn = null;
            return;
        }
        LayoutManager.clickedOn = clickedOn;
    }

<<<<<<< HEAD


    public static void buildLayout(){
        layout = new RelativeLayout(a);
        layout.setBackgroundColor(a.getResources().getColor(R.color.colorBackground));

        a.setContentView(layout);

        a.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        a.getSupportActionBar().setCustomView(R.layout.layout_toolbar);
        a.getSupportActionBar().show();

        categories.put("General", new Category("General"));

        for(Server s : a.manager.serverList){
            if(!categories.containsKey(s.category)){
                categories.put(s.category, new Category(s.category));
            }
            categories.get(s.category).servers.add(s);
        }

        LayoutManager.initializeAvailableTasks();
        LayoutManager.initializeButtons(layout);
    }

    public static void buildLogin(){
        a.getSupportActionBar().hide();
        a.setContentView(R.layout.layout_password);
        a.findViewById(R.id.loginb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.login();
            }
        });

        LoginManager.automatedLogin();
    }



    private static void displayAvailableTasks(boolean initialize){
=======
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
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
        int serverAmount = 0;
        int seperatorAmount = 0;
        int openTasks = 0;

<<<<<<< HEAD
        for(String str : categories.keySet()){
            Category c = categories.get(str);
            c.sep.setY(serverAmount * 115 + seperatorAmount * 80 + 30 + openTasks * 200);
            seperatorAmount++;
            if(initialize){
                layout.addView(c.sep);
            }

            for(Server s : c.servers){
                ServerLayout sLayout = availableTasks.get(s);
                if(initialize){
                    sLayout = new ServerLayout(a.main, s);
                    sLayout.buildServer();
                    availableTasks.put(s, sLayout);

                    sLayout.setY(serverAmount * 115 + seperatorAmount * 80 + 20 + openTasks * 200);
                    layout.addView(sLayout);
                } else {
                    sLayout.setY(serverAmount * 115 + seperatorAmount * 80 + 20 + openTasks * 200);

                    if(clickedOn == sLayout) {
                        openTasks++;
                        stopButton.setY((serverAmount + 1) * 115 + seperatorAmount * 80 + 40);
                        stopButton.setVisibility(View.VISIBLE);
                        startButton.setY((serverAmount + 1) * 115 + seperatorAmount * 80 + 40);
                        startButton.setVisibility(View.VISIBLE);
                    }
                }

                serverAmount++;
            }

            if(!initialize){
                if(clickedOn == null){
                    stopButton.setVisibility(View.INVISIBLE);
                    startButton.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
    public static void initializeAvailableTasks(){
        displayAvailableTasks(true);
    }
    public static void repaintAvailableTasks(){
        displayAvailableTasks(false);
    }

    public static void initializeButtons(RelativeLayout layout){
        stopButton = new ExtraButton(a.main, a.main.getResources().getDrawable(R.drawable.button_stop), "stop");
=======
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
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
        stopButton.setX(170F);
        stopButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.CENTER_HORIZONTAL).build());
        stopButton.setVisibility(View.INVISIBLE);
        layout.addView(stopButton);

<<<<<<< HEAD
        startButton = new ExtraButton(a.main, a.main.getResources().getDrawable(R.drawable.button_start), "start");
=======
        startButton = new ExtraButton(MainActivity.main, MainActivity.main.getResources().getDrawable(R.drawable.button_start), "start");
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
        startButton.setX(-170F);
        startButton.setLayoutParams(new ParamsBuilder(140, 140).alignTo(-1, RelativeLayout.CENTER_HORIZONTAL).build());
        startButton.setVisibility(View.INVISIBLE);
        layout.addView(startButton);
    }

}
