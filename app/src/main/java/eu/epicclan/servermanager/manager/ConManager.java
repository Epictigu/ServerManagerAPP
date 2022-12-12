package eu.epicclan.servermanager.manager;

import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.utils.Server;

public class ConManager {

    private static ConManager instance = null;

    public static ConManager getInstance() {
        if(instance == null){
            instance = new ConManager();
        }
        return instance;
    }

    //public final static String DOMAIN = "https://epicclan.de";

    //Only for testing:
    public final static String DOMAIN = "http://10.0.2.2:8081";

    private ConManager() {}

    public void getServers() throws IOException {
        URL url = new URL(DOMAIN + "/api/getservers");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if(LoginManager.cookieManager.getCookieStore().getCookies().size() > 0){
            con.setRequestProperty("Cookie",
                    TextUtils.join(";", LoginManager.cookieManager.getCookieStore().getCookies()));
        }
        con.setRequestProperty("Accept", "application/json");

        int errorCode = con.getResponseCode();
        if(errorCode > 299){
            System.out.println("Konnte den HTTP Request nicht durchführen! [Error-Code: " + errorCode + "]");
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        try {
            JSONObject serverJSON = new JSONObject(content.toString());
            JSONArray servers = (JSONArray) serverJSON.get("servers");
            for(int i = 0; i < servers.length(); i++){
                JSONObject server = (JSONObject) servers.get(i);
                MainActivity.manager.serverList.add(new Server(server.getString("uuid"), server.getString("name"), server.getString("desc"), server.getString("category"), server.getString("icon"), server.getString("status")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startServer(String uuid) throws IOException{
        URL url = new URL(DOMAIN + "/api/start/" + uuid);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        if(LoginManager.cookieManager.getCookieStore().getCookies().size() > 0){
            con.setRequestProperty("Cookie",
                    TextUtils.join(";", LoginManager.cookieManager.getCookieStore().getCookies()));
        }

        int errorCode = con.getResponseCode();
        if(errorCode > 299){
            System.out.println("Konnte den HTTP Request (Server starten) nicht durchführen! [Error-Code: " + errorCode + "]");
            return;
        }

        con.disconnect();
    }

    public void stopServer(String uuid) throws IOException{
        URL url = new URL(DOMAIN + "/api/stop/" + uuid);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        if(LoginManager.cookieManager.getCookieStore().getCookies().size() > 0){
            con.setRequestProperty("Cookie",
                    TextUtils.join(";", LoginManager.cookieManager.getCookieStore().getCookies()));
        }

        int errorCode = con.getResponseCode();
        if(errorCode > 299){
            System.out.println("Konnte den HTTP Request (Server stoppen) nicht durchführen! [Error-Code: " + errorCode + "]");
            return;
        }

        con.disconnect();
    }

    public void reloadConfig() throws IOException{
        MainActivity.manager.serverList.clear();
        getServers();
    }

}
