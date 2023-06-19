package com.vehicle_routing;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@CheckedTemplate
@Path("/")
public class GeolocationController {

    private final GeolocationService geolocationService;

    @Inject
    @Location("index.html")
    Template indexTemplate;

    @Inject
    @Location("result.html")
    Template resultTemplate;

    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getIndexPage() {
        return indexTemplate.instance();
    }


    @POST
    @Path("/geolocation")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance processGeolocationRequest(
            @FormParam("latitude") double latitude,
            @FormParam("longitude") double longitude
    ) {
        GeolocationResult result = geolocationService.getGeolocation(latitude, longitude);

        List<GeolocationResult> customerLocations = generateCustomerLocations();

        return resultTemplate.data("result", result).data("customerLocations", customerLocations);
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