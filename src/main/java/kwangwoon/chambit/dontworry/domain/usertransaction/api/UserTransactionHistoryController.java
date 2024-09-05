package kwangwoon.chambit.dontworry.domain.usertransaction.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.usertransaction.dto.request.UserTransactionInsertDto;
import kwangwoon.chambit.dontworry.domain.usertransaction.service.UserTransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
@Tag(name = "거래 api")
public class UserTransactionHistoryController {

    private final UserTransactionHistoryService userTransactionHistoryService;

    @PostMapping
    @Operation(summary = "거래 api", description = "deposit은 입금 관련, withdraw는 출금 관련, account는 계좌, investment는 투자")
    public ResponseEntity<?> occurTransaction(@RequestBody UserTransactionInsertDto userTransactionInsertDto, @AuthenticationPrincipal UserDetails user){
        userTransactionHistoryService.occurTransaction(userTransactionInsertDto,user);

        return ResponseEntity.ok("success");
    }
}
