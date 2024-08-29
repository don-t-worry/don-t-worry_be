package kwangwoon.chambit.dontworry.domain.portfolio.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioInsertDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioUpdateDto;
import kwangwoon.chambit.dontworry.domain.portfolio.service.PortfolioService;
import kwangwoon.chambit.dontworry.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseDto<?> getHedgeHome(@AuthenticationPrincipal Authentication authentication){
        return ResponseDto.success(portfolioService.getHedgeHome(authentication));
    }

    @GetMapping("/recommend/hedgeall")
    @Operation(summary = "헷지 상품 추천 화면", description = "무한 스크롤을 위해 page입력받음")
    public ResponseDto<?> getRecommend(@RequestParam("page") int page, @AuthenticationPrincipal Authentication authentication){
        PageRequest pageRequest = PageRequest.of(page,5, Sort.by("stockQuantity").descending());
        return ResponseDto.success(portfolioService.getAllPortfolioRecommendDerivative(pageRequest,authentication));
    }

    @GetMapping("/edit")
    @Operation(summary = "사용자 포트폴리오 편집 페이지")
    public ResponseDto<?> getEditList(@AuthenticationPrincipal Authentication authentication){
        return ResponseDto.success(portfolioService.getPortfolioEdit(authentication));
    }

    @GetMapping("/manage")
    @Operation(summary = "사용자 포트폴리오 페이지")
    public ResponseDto<?> getPortfolioManage(@AuthenticationPrincipal Authentication authentication){
        return ResponseDto.success(portfolioService.getPortfolioManage(authentication));
    }

    @PostMapping("/insert")
    @Operation(summary = "포트폴리오 입력")
    public ResponseDto<?> insertPortfolio(@RequestBody PortfolioInsertDto portfolioInsertDto, @AuthenticationPrincipal Authentication authentication){
        portfolioService.insertPortfolio(portfolioInsertDto, authentication);
        return ResponseDto.success();
    }


    @PutMapping()
    @Operation(summary = "포트폴리오 수정")
    public ResponseDto<?> modifyPortfolio(@PathVariable("portfolioId") Long id, @RequestBody PortfolioUpdateDto portfolioUpdateDto){
        portfolioService.updatePortfolio(id, portfolioUpdateDto);
        return ResponseDto.success();
    }

    @DeleteMapping()
    @Operation(summary = "포트폴리오 삭제")
    public ResponseDto<?> deletePortfolio(@RequestParam List<Long> portfolioIds){
        portfolioService.deletePortfolios(portfolioIds);
        return ResponseDto.success();
    }



}
