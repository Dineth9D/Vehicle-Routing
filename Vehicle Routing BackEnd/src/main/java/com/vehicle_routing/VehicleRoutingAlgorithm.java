package com.vehicle_routing;

import java.util.ArrayList;
import java.util.List;

public class VehicleRoutingAlgorithm {

    private List<CustomerLocation> customerLocations;
    private List<List<CustomerLocation>> possibleRoutes;

    public VehicleRoutingAlgorithm() {
        this.customerLocations = new ArrayList<>();
        this.possibleRoutes = new ArrayList<>();
    }

    public List<List<CustomerLocation>> calculateRoutes(double[] customerLatitudes, double[] customerLongitudes, double vehicleLatitude, double vehicleLongitude) {
        // Create customer locations based on provided latitudes and longitudes
        for (int i = 0; i < customerLatitudes.length; i++) {
            CustomerLocation customerLocation = new CustomerLocation(customerLatitudes[i], customerLongitudes[i]);
            customerLocations.add(customerLocation);
        }

        // Start the permutation algorithm
        permute(new ArrayList<>(), customerLocations, new VehicleLocation(vehicleLatitude, vehicleLongitude));

        return possibleRoutes;
    }

    private void permute(List<CustomerLocation> route, List<CustomerLocation> remainingLocations, VehicleLocation currentLocation) {
        // If there are no remaining locations, add the current route to the list of possible routes
        if (remainingLocations.isEmpty()) {
            possibleRoutes.add(new ArrayList<>(route));
        } else {
            for (int i = 0; i < remainingLocations.size(); i++) {
                CustomerLocation nextLocation = remainingLocations.get(i);
                double distance = calculateDistance(currentLocation, nextLocation);

                // Create a copy of the current route and add the next location to it
                List<CustomerLocation> updatedRoute = new ArrayList<>(route);
                updatedRoute.add(nextLocation);

                // Remove the next location from the remaining locations
                List<CustomerLocation> updatedRemainingLocations = new ArrayList<>(remainingLocations);
                updatedRemainingLocations.remove(i);

                // Create a new VehicleLocation object using the latitude and longitude values of the next location
                VehicleLocation nextVehicleLocation = new VehicleLocation(nextLocation.getLatitude(), nextLocation.getLongitude());

                // Recursively call the permutation algorithm with the updated inputs
                permute(updatedRoute, updatedRemainingLocations, nextVehicleLocation);
            }
        }
    }


    private double calculateDistance(VehicleLocation location1, CustomerLocation location2) {
    double earthRadius = 6371; // Radius of the earth in km
    double latDistance = Math.toRadians(location2.getLatitude() - location1.getLatitude());
    double lonDistance = Math.toRadians(location2.getLongitude() - location1.getLongitude());
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(location1.getLatitude())) * Math.cos(Math.toRadians(location2.getLatitude()))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = earthRadius * c; // Distance in km
    return distance;
}

}

class CustomerLocation {
    private double latitude;
    private double longitude;

    public CustomerLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

class VehicleLocation {
    private double latitude;
    private double longitude;

    public VehicleLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

