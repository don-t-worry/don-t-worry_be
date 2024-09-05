package kwangwoon.chambit.dontworry.domain.usermoney.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.usermoney.service.UserMoneyViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
@Tag(name = "시세차익 api")
public class UserMoneyViewController {

    private final UserMoneyViewService userMoneyViewService;

    @GetMapping("/invest")
    @Operation(summary = "유저의 시세차익 투자 화면")
    public ResponseEntity<?> getUserTradeInvest(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(userMoneyViewService.getUserTradeInvest(user));
    }
}
