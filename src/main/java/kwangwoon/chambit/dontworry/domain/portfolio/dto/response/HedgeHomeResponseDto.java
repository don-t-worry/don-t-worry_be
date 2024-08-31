package kwangwoon.chambit.dontworry.domain.portfolio.dto.response;

import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class HedgeHomeResponseDto {
    private String name;
    private List<PortfolioPieDto> pieChart;
    private List<PortfolioRecommendDerivativeDto> hedgeRecommend2;

    @Builder
    public HedgeHomeResponseDto(String name, List<PortfolioPieDto> pieChart, List<PortfolioRecommendDerivativeDto> hedgeRecommend2){
        this.name = name;
        this.pieChart = pieChart;
        this.hedgeRecommend2 = hedgeRecommend2;
    }

}
