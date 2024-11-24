package kwangwoon.chambit.dontworry.domain.recommendHedge.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "hedgeType"  , "riskReductionRate"})
public class RecommendHedge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_hedge_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private HedgeType hedgeType;
    private Long riskReductionRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stock stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "derivative_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Derivative derivative;
}
