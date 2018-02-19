package eu.epicclan.servermanager.backend.manager;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.backend.manager.utils.Server;

public class ServerManager {
    public List<Server> serverList = new ArrayList<Server>();
    public ConManager conM;

    public ServerManager(){
        MainActivity.manager = this;
        new AsyncLoad(this).execute();
    }

    private class AsyncLoad extends AsyncTask<String, Void, String>{

        public ServerManager sm = null;
        public AsyncLoad(ServerManager sm){
            this.sm = sm;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                sm.conM = new ConManager("185.114.226.146", "epic!");
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            MainActivity.buildLayout();
        }
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values){}
    }

}
