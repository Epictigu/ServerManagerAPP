package eu.epicclan.servermanager;

import android.os.AsyncTask;

public class DelayedRunnable extends AsyncTask<String, Void, String>{

    private Runnable background = null;
    private Runnable postExec = null;

    private long delay = 0l;

    public DelayedRunnable(Runnable... tasks){
        if(tasks[0] != null){
            background = tasks[0];
        }
        if(tasks[1] != null){
            postExec = tasks[1];
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if(background != null){
            background.run();
        }

        try {
            Thread.sleep(delay);
        } catch(InterruptedException e){e.printStackTrace();}

        return null;
    }

    @Override
    protected void onPostExecute(String s){
        if(postExec != null){
            postExec.run();
        }
    }

    public DelayedRunnable setDelay(long time){
        delay = time;
        return this;
    }

    @Override
    protected void onPreExecute() {}
    @Override
    protected void onProgressUpdate(Void... values){}
}
