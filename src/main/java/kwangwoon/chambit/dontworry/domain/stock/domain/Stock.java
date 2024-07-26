package kwangwoon.chambit.dontworry.domain.stock.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "derivative_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Derivative derivative;

}
