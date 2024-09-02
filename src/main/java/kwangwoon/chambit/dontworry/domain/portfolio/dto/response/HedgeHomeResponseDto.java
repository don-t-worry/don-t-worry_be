package kwangwoon.chambit.dontworry.domain.portfolio.dto.response;

import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class HedgeHomeResponseDto {
    private List<PortfolioPieDto> pieChart;
    private List<PortfolioRecommendDerivativeDto> hedgeRecommend2;

    @Builder
    public HedgeHomeResponseDto(List<PortfolioPieDto> pieChart, List<PortfolioRecommendDerivativeDto> hedgeRecommend2){
        this.pieChart = pieChart;
        this.hedgeRecommend2 = hedgeRecommend2;
    }

}
