package kwangwoon.chambit.dontworry.domain.usertransaction.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.AccountType;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "funds"})
public class UserTransactionHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_transaction_history_id")
    private Long id;

    private Long funds;
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

}
