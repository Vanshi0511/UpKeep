package com.example.upkeep.chat_fragments;

import org.json.JSONObject;

public class Message1 {


        private String title, genre, year,mid,dob,price,iscoming,starpercentage,showedit,self,collected_star,address;
        JSONObject jsonObject;
        int percentage=0;

        public Message1()
        {
        }

        public Message1(String title, String genre, String year)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
        }
        public Message1(String title, String genre)
        {
            this.title = title;
            this.genre = genre;
        }


        public Message1(String title, String genre, String year, String mid)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
        }

        public Message1(String title, String genre, String year, String mid, String dob)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.dob = dob;
        }

        public Message1(String title, String genre, String year, String mid, JSONObject jsonObject)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.dob = dob;
            this.jsonObject = jsonObject;
        }
        public Message1(String title, String genre, String year, String mid, String self, JSONObject jsonObject)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.self = self;
            this.jsonObject = jsonObject;
        }

        public Message1(String title, String genre, String year, String mid, String dob, String price)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.dob = dob;
            this.price = price;
        }


        public Message1(String title, String genre, String year, String mid, String dob, String price, String iscoming)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.dob = dob;
            this.price = price;
            this.iscoming = iscoming;
        }
        public Message1(String title, String genre, String year, String mid, String dob, String price, String iscoming,String address)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.dob = dob;
            this.price = price;
            this.iscoming = iscoming;
            this.address = address;
        }

        public Message1(String title, String genre, String year, String mid, String dob, String price, int percentage, String starpercentage, String iscoming, String showedit, String collected_star)
        {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.mid = mid;
            this.dob = dob;
            this.price = price;
            this.percentage = percentage;
            this.starpercentage = starpercentage;
            this.iscoming = iscoming;
            this.showedit = showedit;
            this.collected_star = collected_star;
        }

        public String getTitle() {
            return title;
        }
        public String getMid() {
            return mid;
        }
        public String getDob() {
            return dob;
        }
        public String getprice() {
            return price;
        }
        public String getself() {
            return self;
        }
        public int getPercentage() {return percentage;}
        public String getStarpercentage() {return starpercentage;}
        public String getiscoming() {
            return iscoming;
        }
        public String getShowedit(){return showedit;}

        public String getAddress()
        {
            return address;
        }
        public String getcollected_star(){return collected_star;}
        public JSONObject getjsonObject() {
            return jsonObject;
        }

        public void setTitle(String name) {
            this.title = name;
        }
        public void setMid(String mid) {
            this.mid = mid;
        }
        public void setJsonObject(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }
        public void setprice(String price) {
            this.price = price;
        }

        public void setDob(String dob)
        {
            this.dob = dob;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

}
