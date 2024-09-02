package kwangwoon.chambit.dontworry.global.common.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.global.common.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "토큰 api")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @Operation(summary = "token재발급")
    @GetMapping("/refresh")
    public ResponseEntity<?> updateToken(HttpServletRequest request, HttpServletResponse response){
        String accessToken = request.getHeader("Authorization");

        String token = tokenService.updateToken(accessToken);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
    }
}
