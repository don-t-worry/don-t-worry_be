package kwangwoon.chambit.dontworry.domain.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsernameExistDto {
    private String username;
    private String token;
    private String deviceId;
}
