package eu.epicclan.servermanager.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.epicclan.servermanager.DelayedRunnable;
import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.R;

public class LoginManager {

    public static MainActivity a = MainActivity.main;

    public static String savedUsername = "";
    public static String savedPassword = "";


    public static void automatedLogin(){
        /*if(!savedPassword.equals("")){
            ((EditText)a.findViewById(R.id.password)).setText(savedPassword);
            ((CheckBox)a.findViewById(R.id.saveAccount)).setChecked(true);
            login();
        }*/
    }

    public static void login() {
        a.findViewById(R.id.wrongLogin).setVisibility(View.INVISIBLE);

        String username = ((EditText) a.findViewById(R.id.accountName)).getText().toString();
        String password = ((EditText) a.findViewById(R.id.password)).getText().toString();

        System.out.println(username);
        System.out.println(password);

        JsonObject postData = new JsonObject();
        postData.addProperty("username", username);
        postData.addProperty("password", password);

        new Thread() {
            public void run(){
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("https://epicclan.de/api/auth/signin");

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                    writer.write(postData.toString());
                    writer.flush();

                    int code = urlConnection.getResponseCode();
                    System.out.println(code);

                    BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                    }

                } catch (IOException e) {
                    loginFailed();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        }.start();

        /*final DelayedRunnable postExec = new DelayedRunnable(null, null);

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
                            if(((CheckBox)a.findViewById(R.id.saveAccount)).isChecked()){
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
        }).execute();*/
    }


    public static void loadSavedLogin(){
        MainActivity.pref = a.getPreferences(Context.MODE_PRIVATE);

        savedUsername = MainActivity.pref.getString("saved_username", "");
        savedPassword = MainActivity.pref.getString("saved_password", "");
    }

    /*public static void loadSavedPassword(){
        a.pref = a.getPreferences(Context.MODE_PRIVATE);
        savedPassword = a.pref.getString("saved_password", "");
    }*/

    public static void setSavedLogin(String username, String password){
        SharedPreferences.Editor edit = MainActivity.pref.edit();

        edit.putString("saved_username", username);
        edit.putString("saved_password", password);

        edit.apply();
    }

    public static void setSavedPassword(String password){
        SharedPreferences.Editor edit = a.pref.edit();
        edit.putString("saved_password", password);
        edit.apply();
    }

    public static void loginFailed(){
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                a.findViewById(R.id.wrongLogin).setVisibility(View.VISIBLE);
            }
        });
    }

}
