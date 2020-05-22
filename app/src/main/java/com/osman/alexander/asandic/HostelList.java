package com.osman.alexander.asandic;

import java.util.Date;

public class HostelList {
    private String name;
    private String location;
    private String image_url;
    private Date time;

    public HostelList(String name, String location, String image_url,Date time) {
        this.name = name;
        this.location = location;
        this.image_url = image_url;
        this.time = time;
    }

    public HostelList(){

    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
