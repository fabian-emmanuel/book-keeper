package com.findar.bookkeeper.services.implementations;


import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.constants.Security;
import com.findar.bookkeeper.dtos.TokenResponseDTO;
import com.findar.bookkeeper.exceptions.UnAuthorizedException;
import com.findar.bookkeeper.config.properties.JWTProperty;
import com.findar.bookkeeper.services.AuthTokenService;
import com.findar.bookkeeper.utils.UniqueRefUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final JWTProperty jwtProperty;

    @Override
    public Mono<TokenResponseDTO> generateAccessToken(String userName, String userRole) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Security.USER_ROLE, userRole);
        claims.put("id", UniqueRefUtil.generateUniqueRef("tk", 7));
        Date now = new Date();
        Date validity = Date.from(Instant.now().plus(jwtProperty.expiration(), ChronoUnit.MILLIS));
        return Mono.fromCallable(() -> TokenResponseDTO.builder()
                .token(createToken(claims, userName, now, validity))
                .expiresIn(String.valueOf(jwtProperty.expiration() / 1000))
                .build());
    }

    @Override
    public Mono<String> getAuthenticatedUserEmail() {
        log.info("AuthTokenServiceImpl::getAuthenticatedUserEmail -> STARTED");
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {
                    if (authentication != null) {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        log.info("AuthTokenServiceImpl::getAuthenticatedUserEmail -> USER RETRIEVED::{}",userDetails.getUsername());
                        return Mono.just(userDetails.getUsername());
                    } else {
                        return Mono.error(new UnAuthorizedException(Message.UNAUTHORIZED));
                    }
                })
                .doOnError(throwable -> {
            throw new UnAuthorizedException(Message.INVALID_EXPIRED_TOKEN);
        });
    }

    @Override
    public String extractUserEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Mono<String> extractTokenFromRequest(ServerWebExchange exchange) {
        String headerAuth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return Mono.just(headerAuth.substring(7));
        }
        return Mono.empty();
    }

    @Override
    public boolean validateToken(String token, String authenticatedUserName) {
        final String username = extractUserEmailFromToken(token);
        return (username.equals(authenticatedUserName) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Date created, Date validity) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(created)
                .setExpiration(validity)
                .signWith(getkey(), SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT")
                .setIssuer("book-keeper")
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getkey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.secretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
