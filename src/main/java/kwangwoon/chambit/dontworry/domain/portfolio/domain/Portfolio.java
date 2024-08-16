package kwangwoon.chambit.dontworry.domain.portfolio.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    private Long stockAveragePrice;
    private Long stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stock stock;
}
