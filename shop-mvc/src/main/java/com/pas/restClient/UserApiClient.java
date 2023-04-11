package com.pas.restClient;

import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Rent;
import com.pas.model.User.User;
import com.pas.model.dto.ChangePasswordDTO;
import com.pas.model.dto.RegisterDTO;
import com.pas.model.dto.UserDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.*;

@RequestScoped
public class UserApiClient extends RestClient<User> implements Serializable {

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    public UserApiClient() {
        super();
    }

    public List<UserDTO> getAllUsers(){
        WebTarget webTarget = client.path("/users");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<UserDTO>>(){});
    }

    public void addUser(RegisterDTO user){
        WebTarget webTarget = client.path("/users");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(user));
    }

    public void updateUser(UUID id, UserDTO user, String ifMatch){
        WebTarget webTarget = client.path("/users/" + id.toString());
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .header("If-Match", ifMatch)
                .put(Entity.json(user));
    }

    public Response findOneByLogin(String login){
        WebTarget webTarget = client.path("/users").queryParam("oneByLogin", login);
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
    }

    public UserDTO getUserById(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString());
         return webTarget.request(MediaType.APPLICATION_JSON)
                 .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(UserDTO.class);
    }

    public void suspendOrResumeUser(UUID id){
        WebTarget webTarget = client.path("/users/" + id.toString() + "/suspendOrResume");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(""));
    }

    public List<UserDTO> getSearchedUsers(Optional<String> searchInput) {
        WebTarget webTarget = client.path("/users").queryParam("allMatchingByLogin", searchInput.orElse(""));
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<UserDTO>>(){});
    }

    public void register(RegisterDTO currentUser) {
        WebTarget webTarget = client.path("/users/register");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(currentUser));
    }

    public void changePassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/changePassword");
        webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(changePasswordDTO));
    }

    public List<Rent> getUserRents(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/allOrders");
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(new GenericType<List<Rent>>(){});
    }

    public List<Rent> getUserOrdersAdmin(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/orders");
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(new GenericType<List<Rent>>(){});
    }
}
