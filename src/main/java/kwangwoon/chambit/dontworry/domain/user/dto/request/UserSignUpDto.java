package kwangwoon.chambit.dontworry.domain.user.dto.request;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignUpDto {
    private String name;
    private String username;
    private HedgeType hedgeType;
    private String token;
    private String deviceId;

    @Builder
    public UserSignUpDto(String name, String username, HedgeType hedgeType, String token, String deviceId){
        this.name = name;
        this.username = username;
        this.hedgeType = hedgeType;
        this.token = token;
        this.deviceId = deviceId;
    }

    public User toUser(){
        return User.builder()
                .name(name)
                .username(username)
                .hedgeType(hedgeType)
                .role("ROLE_USER")
                .build();
    }



}
