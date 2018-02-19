package eu.epicclan.servermanager.backend.manager;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.backend.manager.utils.Server;

public class ConManager {

    public String ip;
    public String password;

    public Socket connection;

    public ConManager(String ip, String password) throws Exception{
        this.ip = ip;
        this.password = password;

        connection = new Socket(this.ip, 9955);
        getServers();
    }

    public void getServers() throws IOException {
        OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
        PrintWriter pw = new PrintWriter(os);
        pw.println(password);
        pw.println("getservers");
        pw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<String> lines = new ArrayList<String>();
        lines.add(br.readLine());
        while(br.ready()) {
            lines.add(br.readLine());
        }

        for(String s : lines) {
            String[] args = s.split(";");
            MainActivity.manager.serverList.add(new Server(args[2], args[3], args[0], args[1]));
        }
    }

    public void exec(String path) throws IOException{
        new AsyncExec(path).execute();
    }

    private class AsyncExec extends AsyncTask<String, Void, String> {

        private String path = "";

        public AsyncExec(String path){
            this.path = path;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = new Socket(ip, 9955);

                OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                PrintWriter pw = new PrintWriter(os);
                pw.println(password);
                pw.println("exec");
                pw.println(path);
                pw.flush();
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
