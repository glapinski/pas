package com.pas.auth;

import com.nimbusds.jwt.SignedJWT;
import com.pas.controller.Auth.JwtTokenHolderBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@ApplicationScoped
@AutoApplySession
public class JWTAuthMechanism implements HttpAuthenticationMechanism {

    public final static String BEARER = "Bearer ";

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        if (jwtTokenHolderBean.getJwtToken().isBlank()) {
            return httpMessageContext.notifyContainerAboutLogin("Unauthorized", Collections.singleton("Unauthorized"));
        }
        String jwt = jwtTokenHolderBean.getJwtToken().substring(BEARER.length()).trim();;
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            String login = signedJWT.getJWTClaimsSet().getSubject();
            String groups = signedJWT.getJWTClaimsSet().getStringClaim("auth");
            return httpMessageContext.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return httpMessageContext.notifyContainerAboutLogin("Unauthorized", new HashSet<>(Arrays.asList("Unauthorized")));
        }
    }
}
