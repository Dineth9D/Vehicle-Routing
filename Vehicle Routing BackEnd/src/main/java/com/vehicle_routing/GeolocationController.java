package com.vehicle_routing;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/")
public class GeolocationController {

    private final GeolocationService geolocationService;
    private final ObjectMapper objectMapper;

    @Inject
    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
        this.objectMapper = new ObjectMapper();
    }

    @POST
    @Path("/geolocation")
    @Produces(MediaType.TEXT_HTML)
    public Response processGeolocationRequest(
            @FormParam("latitude") double latitude,
            @FormParam("longitude") double longitude
    ) throws JsonProcessingException {
        try {
            System.out.println("Received POST request to /geolocation endpoint with latitude=" + latitude + " and longitude=" + longitude);

            // Retrieve geolocation data
        GeolocationResult result = geolocationService.getGeolocation(latitude, longitude);

        // Retrieve customer locations
        List<GeolocationResult> customerLocations = generateCustomerLocations();

        // Convert geolocation data and customer locations to JSON strings
        String resultJson = objectMapper.writeValueAsString(result);
        String customerLocationsJson = objectMapper.writeValueAsString(customerLocations);

        // Send data to the Spring Boot server
        String springBootServerURL = "http://localhost:8081/geolocation";
        Response response = ClientBuilder.newClient()
                .target(springBootServerURL)
                .request()
                .post(Entity.form(new Form()
                        .param("latitude", Double.toString(latitude))
                        .param("longitude", Double.toString(longitude))
                        .param("resultJson", resultJson)
                        .param("customerLocationsJson", customerLocationsJson)
                ));
        // System.out.println("Latitude: " + latitude + " Longitude: " + longitude + " Result: " + resultJson + " Customer Locations: " + customerLocationsJson);
        System.out.println("Received response from Spring Boot server with status code=" + response.getStatus());

        // Return the response from the Spring Boot server
        return Response.fromResponse(response).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        
    }


    private List<GeolocationResult> generateCustomerLocations() {
        List<GeolocationResult> customerLocations = new ArrayList<>();

        // Generate sample latitudes and longitudes for customers
        double[] sampleLatitudes = {7.4833314, 7.087310, 6.585395};
        double[] sampleLongitudes = {80.3666652, 80.014366, 79.960739};

        for (int i = 0; i < sampleLatitudes.length; i++) {
            double customerLatitude = sampleLatitudes[i];
            double customerLongitude = sampleLongitudes[i];
            GeolocationResult customerLocation = geolocationService.getGeolocation(customerLatitude, customerLongitude);
            customerLocation.setCustomerLatitude(customerLatitude);
            customerLocation.setCustomerLongitude(customerLongitude);
            customerLocations.add(customerLocation);
        }
        return customerLocations;
    }

}