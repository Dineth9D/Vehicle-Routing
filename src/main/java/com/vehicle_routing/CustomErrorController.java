package com.vehicle_routing;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/error")
public class CustomErrorController implements ContainerRequestFilter {

    @Inject
    @Location("error.html")
    Template errorTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response handleError(@Context ContainerRequestContext requestContext) {
        int statusCode = (int) requestContext.getProperty("javax.servlet.error.status_code");

        if (statusCode == Response.Status.FORBIDDEN.getStatusCode()) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(errorTemplate.instance().data("errorMessage", "Error: Forbidden")).build();
        } else if (statusCode == Response.Status.NOT_FOUND.getStatusCode()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorTemplate.instance().data("errorMessage", "Error: Not Found")).build();
        } else if (statusCode == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorTemplate.instance().data("errorMessage", "Error: Internal Server Error")).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorTemplate.instance().data("errorMessage", "Error: Internal Server Error")).build();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }
}
