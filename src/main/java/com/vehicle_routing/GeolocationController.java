package com.vehicle_routing;

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
    public TemplateInstance index() {
        return indexTemplate.instance();
    }

    @POST
    @Path("/geolocation")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance result(
            @FormParam("latitude") double latitude,
            @FormParam("longitude") double longitude) {
        GeolocationResult result = geolocationService.getGeolocation(latitude, longitude);
        return resultTemplate.data("result", result);
    }
}