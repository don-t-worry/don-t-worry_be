package kwangwoon.chambit.dontworry.domain.stock.dto.response;

import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import lombok.Builder;
import lombok.Data;

@Data
public class StockSearchDto {
    private Long stockId;
    private String stockName;
    private String imageUrl;

    @Builder
    public StockSearchDto(Stock stock){
        this.stockId = stock.getId();
        this.stockName = stock.getStockName();
        this.imageUrl = stock.getImageUrl();
    }
}
