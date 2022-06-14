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

    public String ip;
    public String password;

    public Socket connection = null;

    public ConManager(String ip, String password) throws Exception{
        this.ip = ip;
        this.password = password;
    }

    public void setup() throws IOException{
        connection = new Socket(this.ip, 9955);
    }

    public void getServers() throws IOException {
        URL url = new URL("https://epicclan.de/api/getservers");
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
                MainActivity.manager.serverList.add(new Server("", "", server.getString("name"), server.getString("desc"), server.getString("category"), server.getString("icon"), server.getString("status")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPassword(String password){
        try {
            setup();

            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
            PrintWriter pw = new PrintWriter(os);
            pw.println("checkpw");
            pw.println(password);
            pw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            List<String> lines = new ArrayList<String>();
            lines.add(br.readLine());

            connection.close();
            if(lines.get(0).equalsIgnoreCase("0")){
                return false;
            } else {
                return true;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public void exec(String path) throws IOException{
        new AsyncExec(path).execute();
    }

    public void reloadConfig() throws IOException{
        MainActivity.manager.serverList.clear();
        getServers();
    }


    private class AsyncExec extends AsyncTask<String, Void, String> {

        private String path = "";

        public AsyncExec(String path){
            this.path = path;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                setup();

                OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                PrintWriter pw = new PrintWriter(os);
                pw.println(password);
                pw.println("exec");
                pw.println(path);
                pw.flush();

                connection.close();
            } catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {}
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values){}
    }

}
