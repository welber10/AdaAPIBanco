package tech.ada.banco.service.login;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        final String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            final String token = header.substring(7);
            this.authenticateUserFromToken(request, token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUserFromToken(HttpServletRequest request, String token) {
        final String username = this.jwtService.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtService.getUserDetails(token);
            if (this.jwtService.isTokenValid(token, userDetails)) {
                this.setAuthTokenOnSecurityContext(request, userDetails);
            }
        }
    }

    private void setAuthTokenOnSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        final var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
