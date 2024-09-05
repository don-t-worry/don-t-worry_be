package kwangwoon.chambit.dontworry.domain.usertrade.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.usertrade.service.UserTradeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
@Tag(name = "시세차익 api")
public class UserTradeHistoryController {

    private final UserTradeHistoryService userTradeHistoryService;

    @GetMapping("/userhistory")
    @Operation(summary = "유저의 시세차익 거래내역", description = "무한 스크롤을 위한 page입력받음")
    public ResponseEntity<?> getUserTradeHistory(@RequestParam("page")int page, @AuthenticationPrincipal UserDetails user){
        PageRequest pr = PageRequest.of(page,10, Sort.by("time"));
        return ResponseEntity.ok(userTradeHistoryService.getUserTradeHistory(pr,user));
    }
}
