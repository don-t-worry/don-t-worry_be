package kwangwoon.chambit.dontworry.global.infra.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken", timeToLive = 3600)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String username;

    @Indexed
    private String accessToken;

    private String refreshToken;

    public void updateAccessToken(String accessToken){
        this.accessToken = accessToken;
    }
}
