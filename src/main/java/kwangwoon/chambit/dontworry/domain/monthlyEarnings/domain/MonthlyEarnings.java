package kwangwoon.chambit.dontworry.domain.monthlyEarnings.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MonthlyEarnings {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Monthly_earnings_id")
    Long id;
    String monthDate;
    Double earningsRate;
    Long investmentMoney;
    Long earnings;
}
