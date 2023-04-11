package com.pas.endpoint;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.pas.endpoint.auth.JWTAuthTokenUtils;
import com.pas.manager.UserManager;
import com.pas.model.Rent;
import com.pas.model.dto.ChangePasswordDTO;
import com.pas.model.dto.RegisterDTO;
import com.pas.model.dto.UserDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import com.pas.model.User.User;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public class UserApi {

    @Inject
    UserManager userManager;

    @Inject
    Principal principal;

    @GET
    @RolesAllowed({"BaseUser", "Manager", "Admin", "Unauthorized"})
    public Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) throws JOSEException {
        List<User> users = userManager.findUsers(allMatchingByLogin, oneByLogin);
        if(oneByLogin.isPresent()){
            User user = users.get(0);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", String.valueOf(user.getId()));
            jsonObject.addProperty("login", user.getLogin());
            String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
            return Response.status(Response.Status.OK).entity(UserDTO.fromEntityToDTO(user)).tag(ifMatch).build();
        }
        return Response.status(Response.Status.OK).entity(UserDTO.entityListToDTO(userManager.findUsers(allMatchingByLogin, oneByLogin))).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    public Response getUserById(@PathParam("id") UUID id) throws JOSEException {
        User user = userManager.findById(id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(id));
        jsonObject.addProperty("login", user.getLogin());
        String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
        return Response.status(Response.Status.OK).entity(UserDTO.fromEntityToDTO(user)).tag(ifMatch).build();
    }

    @GET
    @Path("/{id}/ongoingRents")
    @RolesAllowed({"BaseUser"})
    public List<Rent> getOngoingUserRents(@PathParam("id") UUID userId) {
        return userManager.findOngoingUserRents(userId);
    }

    @GET
    @Path("/{id}/finishedRents")
    @RolesAllowed({"BaseUser"})
    public List<Rent> getFinishedUserRents(@PathParam("id") UUID userId) {
        return userManager.findFinishedUserRents(userId);
    }

    @GET
    @Path("/{id}/allRents")
    @RolesAllowed({"BaseUser", "Admin", "Manager"})
    public List<Rent> getAllUserRents(@PathParam("id") UUID userId) {
        return userManager.findAllUserRents(userId);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    public Response updateUser(@PathParam("id") UUID id, UserDTO updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", updatedUser.getId().toString());
        jsonObject.addProperty("login", updatedUser.getLogin());
        if(!JWTAuthTokenUtils.verify(ifMatch, jsonObject.toString())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userManager.updateUser(id, updatedUser);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/changePassword")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    public Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO) {
       userManager.changeUserPassword(id, changePasswordDTO);
       return Response.ok().build();
    }

    @GET
    @Path("/class/{id}")
    @RolesAllowed({"BaseUser","Manager","Admin","Unauthorized"})
    public String getUserClass(@PathParam("id") UUID userId) {
        return userManager.getRole(userId);
    }

    @GET
    @Path("/{id}/rents")
    @RolesAllowed({"BaseUser", "Admin"})
    public List<Rent> getUserRents(@PathParam("id") UUID userId) {
        return userManager.findUserRents(userId);
    }

    @POST
    @Path("/register")
    @RolesAllowed({"Unauthorized"})
    public User register(@Valid RegisterDTO user) {
        return userManager.register(user);
    }

    @POST
    @RolesAllowed({"Admin"})
    public User addUser(@Valid RegisterDTO user) {
        return userManager.register(user);
    }

    @PUT
    @Path("/{id}/suspendOrResume")
    @RolesAllowed({"Admin"})
    public Response suspendOrResumeUser(@PathParam("id") UUID userId) {
        userManager.suspendOrResumeUser(userId);
        return Response.ok().build();
    }
}
