package kwangwoon.chambit.dontworry.domain.servicemoney.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "service_money_view")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"investmentBalance", "availableInvestments" , "activeInvestments", "profitRate"})
public class ServiceMoneyView {
    @Id @Column(name = "id")
    private Long id;

    @Column(name = "investment_balance")
    private Long investmentBalance;

    @Column(name = "available_investments")
    private Long availableInvestments;

    @Column(name = "active_investments")
    private Long activeInvestments;

    @Column(name = "profit_rate")
    private Double profitRate;


    @Builder
    public ServiceMoneyView(Long investmentBalance, Long availableInvestments, Long activeInvestments, Double profitRate){
        this.investmentBalance = investmentBalance;
        this.availableInvestments = availableInvestments;
        this.activeInvestments = activeInvestments;
        this.profitRate = profitRate;
    }

}
