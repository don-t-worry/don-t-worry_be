package kwangwoon.chambit.dontworry.domain.usertrade.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "time" , "investmentBalance" , "activeInvestments" , "profit"})
public class UserTradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_trade_history_id")
    private Long id;

    private LocalDateTime time;
    private Long investmentBalance;
    private Long activeInvestments;
    private Long profit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_history_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private TradeHistory tradeHistory;

    @Builder
    public UserTradeHistory(LocalDateTime time, Long investmentBalance, Long activeInvestments, Long profit, User user, TradeHistory tradeHistory){
        this.time = time;
        this.investmentBalance = investmentBalance;
        this.activeInvestments = activeInvestments;
        this.profit = profit;
        this.user= user;
        this.tradeHistory = tradeHistory;
    }
}
