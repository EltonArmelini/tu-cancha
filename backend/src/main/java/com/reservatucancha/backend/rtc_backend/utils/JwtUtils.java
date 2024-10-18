package com.reservatucancha.backend.rtc_backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {


    Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.expire_ms}")
    private Integer expire_ms;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String generateToken(Authentication auth){
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        LOGGER.info("Token Algorithm: " + algorithm);

        String username = auth.getPrincipal().toString();

        String roles = auth.getAuthorities()
                .stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith("ROLE_"))
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        LOGGER.info("Generated Username and Authorities: " + username + " with " + roles);

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expire_ms))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        LOGGER.info("Token generated succesfully: ", jwtToken);
        return jwtToken;
    }



    public DecodedJWT jwtValidation(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            return verifier.verify(token);

        } catch (JWTVerificationException exception)  {
            throw new JWTVerificationException("Invalid token. Details: " + exception.getMessage());
        }

    }
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }
    public Date getExpirationDate(DecodedJWT decodedJWT) {return decodedJWT.getExpiresAt();}
    public boolean checkExpirationToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expirationDate = decodedJWT.getExpiresAt();
        return expirationDate != null && expirationDate.before(new Date());
    }
}
