package kwangwoon.chambit.dontworry.global.common.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class HealthController {
    @GetMapping("/")
    @Operation(summary = "토큰 정보 없이 테스트 할 수 있는 api" , description = "cicd용 health check때 사용")
    public String getHealthCheck(){
        return "hi";
    }

    @GetMapping("/test/user/data")
    @Operation(summary = "토큰 정보 있이 테스트 할 수 있는 api")
    public String getUserData(@AuthenticationPrincipal UserDetails user){
        return user.getUsername();
    }
}
