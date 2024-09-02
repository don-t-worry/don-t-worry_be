package kwangwoon.chambit.dontworry.domain.stock.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "주식 api")
@RequestMapping("/api/stock")
public class StockController {
    private final StockService stockService;

    @GetMapping("/search")
    @Operation(summary = "주식 검색 api", description = "무한 스크롤을 위해 page입력 받음")
    public ResponseEntity<?> getStockSearchPage(@RequestParam("searchKeyword") String searchKeyword,@RequestParam("page") int page){
        PageRequest pr = PageRequest.of(page,10, Sort.by("stockName"));
        return ResponseEntity.ok(stockService.getSearchStock(searchKeyword, pr));
    }

}
