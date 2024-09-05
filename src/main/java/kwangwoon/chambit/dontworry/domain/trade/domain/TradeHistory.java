package kwangwoon.chambit.dontworry.domain.trade.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.trade.enums.TradeType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "commission" , "time" , "investmentBalance" , "activeInvestments" , "profit" , "price" , "tradeType"})
public class TradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_history_id")
    private Long id;

    private Long commission;
    private LocalDateTime time;
    private Long investmentBalance;
    private Long activeInvestments;
    private Long profit;
    private Long price;
    private Long count;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "derivative_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Derivative derivative;

    @Builder
    public TradeHistory(Long commission, LocalDateTime time, Long investmentBalance, Long activeInvestments, Long profit, Long price, Long count, TradeType tradeType, Derivative derivative){
        this.commission = commission;
        this.time = time;
        this.investmentBalance = investmentBalance;
        this.activeInvestments = activeInvestments;
        this.profit = profit;
        this.price = price;
        this.count = count;
        this.tradeType = tradeType;
        this.derivative = derivative;
    }

}
