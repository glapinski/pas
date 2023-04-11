package com.pas.controller.Auth;

import com.pas.restClient.AuthApiClient;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

@ViewScoped
@Named
@Getter
@Setter
public class AuthController implements Serializable {
    String login;
    String password;
    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;
    @Inject
    AuthApiClient authApiClient;

    public String login() {
        String jwt;
        try {
            jwt = authApiClient.login(login, password);
        } catch (Exception e) {
            return "Index";
        }
        if (jwt != null) {
            jwtTokenHolderBean.setLoggedInUser(jwt);
            return "Index";
        } else {

        }
        return "Index";
    }
}
