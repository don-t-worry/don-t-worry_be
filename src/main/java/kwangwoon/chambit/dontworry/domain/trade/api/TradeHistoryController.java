package kwangwoon.chambit.dontworry.domain.trade.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.trade.service.TradeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
@Tag(name = "시세차익 api")
public class TradeHistoryController {
    private final TradeHistoryService tradeHistoryService;

    @GetMapping("/history")
    @Operation(summary = "서비스의 시세차익 거래 내역",description = "무한 스크롤을 위해 page입력받음")
    public ResponseEntity<?> getAllTradeHistory(@RequestParam("page") int page){
        PageRequest pr = PageRequest.of(page,12, Sort.by("time"));
        return ResponseEntity.ok(tradeHistoryService.getAllTradeHistory(pr));
    }

    @GetMapping("home")
    @Operation(summary = "시세차익 거래 홈 화면", description = "")
    public ResponseEntity<?> getTradeHomeDto(){
        return ResponseEntity.ok(tradeHistoryService.getTradeHomeDto());
    }
}
