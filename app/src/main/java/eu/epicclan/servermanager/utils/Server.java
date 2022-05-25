package eu.epicclan.servermanager.utils;

public class Server {
    public String startPath;
    public String stopPath;

    public String name;
    public String desc;
    public String category;
    public String picURL;

    public String status;

    public Server(String startPath, String stopPath, String name, String desc, String category, String picURL, String status){
        this.startPath = startPath;
        this.stopPath = stopPath;

        this.name = name;
        this.desc = desc;

        this.category = category;
        this.picURL = picURL;

        this.status = status;
    }

}
