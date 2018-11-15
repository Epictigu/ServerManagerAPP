package eu.epicclan.servermanager.utils;

public class Server {
    public String startPath;
    public String stopPath;

    public String name;
    public String desc;
    public String category;
    public String picURL;

    public Server(String startPath, String stopPath, String name, String desc, String category, String picURL){
        this.startPath = startPath;
        this.stopPath = stopPath;

        this.name = name;
        this.desc = desc;

        this.category = category;
        this.picURL = picURL;
    }

}
