package kwangwoon.chambit.dontworry.global.common.service;

import io.jsonwebtoken.JwtException;
import kwangwoon.chambit.dontworry.global.infra.redis.refreshToken.RefreshToken;
import kwangwoon.chambit.dontworry.global.infra.redis.refreshToken.RefreshTokenService;
import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public String updateToken(String accessToken){
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(accessToken);


        if(!jwtUtil.validateToken(refreshToken.getRefreshToken())){
            throw new JwtException("Refresh Token 만료");
        }

        String role = jwtUtil.getRole(refreshToken.getRefreshToken());
        String username = jwtUtil.getUsername(refreshToken.getRefreshToken());
        TokenDto token = jwtUtil.createToken(username, role);

        refreshTokenService.save(token,username);

        return token.getAccessToken();
    }
}
