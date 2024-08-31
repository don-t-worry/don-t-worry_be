package kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import lombok.Builder;
import lombok.Data;

@Data
public class PortfolioElementDto {
    private Long portfolioId;
    private String stockName;
    private Long stockQuantity;
    private String stockImage;
    private Long presentPrice;
    private Long purchasePrice;
    private Long profit;
    private Float profitRate;

    @Builder
    public PortfolioElementDto(Portfolio portfolio, Long presentPrice, Long profit, Float profitRate){
        this.portfolioId = portfolio.getId();
        this.stockName = portfolio.getStock().getStockName();
        this.stockQuantity = portfolio.getStockQuantity();
        this.stockImage = portfolio.getStock().getImageUrl();
        this.presentPrice = presentPrice;
        this.profit = profit;
        this.profitRate = profitRate;
        this.purchasePrice = portfolio.getStockAveragePrice();
    }
}
