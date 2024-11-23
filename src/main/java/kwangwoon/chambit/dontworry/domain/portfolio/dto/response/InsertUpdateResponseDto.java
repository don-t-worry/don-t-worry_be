package kwangwoon.chambit.dontworry.domain.portfolio.dto.response;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import lombok.Builder;
import lombok.Data;

@Data
public class InsertUpdateResponseDto {
    private Long stockId;
    private String stockName;
    private Long stockQuantity;
    private String stockImage;
    private Long presentPrice;
    private Long purchasePrice;
    private Long profit;
    private Float profitRate;

    @Builder
    public InsertUpdateResponseDto(Stock stock, Long stockQuantity, Long presentPrice, Long purchasePrice){
        this.stockId = stock.getId();
        this.stockName = stock.getStockName();
        this.stockQuantity = stockQuantity;
        this.stockImage = stock.getImageUrl();
        this.presentPrice = presentPrice * stockQuantity;
        this.purchasePrice = purchasePrice * stockQuantity;
        this.profit = this.presentPrice - this.purchasePrice;
        this.profitRate = (float) this.profit / this.purchasePrice * 100;
    }
}
