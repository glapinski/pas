package com.pas.controller.Rent;

import com.pas.controller.Conversational;
import com.pas.model.Rent;
import com.pas.model.User.User;
import com.pas.restClient.RentApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class RentsController extends Conversational implements Serializable {
    @Inject
    RentApiClient rentApiClient;
    @Inject
    UserApiClient userApiClient;
    User currentUser;
    List<Rent> rents;

    @PostConstruct
    public void initCurrentOrders(){
        rents = rentApiClient.getAllRents();
    }
}
