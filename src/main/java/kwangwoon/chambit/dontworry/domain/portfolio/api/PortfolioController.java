package kwangwoon.chambit.dontworry.domain.portfolio.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioDeleteDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioInsertDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioUpdateDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.InsertUpdateResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
@Tag(name = "포트폴리오 api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/hedgehome")
    @Operation(summary = "헷지 home 화면", description = "토큰 정보 필요함, pie chart에 대한 정보와, 헷지 추천 상품 보여줌")
    public ResponseEntity<?> getHedgeHome(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getHedgeHome(userDetails));
    }

//    @GetMapping("/recommend/hedgeall")
//    @Operation(summary = "헷지 상품 추천 화면", description = "무한 스크롤을 위해 page 입력받음")
//    public ResponseEntity<?> getRecommend(@RequestParam("page") int page, @AuthenticationPrincipal UserDetails userDetails){
//        PageRequest pageRequest = PageRequest.of(page,10, Sort.by("stockQuantity").descending());
//        return ResponseEntity.ok(portfolioService.getAllPortfolioRecommendDerivative(pageRequest,userDetails));
//    }

    @GetMapping("/hedgeall")
    @Operation(summary = "헷지 상품 추천 화면", description = "모든 헷지 추천 상품 보여줌, 현재는 list가 반환값임")
    public ResponseEntity<?> getRecommend(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getAllPortfolioRecommendDerivative(userDetails));
    }

    @GetMapping("/edit")
    @Operation(summary = "사용자 포트폴리오 편집 페이지", description = "토큰 정보 필요함, 무한 스크롤 고려사항 api")
    public ResponseEntity<?> getEditList(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getPortfolioEdit(userDetails));
    }

    @GetMapping("/manage")
    @Operation(summary = "사용자 포트폴리오 페이지", description = "토큰 정보 필요함, 무한 스크롤 고려사항 api")
    public ResponseEntity<?> getPortfolioManage(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(portfolioService.getPortfolioManage(userDetails));
    }

    @PostMapping()
    @Operation(summary = "포트폴리오 입력", description = "토큰 정보 필요함")
    public ResponseEntity<?> insertPortfolio(@RequestBody PortfolioInsertDto portfolioInsertDto, @AuthenticationPrincipal UserDetails userDetails){
        InsertUpdateResponseDto insertUpdateResponseDto = portfolioService.insertPortfolio(portfolioInsertDto, userDetails);
        return ResponseEntity.ok(insertUpdateResponseDto);
    }


    @PutMapping("/{portfolioId}")
    @Operation(summary = "포트폴리오 수정")
    public ResponseEntity<?> modifyPortfolio(@PathVariable("portfolioId") Long id, @RequestBody PortfolioUpdateDto portfolioUpdateDto){
        InsertUpdateResponseDto insertUpdateResponseDto = portfolioService.updatePortfolio(id, portfolioUpdateDto);
        return ResponseEntity.ok(insertUpdateResponseDto);
    }

    @DeleteMapping()
    @Operation(summary = "포트폴리오 삭제")
    public ResponseEntity<?> deletePortfolio(@RequestBody PortfolioDeleteDto portfolioDeleteDto){
        portfolioService.deletePortfolios(portfolioDeleteDto.getPortfolioIds());
        return ResponseEntity.ok("success");
    }

//    @DeleteMapping()
//    @Operation(summary = "포트폴리오 삭제")
//    public ResponseEntity<?> deletePortfolio(@RequestParam("deleteIds") String portfolioDeleteIds){
//        List<Long> portfolioIds = Arrays.stream(portfolioDeleteIds.split(","))
//                .map(Long::parseLong)
//                .collect(Collectors.toList());
//
//        portfolioService.deletePortfolios(portfolioIds);
//        return ResponseEntity.ok("success");
//    }



}
