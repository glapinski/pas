package com.pas.endpoint;

import com.pas.model.Rent;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import com.pas.manager.RentManager;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/rents")
@Consumes("application/json")
@Produces("application/json")
public class RentApi {

    @Inject
    RentManager rentManager;
    @GET
    @RolesAllowed({"Manager", "Admin"})
    public List<Rent> getAllRents() {
        return rentManager.findAllRents();
    }
    @GET
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Rent getRentById(@PathParam("id") UUID id) {
        return rentManager.findById(id);
    }
    @GET
    @Path("/ongoing")
    @RolesAllowed({"Manager"})
    public List<Rent> getOngoingRents() {
        return rentManager.findOngoingRents();
    }
    @GET
    @Path("/finished")
    @RolesAllowed({"Manager"})
    public List<Rent> getFinishedRents() {
        return rentManager.findFinishedRents();
    }
    @POST
    @Path("/create")
    @RolesAllowed({"BaseUser"})
    public Rent createRent(@QueryParam("userId") UUID userId, @QueryParam("productId") UUID productId) {
        return rentManager.createRent(userId,productId);
    }
    @PUT
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response endRent(@PathParam("id") UUID rentId) {
        rentManager.endRent(rentId);
        return Response.ok().build();
    }


}
