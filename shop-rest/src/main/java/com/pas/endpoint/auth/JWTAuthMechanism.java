package com.pas.endpoint.auth;

import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@ApplicationScoped
@DeclareRoles({"Admin", "Manager", "BaseUser", "Unauthorized"})
public class JWTAuthMechanism implements HttpAuthenticationMechanism {
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER = "Bearer ";

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) {
        if(!httpServletRequest.isSecure()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED);
            httpMessageContext.setResponse(httpServletResponse);
            return httpMessageContext.doNothing();
        }

        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
           return httpMessageContext.notifyContainerAboutLogin("Unauthorized", new HashSet<>(Arrays.asList("Unauthorized")));
        }

        String jwtSerializedToken = authorizationHeader.substring(BEARER.length()).trim();
        if(JWTAuthTokenUtils.validateJwtSignature(jwtSerializedToken)) {
            try {
                SignedJWT signedJWT = SignedJWT.parse(jwtSerializedToken);
                String login = signedJWT.getJWTClaimsSet().getSubject();
                String groups = signedJWT.getJWTClaimsSet().getStringClaim("auth");
                Date expirationTime = (Date) (signedJWT.getJWTClaimsSet().getClaim("exp"));

                if(new Date().after(expirationTime)) {
                    return httpMessageContext.responseUnauthorized();
                }

                // from now container knows user login and user groups, so web.xml can verify authority
                return httpMessageContext.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));
            } catch (ParseException e) {
                System.err.println(e.getMessage());
                return httpMessageContext.notifyContainerAboutLogin("Unauthorized", new HashSet<>(Arrays.asList("Unauthorized")));
            }
        }
        return httpMessageContext.notifyContainerAboutLogin("Unauthorized", new HashSet<>(Arrays.asList("Unauthorized")));
    }
}
