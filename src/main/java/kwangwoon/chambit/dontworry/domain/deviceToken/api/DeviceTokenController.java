package kwangwoon.chambit.dontworry.domain.deviceToken.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kwangwoon.chambit.dontworry.domain.deviceToken.dto.ExamDto;
import kwangwoon.chambit.dontworry.domain.deviceToken.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/fcm")
@Tag(name = "fcm api")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;
    private final String arbitrageServerIp;

    public DeviceTokenController(
            DeviceTokenService deviceTokenService,
            @Value("${fcm.arbitrageIP}")String arbitrageServerIp){
        this.deviceTokenService = deviceTokenService;
        this.arbitrageServerIp = arbitrageServerIp;
    }

    @GetMapping("/arbitrage")
    @Operation(summary = "시세차익 토큰 보내는 api", description = "시세차익 서버에서만 작동")
    public ResponseEntity<?> sendArbitrageToken(HttpServletRequest request) throws IOException {

        String clientIP = request.getRemoteAddr();

        if(!clientIP.equals(arbitrageServerIp)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        deviceTokenService.arbitrageCatch();
        return ResponseEntity.ok("success");
    }

    @PostMapping("/exam")
    @Operation(summary = "토큰 보내는 예시 api")
    public ResponseEntity<?> sendMessage(@RequestBody ExamDto examDto) throws IOException {
        ExamDto examDto1 = deviceTokenService.sendExamMessage(examDto);
        return ResponseEntity.ok(examDto1);
    }
}
