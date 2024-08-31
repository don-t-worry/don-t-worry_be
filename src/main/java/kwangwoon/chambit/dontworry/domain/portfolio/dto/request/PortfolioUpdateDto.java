package kwangwoon.chambit.dontworry.domain.portfolio.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortfolioUpdateDto {
    private Long stockQuantity;
    private Long purchasePrice;

    @Builder
    public PortfolioUpdateDto(Long stockQuantity, Long purchasePrice){
        this.stockQuantity = stockQuantity;
        this.purchasePrice = purchasePrice;
    }
}
