package tech.ada.banco.service.login;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import tech.ada.banco.model.Usuario;
import tech.ada.banco.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Value;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import io.jsonwebtoken.Claims;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.token.secret-key}")
    private String secret;

    private final UsuarioRepository usuarioRepository;

    public String createToken(UserDetails userDetails) {
        var usuario = usuarioRepository.findByUsername(userDetails.getUsername());
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", usuario.get().getRoles());
        claims.put("cpf",usuario.get().getCpf());
        claims.put("id_usuario",usuario.get().getId());

        final var now = LocalDateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(convertFromLocalDateTime(now))
                .setExpiration(convertFromLocalDateTime(now.plusHours(1L)))
                .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Date convertFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.secret.getBytes()));
    }

    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    public UserDetails getUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");
        String cpf = (String) claims.get("cpf");
        Integer id = (Integer) claims.get("id_usuario");

        String[] jwtSubject = subject.split(",");

        return Usuario.
                builder()
                .roles(roles)
                .cpf(cpf)
                .id(Long.valueOf(id))
                .username(jwtSubject[0])
                .build();
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final var claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final var username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

}
