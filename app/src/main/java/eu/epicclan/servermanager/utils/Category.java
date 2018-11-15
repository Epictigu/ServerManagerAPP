package eu.epicclan.servermanager.utils;

import java.util.ArrayList;
import java.util.List;

import eu.epicclan.servermanager.MainActivity;

public class Category {

    public String title;
    public Seperator sep;
    public List<Server> servers = new ArrayList<Server>();

    public Category(String title){
        this.title = title;
        createSeperator();
    }

    private void createSeperator(){
        this.sep = new Seperator(MainActivity.main, title);
    }

}
