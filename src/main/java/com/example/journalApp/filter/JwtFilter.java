package com.example.journalApp.filter;

import com.example.journalApp.utills.JwtUtill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtill jwtUtill;

    /**
     * Intercepts each request to perform JWT-based authentication before the request reaches the controller.
     * - Reads the "Authorization" header and extracts the Bearer token (JWT).
     * - Decodes the JWT to get the username (subject) and validates the token.
     * - If valid, loads user details, builds an authentication and sets it in SecurityContext so Spring Security
     *   treats the request as authenticated. If invalid or missing, the request continues unauthenticated.
     * - Always calls filterChain.doFilter so the request proceeds down the filter chain.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Read the Authorization header (e.g. "Bearer <token>")
        String authenticationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            // Strip "Bearer " prefix to get the raw JWT
            jwt = authenticationHeader.substring(7);
            if (jwt != null && !jwt.isEmpty()) {
                try {
                    // Decode JWT and get the username (subject claim) without full validation yet
                    username = jwtUtill.extractUsername(jwt);
                } catch (Exception e) {
                    // Log the error or handle invalid token silently
                    // e.printStackTrace();
                }
            }
        }
        if(username != null) {
            // Load user from DB so we can build a full UserDetails for Spring Security
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validate signature and expiry of the JWT
            if(jwtUtill.validateToken(jwt)) {
                // Build Spring Security authentication token with user and authorities
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Attach request details (e.g. remote address) to the authentication
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Mark this request as authenticated for the rest of the filter chain and controller
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // Continue to the next filter or the controller
        filterChain.doFilter(request, response);
    }
}
