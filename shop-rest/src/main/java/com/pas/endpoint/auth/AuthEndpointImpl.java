package com.pas.endpoint.auth;

import com.pas.manager.UserManager;
import com.pas.model.User.User;
import com.pas.model.dto.JwtResponse;
import com.pas.model.dto.UserAuthDTO;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth/login")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthEndpointImpl {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    UserManager userManager;

    @POST
    @PermitAll
    public Response authenticate(@NotNull Credentials credentials) {
        Credential credential = new UsernamePasswordCredential(credentials.getLogin(), new Password(credentials.getPassword()));
        CredentialValidationResult cValResult = identityStoreHandler.validate(credential);
        if (cValResult.getStatus().equals(VALID)) {
            String jwtToken = JWTAuthTokenUtils.generateJwtString(cValResult);
            User user = userManager.findOneByLogin(credentials.getLogin());
            JwtResponse jwtResponse = new JwtResponse(jwtToken, new UserAuthDTO(user.getClass().getSimpleName(), user.getLogin(), user.getId()));
            return Response.accepted().header("Authentication", "Bearer " + jwtToken).type(MediaType.APPLICATION_JSON).entity(jwtResponse).build();
        } else {
            return Response.status(UNAUTHORIZED).entity(UNAUTHORIZED).build();
        }
    }

    @Data
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Credentials {
        private String login;
        private String password;
    }
}
