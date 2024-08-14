package kwangwoon.chambit.dontworry.global.security.oauth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
public class Oauth2ClientDto {
    private String username;
    private String role;
    private Boolean isExist;
    private String oauthName;


    @Builder
    public Oauth2ClientDto(String username, String role, Boolean isExist, String oauthName){
        this.username = username;
        this.role = role;
        this.isExist = isExist;
        this.oauthName = oauthName;
    }
}
