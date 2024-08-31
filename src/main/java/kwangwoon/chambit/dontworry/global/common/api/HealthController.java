package kwangwoon.chambit.dontworry.global.common.api;

import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public String getHealthCheck(){
        return "hi";
    }

    @GetMapping("/test/user/data")
    public String getUserData(@AuthenticationPrincipal Authentication authentication){
        CustomOauth2ClientDto dto = (CustomOauth2ClientDto) authentication.getPrincipal();
        return dto.getUsername();
    }
}
