package com.vehicle_routing;

public class GeolocationResult {
    private String ipAddress;
    private String country;
    private String city;
    private double latitude;
    private double longitude;

    public GeolocationResult(String ipAddress, String country, String city, double latitude, double longitude) {
        this.ipAddress = ipAddress;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
}