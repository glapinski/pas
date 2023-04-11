package com.pas.controller.Auth;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@SessionScoped
@Named
@Getter
@Setter
public class JwtTokenHolderBean implements Serializable {
    private String jwtToken="";

    @Inject
    private HttpServletRequest httpServletRequest;

    public void setLoggedInUser(String jwt){
        this.jwtToken = jwt;
        try {
            httpServletRequest.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public String logout() {
        invalidateSession();
        return "Start";
    }

    public void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
