package eu.epicclan.servermanager;

import android.os.AsyncTask;

public class DelayedRunnable extends AsyncTask<String, Void, String>{

    public Runnable background = null;
    public Runnable postExec = null;

    public long delay = 0L;

    public DelayedRunnable(Runnable background, Runnable postExec){
        this.background = background;
        this.postExec = postExec;
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
