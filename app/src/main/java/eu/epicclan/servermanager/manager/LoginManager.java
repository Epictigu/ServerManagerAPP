package eu.epicclan.servermanager.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import eu.epicclan.servermanager.DelayedRunnable;
import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.R;

public class LoginManager {

    public static MainActivity a = MainActivity.main;
    public static String savedPassword = "";


    public static void automatedLogin(){
        if(!savedPassword.equals("")){
            ((EditText)a.findViewById(R.id.password)).setText(savedPassword);
            ((CheckBox)a.findViewById(R.id.savepw)).setChecked(true);
            login();
        }
    }

    public static void login(){
        a.findViewById(R.id.wrongpw).setVisibility(View.INVISIBLE);

        final DelayedRunnable postExec = new DelayedRunnable(null, null);

        new DelayedRunnable(new Runnable() {
            @Override
            public void run() {
                final String password = ((EditText) a.findViewById(R.id.password)).getText().toString();
                System.out.println(password);
                if (password.equalsIgnoreCase("")) {
                    loginFailed();
                    return;
                }
                boolean check = a.manager.conM.checkPassword(password);

                if (check) {
                    a.manager.load(password);
                    postExec.postExec = new Runnable() {
                        @Override
                        public void run() {
                            if(((CheckBox)a.findViewById(R.id.savepw)).isChecked()){
                                setSavedPassword(password);
                            } else {
                                setSavedPassword("");
                            }
                            LayoutManager.buildLayout();
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


    public static void loadSavedPassword(){
        a.pref = a.getPreferences(Context.MODE_PRIVATE);
        savedPassword = a.pref.getString("saved_password", "");
    }

    public static void setSavedPassword(String password){
        SharedPreferences.Editor edit = a.pref.edit();
        edit.putString("saved_password", password);
        edit.apply();
    }

    public static void loginFailed(){
        a.findViewById(R.id.wrongpw).setVisibility(View.VISIBLE);
    }

}
