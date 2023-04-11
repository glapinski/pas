package com.pas.restClient;

import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Rent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class RentApiClient extends RestClient<Rent> implements Serializable {

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    public RentApiClient() {
        super();
    }

    public void createRent(UUID userId,UUID productId) {
        WebTarget webTarget = client.path("/rents/create").queryParam("userId", userId.toString()).queryParam("productId",productId.toString());
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(""));
    }

   /* public void deliverOrder(UUID id) {
        WebTarget webTarget = client.path("/rents/" + id.toString() + "/deliver");
        Response response = webTarget.request()
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(""));
    }*/
    public List<Rent> getAllRents(){
        WebTarget webTarget = client.path("/rents");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<Rent>>(){});
    }
}
