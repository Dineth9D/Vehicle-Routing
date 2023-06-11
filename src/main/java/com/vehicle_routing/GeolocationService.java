package com.vehicle_routing;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;
import org.springframework.stereotype.Component;

@Component
public class GeolocationService {
    private static final String OPENCAGE_API_KEY = "The OpenCage Geocoding API";

    public GeolocationResult getGeolocation(double latitude, double longitude) {
        try {
            // Create an instance of the JOpenCageGeocoder with your API key
            JOpenCageGeocoder geocoder = new JOpenCageGeocoder(OPENCAGE_API_KEY);

            // Perform a reverse geocoding lookup based on latitude and longitude
            JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
            JOpenCageResponse response = geocoder.reverse(request);

            // Check if the response was successful
            if (response.getStatus().getCode() == 200) {
                // Retrieve the first result
                JOpenCageResult result = response.getResults().get(0);

                // Retrieve the city name and country
                String city = result.getComponents().getCity();
                String country = result.getComponents().getCountry();

                return new GeolocationResult(latitude, longitude, city, country);
            } else {
                System.err.println("Geocoding request failed. Status code: " + response.getStatus().getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}