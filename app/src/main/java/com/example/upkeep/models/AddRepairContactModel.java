package com.example.upkeep.models;

import com.google.gson.annotations.SerializedName;

public class AddRepairContactModel {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("contact_no")
    private String contact_no;

    @SerializedName("type_of_repairs")
    private String type_of_repairs;

    private boolean expand;


    public AddRepairContactModel(String name, String email, String contact_no, String type_of_repairs) {
        this.name = name;
        this.email = email;
        this.contact_no = contact_no;
        this.type_of_repairs = type_of_repairs;
        this.expand = false;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getType_of_repairs() {
        return type_of_repairs;
    }

    public void setType_of_repairs(String type_of_repairs) {
        this.type_of_repairs = type_of_repairs;
    }
}
