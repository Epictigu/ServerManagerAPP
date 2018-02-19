package eu.epicclan.servermanager.backend.manager.utils;

public class Server {
    public String startPath;
    public String stopPath;

    public String name;
    public String desc;

    public Server(String startPath, String stopPath, String name, String desc){
        this.startPath = startPath;
        this.stopPath = stopPath;

        this.name = name;
        this.desc = desc;
    }

}
