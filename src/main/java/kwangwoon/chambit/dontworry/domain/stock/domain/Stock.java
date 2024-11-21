package kwangwoon.chambit.dontworry.domain.stock.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "stockCode" , "stockName" , "volatility"})
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    private String stockCode;
    private String stockName;
    private BigDecimal volatility;
    private String imageUrl;

    @Builder
    public Stock(String stockCode, String stockName, BigDecimal volatility, String imageUrl){
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.volatility = volatility;
        this.imageUrl = imageUrl;
    }

}
