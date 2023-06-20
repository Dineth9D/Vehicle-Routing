package com.vehicle_routing;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient
public interface TemplateService {

    @POST
    @Path("/index.html")
    @Produces(MediaType.TEXT_HTML)
    String getIndexTemplate();

    @GET
    @Path("/result.html")
    @Produces(MediaType.TEXT_HTML)
    String getResultTemplate();
}