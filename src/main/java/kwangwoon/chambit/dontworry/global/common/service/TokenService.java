package kwangwoon.chambit.dontworry.global.common.service;

import kwangwoon.chambit.dontworry.global.infra.redis.RefreshToken;
import kwangwoon.chambit.dontworry.global.infra.redis.RefreshTokenService;
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

        String role = jwtUtil.getRole(refreshToken.getRefreshToken());
        String username = jwtUtil.getUsername(refreshToken.getRefreshToken());
        TokenDto token = jwtUtil.createToken(username, role);

        refreshTokenService.save(token,username);

        return token.getAccessToken();
    }
}
