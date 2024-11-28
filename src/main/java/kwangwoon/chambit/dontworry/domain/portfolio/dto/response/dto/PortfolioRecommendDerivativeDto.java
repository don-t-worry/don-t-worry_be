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
    private Double riskReductionRate;
    private String stockImageUrl;

    @Builder
    public PortfolioRecommendDerivativeDto(Portfolio portfolio){
        this.derivativeName = portfolio.getDerivative().getDerivativeName();
        this.derivativeCode = portfolio.getDerivative().getDerivativeCode();
        this.stockName = portfolio.getStock().getStockName();
        this.derivativeQuantity = portfolio.getDerivativeQuantity();
//        this.riskReductionRate = portfolio.getRiskReductionRate();
        this.stockImageUrl = portfolio.getStock().getImageUrl();
    }

    public PortfolioRecommendDerivativeDto(Object[] hedge, Long stockQuantity){
        this.riskReductionRate = (double) hedge[2];
        this.derivativeName = (String) hedge[3];
        this.derivativeCode = (String) hedge[4];
        this.stockName = (String) hedge[5];
        this.stockImageUrl = (String) hedge[6];
        this.derivativeQuantity = stockQuantity * (long)hedge[7];
    }
}
