package com.osman.alexander.asandic;

/**
 * Created by ALEXANDER on 01/Nov/2019.
 */

public class UserData {
    private String firstName;
    private String lastName;
    private String userid;
    private String gender;
    private String phone;
    private String referenceNumber;
    private String username;
    private String image_url;
    private String campus;

    public UserData(String firstName, String lastName, String userid, String gender, String phone, String referenceNumber, String username, String image_url, String campus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userid = userid;
        this.gender = gender;
        this.phone = phone;
        this.referenceNumber = referenceNumber;
        this.username = username;
        this.image_url = image_url;
        this.campus = campus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
