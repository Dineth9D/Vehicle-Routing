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
            @FormParam("longitude") double longitude,
            @FormParam("customerLatitude1") double customerLatitude1,
            @FormParam("customerLongitude1") double customerLongitude1
    // Repeat the above parameters for each customer (customerLatitude2,
    // customerLongitude2, etc.)
    ) {
        GeolocationResult result = geolocationService.getGeolocation(latitude, longitude);

        // Retrieve geolocation for each customer
        List<GeolocationResult> customerLocations = new ArrayList<>();
        GeolocationResult customerLocation1 = geolocationService.getGeolocation(customerLatitude1, customerLongitude1);
        customerLocations.add(customerLocation1);
        // Add geolocation for each customer to the customerLocations list

        return resultTemplate.data("result", result).data("customerLocations", customerLocations);
    }
}