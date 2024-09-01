package kwangwoon.chambit.dontworry.domain.portfolio.dto.response;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import lombok.Builder;
import lombok.Data;

@Data
public class PortfolioEditResponseDto {
    private Long portfolioId;
    private String stockName;
    private Long stockQuantity;
    private String stockImage;
    private Long presentPrice;
    private Long purchasePrice;

    @Builder
    public PortfolioEditResponseDto(Portfolio portfolio, Long presentPrice){
        this.portfolioId = portfolio.getId();
        this.stockName = portfolio.getStock().getStockName();
        this.stockImage = portfolio.getStock().getImageUrl();
        this.stockQuantity = portfolio.getStockQuantity();
        this.presentPrice = presentPrice;
        this.purchasePrice = portfolio.getStockAveragePrice();
    }
}
