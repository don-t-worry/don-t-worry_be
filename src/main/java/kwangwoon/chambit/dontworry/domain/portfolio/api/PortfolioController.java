package kwangwoon.chambit.dontworry.domain.portfolio.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioInsertDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioUpdateDto;
import kwangwoon.chambit.dontworry.domain.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
@Tag(name = "포트폴리오 api")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/hedgehome")
    @Operation(summary = "헷지 home 화면")
    public ResponseEntity<?> getHedgeHome(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getHedgeHome(userDetails));
    }

    @GetMapping("/recommend/hedgeall")
    @Operation(summary = "헷지 상품 추천 화면", description = "무한 스크롤을 위해 page 입력받음")
    public ResponseEntity<?> getRecommend(@RequestParam("page") int page, @AuthenticationPrincipal UserDetails userDetails){
        PageRequest pageRequest = PageRequest.of(page,10, Sort.by("stockQuantity").descending());
        return ResponseEntity.ok(portfolioService.getAllPortfolioRecommendDerivative(pageRequest,userDetails));
    }

    @GetMapping("/edit")
    @Operation(summary = "사용자 포트폴리오 편집 페이지", description = "무한 스크롤 고려사항 api")
    public ResponseEntity<?> getEditList(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getPortfolioEdit(userDetails));
    }

    @GetMapping("/manage")
    @Operation(summary = "사용자 포트폴리오 페이지", description = "무한 스크롤 고려사항 api")
    public ResponseEntity<?> getPortfolioManage(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getPortfolioManage(userDetails));
    }

    @PostMapping("/insert")
    @Operation(summary = "포트폴리오 입력")
    public ResponseEntity<?> insertPortfolio(@RequestBody PortfolioInsertDto portfolioInsertDto, @AuthenticationPrincipal UserDetails userDetails){
        portfolioService.insertPortfolio(portfolioInsertDto, userDetails);
        return ResponseEntity.ok("success");
    }


    @PutMapping()
    @Operation(summary = "포트폴리오 수정")
    public ResponseEntity<?> modifyPortfolio(@PathVariable("portfolioId") Long id, @RequestBody PortfolioUpdateDto portfolioUpdateDto){
        portfolioService.updatePortfolio(id, portfolioUpdateDto);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping()
    @Operation(summary = "포트폴리오 삭제")
    public ResponseEntity<?> deletePortfolio(@RequestParam List<Long> portfolioIds){
        portfolioService.deletePortfolios(portfolioIds);
        return ResponseEntity.ok("success");
    }



}
