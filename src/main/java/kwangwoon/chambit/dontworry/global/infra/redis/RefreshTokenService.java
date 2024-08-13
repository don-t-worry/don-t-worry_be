package kwangwoon.chambit.dontworry.global.infra.redis;

import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void save(TokenDto token, String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token.getRefreshToken())
                .accessToken(token.getAccessToken())
                .username(username)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByUsername(String username){
        return refreshTokenRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("there is no Id"));
    }

    public void delete(String username){
        refreshTokenRepository.deleteById(username);
    }

    public void updateAccessToken(String username, String accessToken){
        RefreshToken refreshToken = refreshTokenRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("there is no Id"));

        refreshToken.updateAccessToken(accessToken);
    }
}
