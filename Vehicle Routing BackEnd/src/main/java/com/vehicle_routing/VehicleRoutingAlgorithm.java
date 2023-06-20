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

    public List<CustomerLocation> calculateRoutes(double[] customerLatitudes, double[] customerLongitudes, double vehicleLatitude, double vehicleLongitude) {
        // Create customer locations based on provided latitudes and longitudes
        for (int i = 0; i < customerLatitudes.length; i++) {
            CustomerLocation customerLocation = new CustomerLocation(customerLatitudes[i], customerLongitudes[i]);
            customerLocations.add(customerLocation);
        }

        // Start the permutation algorithm
        List<CustomerLocation> bestRoute = new ArrayList<>();
        double bestDistance = Double.MAX_VALUE;
        CustomerLocation startingLocation = new CustomerLocation(vehicleLatitude, vehicleLongitude);
        permute(new ArrayList<>(), customerLocations, startingLocation, bestRoute, bestDistance);

        return bestRoute;
    }



    private void permute(List<CustomerLocation> route, List<CustomerLocation> remainingLocations, CustomerLocation currentLocation, List<CustomerLocation> bestRoute, double bestDistance) {
        // If there are no remaining locations, check if the current route is better than the best route
        if (remainingLocations.isEmpty()) {
            double currentDistance = calculateTotalDistance(route);
            if (currentDistance < bestDistance) {
                bestRoute.clear();
                bestRoute.addAll(route);
                bestDistance = currentDistance;
            }
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

                // Recursively call the permutation algorithm with the updated inputs
                permute(updatedRoute, updatedRemainingLocations, nextLocation, bestRoute, bestDistance);
            }
        }
    }


    private double calculateDistance(CustomerLocation location1, CustomerLocation location2) {
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


    private double calculateTotalDistance(List<CustomerLocation> route) {
        double totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += calculateDistance(route.get(i), route.get(i + 1));
        }
        return totalDistance;
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

