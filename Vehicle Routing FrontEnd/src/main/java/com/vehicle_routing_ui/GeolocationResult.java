package com.vehicle_routing_ui;

public class GeolocationResult {
    private double latitude;
    private double longitude;
    private String city;
    private String country;
    private double customerLatitude;
    private double customerLongitude;

    public GeolocationResult(double latitude, double longitude, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

    // Getters and setters

    public GeolocationResult() {

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

}

