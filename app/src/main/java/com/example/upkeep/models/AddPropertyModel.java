package com.example.upkeep.models;

import android.net.Uri;

public class    AddPropertyModel {

    private String propertyName;
    private String totalRoom;
    private String propertyCapacity;
    private String address1;
    private String address2;
    private String state;
    private String city;
    private String postCode;
    private String description;
    private Uri image;

    public AddPropertyModel(String propertyName, String totalRoom, String propertyCapacity, String address1, String address2, String state, String city, String postCode, String description , Uri image) {
        this.propertyName = propertyName;
        this.totalRoom = totalRoom;
        this.propertyCapacity = propertyCapacity;
        this.address1 = address1;
        this.address2 = address2;
        this.state = state;
        this.city = city;
        this.postCode = postCode;
        this.description = description;
        this.image=image;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(String totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getPropertyCapacity() {
        return propertyCapacity;
    }

    public void setPropertyCapacity(String propertyCapacity) {
        this.propertyCapacity = propertyCapacity;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
