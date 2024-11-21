package kwangwoon.chambit.dontworry.domain.monthlyEarnings.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.monthlyEarnings.service.MonthlyEarningsService;
import kwangwoon.chambit.dontworry.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "월별 수익률 정보")
@RequestMapping("/api")
public class MonthlyEarningsController {
    private final MonthlyEarningsService monthlyEarningsService;

    @GetMapping("/monthly/earnings")
    @Operation(summary = "수익률 그래프 조회를 위한 api", description = "무한 스크롤로 인한 page받음")
    public ResponseEntity<?> getMonthEarnings(@RequestParam("page") int page){
        PageRequest pr = PageRequest.of(page-1, 12, Sort.by("monthDate").descending());
        return ResponseEntity.ok(monthlyEarningsService.getMonthlyEarnings(pr));
    }


}
