package eu.epicclan.servermanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import eu.epicclan.servermanager.manager.ServerManager;
import eu.epicclan.servermanager.utils.Server;
import eu.epicclan.servermanager.utils.ExtraButton;
import eu.epicclan.servermanager.utils.ServerLayout;

public class MainActivity extends AppCompatActivity {

    public static ServerManager manager;
    public static MainActivity main;
    public static SharedPreferences pref;

    public static String savedPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        loadSavedPassword();
        new ServerManager();
    }

    public static void buildLogin(){
        main.setContentView(R.layout.layout_password);
        main.findViewById(R.id.loginb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        if(!savedPassword.equals("")){
            ((EditText)main.findViewById(R.id.password)).setText(savedPassword);
            ((CheckBox)main.findViewById(R.id.savepw)).setChecked(true);
            login();
        }
    }

    public static void loadSavedPassword(){
        pref = main.getPreferences(Context.MODE_PRIVATE);
        savedPassword = pref.getString("saved_password", "");
    }

    public static void setSavedPassword(String password){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("saved_password", password);
        edit.commit();
    }

    public static void login(){
        main.findViewById(R.id.wrongpw).setVisibility(View.INVISIBLE);

        final DelayedRunnable postExec = new DelayedRunnable(null, null);

        new DelayedRunnable(new Runnable() {
            @Override
            public void run() {
                final String password = ((EditText) main.findViewById(R.id.password)).getText().toString();
                System.out.println(password);
                if (password.equalsIgnoreCase("")) {
                    loginFailed();
                    return;
                }
                boolean check = manager.conM.checkPassword(password);

                if (check) {
                    manager.load(password);
                    postExec.postExec = new Runnable() {
                        @Override
                        public void run() {
                            if(((CheckBox)main.findViewById(R.id.savepw)).isChecked()){
                                setSavedPassword(password);
                            } else {
                                setSavedPassword("");
                            }
                            buildLayout();
                        }
                    };
                } else {
                    savedPassword = "";
                    postExec.postExec = new Runnable() {
                        @Override
                        public void run() {
                            loginFailed();
                        }
                    };
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                postExec.execute();
            }
        }).execute();
    }

    public static void loginFailed(){
        main.findViewById(R.id.wrongpw).setVisibility(View.VISIBLE);
    }

    public static void buildLayout(){
        System.out.println("Test");
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
        layout.invalidate();
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
