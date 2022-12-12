package eu.epicclan.servermanager.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.utils.Server;

public class ServerManager {
    public List<Server> serverList = new ArrayList<Server>();

    public ServerManager(){
        MainActivity.manager = this;
        LayoutManager.buildLogin();
    }

    public void load(){
        try {
            ConManager.getInstance().getServers();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
