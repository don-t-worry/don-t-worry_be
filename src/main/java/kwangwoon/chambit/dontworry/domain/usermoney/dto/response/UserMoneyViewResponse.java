package kwangwoon.chambit.dontworry.domain.usermoney.dto.response;

import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import lombok.Builder;
import lombok.Data;

@Data
public class UserMoneyViewResponse {
    Long totalAmount;
    Long totalInvestmentAmount;
    Long totalAccountAmount;

    @Builder
    public UserMoneyViewResponse(UserMoneyView userMoneyView){
        this.totalAmount = userMoneyView.getAccountBalance() + userMoneyView.getInvestmentBalance();
        this.totalInvestmentAmount = userMoneyView.getInvestmentBalance();
        this.totalAccountAmount = userMoneyView.getAccountBalance();
    }
}
