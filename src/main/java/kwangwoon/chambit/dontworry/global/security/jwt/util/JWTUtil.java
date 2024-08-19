package kwangwoon.chambit.dontworry.global.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import kwangwoon.chambit.dontworry.global.infra.redis.RefreshTokenService;
import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_EXPIRATION_TIME_SECOND;
    private final long REFRESH_TOKEN_EXPIRATION_TIME_SECOND;

    private final RefreshTokenService refreshTokenService;

    public JWTUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpiration,
            RefreshTokenService refreshTokenService
    ){
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        // 지정된 서명 알고리즘과 비밀키를 사용하여 토큰 서명할때 사용
        ACCESS_TOKEN_EXPIRATION_TIME_SECOND = accessTokenExpiration;
        REFRESH_TOKEN_EXPIRATION_TIME_SECOND = refreshTokenExpiration;
        this.refreshTokenService = refreshTokenService;
    }

    public TokenDto createToken(String username, String role){
        TokenDto token = TokenDto.builder()
                .accessToken(createAccessToken(username, role))
                .refreshToken(createRefreshToken(username))
                .grant("Bearer")
                .build();
        refreshTokenService.save(token, username);
        return token;
    }
    public String createAccessToken(String username, String role){
        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(OffsetDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION_TIME_SECOND).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(OffsetDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_TIME_SECOND).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token){

        try{
            Claims payload = Jwts.parser()
                    .verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return payload.getExpiration().after(new Date()); // 만료되지 않았다면 true
        }catch (Exception e){
            return false;
        }
    }

    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }

    public Authentication getAuthentication(String token){
        Oauth2ClientDto oauth2Client = Oauth2ClientDto.builder()
                .username(getUsername(token))
                .role(getRole(token))
                .build();
        CustomOauth2ClientDto customOauth2ClientDto = new CustomOauth2ClientDto(oauth2Client);

        return new UsernamePasswordAuthenticationToken(customOauth2ClientDto,null,customOauth2ClientDto.getAuthorities());
    }
}
