package kwangwoon.chambit.dontworry.domain.portfolio.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "stockAveragePrice" , "stockQuantity"})
public class Portfolio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @Setter
    private Long stockAveragePrice;
    @Setter
    private Long stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "derivative_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Derivative derivative;

    private Long derivativeQuantity;
    private Long riskReductionRate;

    @Builder
    public Portfolio(Long stockQuantity, Long stockAveragePrice, User user, Stock stock, Derivative derivative, Long derivativeQuantity, Long riskReductionRate){
        this.stockQuantity = stockQuantity;
        this.stockAveragePrice = stockAveragePrice;
        this.user = user;
        this.stock = stock;

        this.derivative = derivative;
        this.derivativeQuantity = derivativeQuantity;
        this.riskReductionRate = riskReductionRate;
    }
}
