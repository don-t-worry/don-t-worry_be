package kwangwoon.chambit.dontworry.domain.deviceToken.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.deviceToken.dto.ExamDto;
import kwangwoon.chambit.dontworry.domain.deviceToken.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
@Tag(name = "fcm api")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;

//    @GetMapping("/arbitrage")
//    @Operation(summary = "시세차익 ", description = "토큰 정보 필요함, pie chart에 대한 정보와, 헷지 추천 상품 보여줌")
//    public ResponseEntity<?> getHedgeHome(@AuthenticationPrincipal UserDetails userDetails){
//        return ResponseEntity.ok(portfolioService.getHedgeHome(userDetails));
//    }

    @PostMapping("/exam")
    @Operation(summary = "토큰 보내는 예시 api")
    public ResponseEntity<?> sendMessage(@RequestBody ExamDto examDto) throws IOException {
        ExamDto examDto1 = deviceTokenService.sendExamMessage(examDto);
        return ResponseEntity.ok(examDto1);
    }
}
