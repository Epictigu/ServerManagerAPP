package eu.epicclan.servermanager;

<<<<<<< HEAD
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import eu.epicclan.servermanager.manager.LoginManager;
import eu.epicclan.servermanager.manager.ServerManager;
=======
import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.LinkedHashMap;

import eu.epicclan.servermanager.manager.LayoutManager;
import eu.epicclan.servermanager.manager.ServerManager;
import eu.epicclan.servermanager.utils.Category;
import eu.epicclan.servermanager.utils.Server;
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be

public class MainActivity extends AppCompatActivity {

    public static ServerManager manager;
    public static MainActivity main;
<<<<<<< HEAD

    public static SharedPreferences pref;
=======
    public static RelativeLayout layout;
    public static SharedPreferences pref;
    public static LinkedHashMap<String, Category> categories;

    public static String savedPassword = "";
>>>>>>> 2993c324c0a4033210577270ccc373c377f392be

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
<<<<<<< HEAD

        LoginManager.loadSavedPassword();
        new ServerManager();
    }

=======
        categories = new LinkedHashMap<String, Category>();
        loadSavedPassword();
        new ServerManager();
    }

    public static void buildLogin(){
        main.getSupportActionBar().hide();
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
        layout = new RelativeLayout(main);
        layout.setBackgroundColor(main.getResources().getColor(R.color.colorBackground));

        main.setContentView(layout);

        main.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        main.getSupportActionBar().setCustomView(R.layout.layout_toolbar);
        main.getSupportActionBar().show();

        categories.put("General", new Category("General"));

        for(Server s : manager.serverList){
            if(!categories.containsKey(s.category)){
                categories.put(s.category, new Category(s.category));
            }
            categories.get(s.category).servers.add(s);
        }

        LayoutManager.displayAvailableTasks();
        LayoutManager.initializeButtons(layout);
    }

>>>>>>> 2993c324c0a4033210577270ccc373c377f392be
}
