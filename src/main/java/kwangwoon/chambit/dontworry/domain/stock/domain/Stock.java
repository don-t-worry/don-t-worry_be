package kwangwoon.chambit.dontworry.domain.stock.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private Long volatility;
    private String imageUrl;

    @Builder
    public Stock(String stockCode, String stockName, Long volatility, String imageUrl){
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.volatility = volatility;
        this.imageUrl = imageUrl;
    }

}
