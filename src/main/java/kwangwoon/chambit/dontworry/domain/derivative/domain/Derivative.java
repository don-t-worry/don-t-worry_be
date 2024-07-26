package kwangwoon.chambit.dontworry.domain.derivative.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.derivative.enums.OptionType;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "derivativeCode" , "derivativeName" , "optionType"})
public class Derivative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "derivative_id")
    private Long id;

    String derivativeCode;
    String derivativeName;

    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stock stock;
}
