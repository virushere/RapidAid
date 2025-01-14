package com.example.rapidaid;

public class DriverOngoingDataModel {
    String id, name, phone, vehicle, city, maps;

    public DriverOngoingDataModel() {}

    public DriverOngoingDataModel(String id, String name, String phone, String vehicle, String city, String maps) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.vehicle = vehicle;
        this.city = city;
        this.maps = maps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
