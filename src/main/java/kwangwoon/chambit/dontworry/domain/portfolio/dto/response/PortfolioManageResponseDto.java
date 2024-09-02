package kwangwoon.chambit.dontworry.domain.portfolio.dto.response;

import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioElementDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PortfolioManageResponseDto {

    private List<PortfolioPieDto> pieChart;
    private List<PortfolioElementDto> stocks;

    @Builder
    public PortfolioManageResponseDto(List<PortfolioPieDto> pieChart, List<PortfolioElementDto> stocks){

        this.pieChart = pieChart;
        this.stocks = stocks;
    }
}
