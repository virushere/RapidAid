package com.example.rapidaid;

public class BloodDonationData {

    String city, link, location;

    public BloodDonationData() {}

    public BloodDonationData(String city, String link, String location) {
        this.city = city;
        this.link = link;
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
