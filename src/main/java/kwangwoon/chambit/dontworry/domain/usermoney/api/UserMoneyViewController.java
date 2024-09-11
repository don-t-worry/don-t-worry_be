package kwangwoon.chambit.dontworry.domain.usermoney.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyAccountResponse;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyInvestDepositResponse;
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
@RequestMapping("/api/money")
@Tag(name = "유저 잔고 조회")
public class UserMoneyViewController {
    private final UserMoneyViewService userMoneyViewService;

    @Operation(summary = "유저 계좌 잔고 조회")
    @GetMapping("/account/balance")
    public ResponseEntity<?> getAccountBalance(@AuthenticationPrincipal UserDetails user){
        UserMoneyAccountResponse accountBalance = userMoneyViewService.getAccountBalance(user);
        return ResponseEntity.ok(accountBalance);
    }

    @Operation(summary = "유저 투자금 회수 가능한 잔고 조회")
    @GetMapping("/invest/deposit")
    public ResponseEntity<?> getInvestDeposit(@AuthenticationPrincipal UserDetails user){
        UserMoneyInvestDepositResponse investDeposit = userMoneyViewService.getInvestDeposit(user);
        return ResponseEntity.ok(investDeposit);
    }

}
