package kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PortfolioPieDto {
    private String stockName;
    private Float rate;

    @Builder
    public PortfolioPieDto(String stockName, Float rate){
        this.stockName = stockName;
        this.rate = rate;
    }
}
