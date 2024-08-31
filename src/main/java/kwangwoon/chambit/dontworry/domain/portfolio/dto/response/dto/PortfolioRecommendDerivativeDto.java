package kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import lombok.Builder;
import lombok.Data;

@Data
public class PortfolioRecommendDerivativeDto {
    private String derivativeName;
    private String derivativeCode;
    private String stockName;
    private Long derivativeQuantity;
    private Long riskReductionRate;
    private String stockImageUrl;

    @Builder
    public PortfolioRecommendDerivativeDto(Portfolio portfolio){
        this.derivativeName = portfolio.getDerivative().getDerivativeName();
        this.derivativeCode = portfolio.getDerivative().getDerivativeCode();
        this.stockName = portfolio.getStock().getStockName();
        this.derivativeQuantity = portfolio.getDerivativeQuantity();
        this.riskReductionRate = portfolio.getRiskReductionRate();
        this.stockImageUrl = portfolio.getStock().getImageUrl();
    }
}
