package eu.epicclan.servermanager.manager;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.utils.Server;

public class ServerManager {
    public List<Server> serverList = new ArrayList<Server>();
    public ConManager conM;

    public ServerManager(){
        MainActivity.manager = this;
        new AsyncLoad(this).execute();
    }

    public void load(String password){
        conM.password = password;
        try {
            conM.getServers();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private class AsyncLoad extends AsyncTask<String, Void, String>{

        public ServerManager sm = null;
        public AsyncLoad(ServerManager sm){
            this.sm = sm;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                conM = new ConManager("91.200.100.92", "none");
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {LayoutManager.buildLogin();}
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values){}
    }

}
