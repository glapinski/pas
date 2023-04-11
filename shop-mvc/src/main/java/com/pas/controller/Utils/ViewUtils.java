package com.pas.controller.Utils;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
public class ViewUtils {




    public static String getClassName(Object obj){
        return obj.getClass().getSimpleName();
    }

    public static ExternalContext context() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static boolean isManager() {
        return context().isUserInRole("Manager");
    }

    public static boolean isAdmin() {
        return context().isUserInRole("Admin");
    }

    public static boolean isBaseUser() {
        return context().isUserInRole("BaseUser");
    }

    public static boolean isUnauthorized() {
        return context().isUserInRole("Unauthorized");
    }


}
