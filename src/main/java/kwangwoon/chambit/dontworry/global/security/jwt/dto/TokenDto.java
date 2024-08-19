package kwangwoon.chambit.dontworry.global.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenDto {
    String refreshToken;
    String accessToken;
    String grant;
}
