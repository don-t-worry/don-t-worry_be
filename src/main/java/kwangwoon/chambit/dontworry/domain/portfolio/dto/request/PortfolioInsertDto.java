package kwangwoon.chambit.dontworry.domain.portfolio.dto.request;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortfolioInsertDto {
    private Long stockId;
    private Long stockAveragePrice;
    private Long stockQuantity;

    @Builder
    public PortfolioInsertDto(Long stockId, Long stockAveragePrice, Long stockQuantity){
        this.stockId = stockId;
        this.stockAveragePrice = stockAveragePrice;
        this.stockQuantity = stockQuantity;
    }

    public Portfolio toPortfolio(User user, Stock stock){
        return Portfolio.builder()
                .stockAveragePrice(stockAveragePrice)
                .stockQuantity(stockQuantity)
                .user(user)
                .stock(stock)
                .build();
    }
}
