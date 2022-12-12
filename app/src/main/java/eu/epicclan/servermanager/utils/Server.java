package eu.epicclan.servermanager.utils;

import java.util.UUID;

public class Server {

    public UUID uuid;

    public String name;
    public String desc;
    public String category;
    public String picURL;

    public String status;

    public Server(String uuid, String name, String desc, String category, String picURL, String status){
        this.uuid = UUID.fromString(uuid);

        this.name = name;
        this.desc = desc;

        this.category = category;
        this.picURL = picURL;

        this.status = status;
    }

}
