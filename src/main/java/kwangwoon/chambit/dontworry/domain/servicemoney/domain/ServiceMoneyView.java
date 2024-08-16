package kwangwoon.chambit.dontworry.domain.servicemoney.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "service_money_view")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"investmentBalance", "availableInvestments" , "activeInvestments"})
public class ServiceMoneyView {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "investment_balance")
    private Long investmentBalance;

    @Column(name = "available_investments")
    private Long availableInvestments;

    @Column(name = "active_investments")
    private Long activeInvestments;

    @Column(name = "profit_rate")
    private Long profitRate;

}
