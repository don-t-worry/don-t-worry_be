package kwangwoon.chambit.dontworry.domain.importantIndex.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.CommodityRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.IndexRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.InterestRateRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.RateRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.service.ImportantIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/indexes")
@Tag(name = "주요지수 api")
public class ImportantIndexController {
    private final ImportantIndexService importantIndexService;

    @GetMapping
    @Operation(summary = "주요 지수 조회")
    public ResponseEntity<?> getAllIndexes(){
        return ResponseEntity.ok(importantIndexService.getAllIndexes());
    }


}
