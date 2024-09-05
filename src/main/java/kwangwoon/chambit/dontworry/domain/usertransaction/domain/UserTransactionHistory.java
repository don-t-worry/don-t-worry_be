package kwangwoon.chambit.dontworry.domain.usertransaction.domain;

import jakarta.persistence.*;
import jakarta.transaction.UserTransaction;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.AccountType;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id" , "funds", "time" , "accountType","transactionType"})
public class UserTransactionHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_transaction_history_id")
    private Long id;

    private Long funds;

    @CreatedDate
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Builder
    public UserTransactionHistory(Long funds, User user, AccountType accountType, TransactionType transactionType){
        this.funds = funds;
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.time = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.user = user;
        this.accountType = accountType;
        this.transactionType = transactionType;
    }

}
