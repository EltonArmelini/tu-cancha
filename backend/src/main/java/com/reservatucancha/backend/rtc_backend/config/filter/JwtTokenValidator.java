package com.reservatucancha.backend.rtc_backend.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.reservatucancha.backend.rtc_backend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException{

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer")) {
            jwtToken = jwtToken.substring(7);
            if (jwtUtils.checkExpirationToken(jwtToken)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("The token has expired. Please login again.");
                return;
            }
            DecodedJWT decodedJWT = jwtUtils.jwtValidation(jwtToken);
            String username = jwtUtils.extractUsername(decodedJWT);
            String stringRoles = jwtUtils.getSpecificClaim(decodedJWT, "roles").asString();

            Collection<? extends GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(stringRoles);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, roles);
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        }
          filterChain.doFilter(request, response);
    }
}
