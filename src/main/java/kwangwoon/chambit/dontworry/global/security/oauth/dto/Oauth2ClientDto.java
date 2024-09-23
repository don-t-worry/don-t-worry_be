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


    @Builder
    public Oauth2ClientDto(String username,String role, boolean isExist){
        this.username = username;
        this.role = "ROLE_USER";
        this.isExist = true;
    }
}
