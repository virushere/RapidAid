package com.example.rapidaid;

public class PoliceOngoingData {

    String name, phone, vehicle, city, maps;

    public PoliceOngoingData() {}

    public PoliceOngoingData(String name, String phone, String vehicle, String city, String maps) {
        this.name = name;
        this.phone = phone;
        this.vehicle = vehicle;
        this.city = city;
        this.maps = maps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMaps() {
        return maps;
    }

    public void setMaps(String maps) {
        this.maps = maps;
    }
}
