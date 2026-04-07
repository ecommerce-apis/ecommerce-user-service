package com.ecommerce.user.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        try {
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            // Continue the filter chain if everything is okay
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            // This catches the specific error you saw in the logs
            handleException(response, "Your token has expired. Please login again.", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            // This catches any other JWT issues (malformed, tampered, etc.)
            handleException(response, "Invalid token provided.", HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /**
     * Helper method to write a clean JSON error response
     */
    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        // We manually write the JSON string because we are outside the Controller's reach
        String jsonResponse = String.format("{\"error\": \"%s\", \"status\": %d}", message, status);
        response.getWriter().write(jsonResponse);
    }
}