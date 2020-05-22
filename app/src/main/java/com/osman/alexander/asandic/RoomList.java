package com.osman.alexander.asandic;

import java.util.Date;

public class RoomList {
    private String price;
    private String numberOfRoomies;
    private String image_url;
    private Date time;

    public RoomList(String price, String numberOfRoomies, String image_url,Date time) {
        this.price = price;
        this.numberOfRoomies = numberOfRoomies;
        this.image_url = image_url;
        this.time = time;
    }

    public RoomList() {
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOfRoomies() {
        return numberOfRoomies;
    }

    public void setNumberOfRoomies(String numberOfRoomies) {
        this.numberOfRoomies = numberOfRoomies;
    }
}
