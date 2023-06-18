package com.vehicle_routing;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeolocationService {
    private static final String OPENCAGE_API_KEY = "key";
    private final JOpenCageGeocoder geocoder;

    public GeolocationService() {
        geocoder = new JOpenCageGeocoder(OPENCAGE_API_KEY);
    }

    public GeolocationResult getGeolocation(double latitude, double longitude) {
        try {
            JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
            JOpenCageResponse response = geocoder.reverse(request);

            if (response.getStatus().getCode() == 200) {
                JOpenCageResult result = response.getResults().get(0);

                String city = result.getComponents().getCity();
                String country = result.getComponents().getCountry();

                return new GeolocationResult(latitude, longitude, city, country);
            } else {
                throw new RuntimeException("Geocoding request failed. Status code: " + response.getStatus().getCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during geocoding.", e);
        }
    }
}
