package kwangwoon.chambit.dontworry.domain.usermoney.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "user_money_view")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"accountBalance", "investmentBalance" , "availableInvestments", "activeInvestments" ,"profitRate", "profit"})
public class UserMoneyView {

    @Id @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "account_balance")
    private Long accountBalance;

    @Column(name = "investment_balance")
    private Long investmentBalance;

    @Column(name = "available_investments")
    private Long availableInvestments;

    @Column(name = "active_investments")
    private Long activeInvestments;

    @Column(name = "profit_rate")
    private Long profitRate;

    @Column(name = "profit")
    private Long profit;

    @Builder
    public UserMoneyView(User user, Long accountBalance, Long investmentBalance, Long availableInvestments, Long activeInvestments, Long profitRate , Long profit){
        this.id = user.getId();
        this.user = user;
        this.accountBalance = accountBalance;
        this.investmentBalance = investmentBalance;
        this.availableInvestments = availableInvestments;
        this.activeInvestments = activeInvestments;
        this.profitRate = profitRate;
        this.profit = profit;
    }

}
