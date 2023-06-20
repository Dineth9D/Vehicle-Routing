package com.vehicle_routing_ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class GeolocationController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeolocationController() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/geolocation")
    public String processGeolocationRequest(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("resultJson") String resultJson,
            @RequestParam("customerLocationsJson") String customerLocationsJson,
            Model model
    ) throws JsonProcessingException {

        try {
        System.out.println("Spring Received POST request to /geolocation endpoint with latitude=" + latitude + " and longitude=" + longitude);
        
        // Parse geolocation data and customer locations from request parameters
        GeolocationResult result = objectMapper.readValue(resultJson, GeolocationResult.class);
        List<GeolocationResult> customerLocations = objectMapper.readValue(customerLocationsJson, new TypeReference<List<GeolocationResult>>() {});

        // Store geolocation data and customer locations in the model
        model.addAttribute("result", result);
        model.addAttribute("customerLocations", customerLocations);

        // Render the result.html template with the data from the model
        return "result";
        } catch (Exception e) {
            System.err.println("An error occurred while processing the POST request to /geolocation endpoint: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}