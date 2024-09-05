package kwangwoon.chambit.dontworry.domain.usertransaction.dto.request;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.usertransaction.domain.UserTransactionHistory;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.AccountType;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.TransactionType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTransactionInsertDto {
    private Long funds;
    private AccountType accountType;
    private TransactionType transactionType;

    @Builder
    public UserTransactionInsertDto(Long funds, AccountType accountType, TransactionType transactionType){
        this.funds = funds;
        this.accountType = accountType;
        this.transactionType = transactionType;
    }

    public UserTransactionHistory toEntity(User user){
        return UserTransactionHistory.builder()
                .user(user)
                .accountType(accountType)
                .transactionType(transactionType)
                .funds(funds)
                .build();
    }
}
