package kwangwoon.chambit.dontworry.domain.usermoney.dto.response;

import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import lombok.Data;

@Data
public class UserMoneyInvestDepositResponse {
    private Long investDepositAmount;

    public UserMoneyInvestDepositResponse(UserMoneyView userMoneyView){
        this.investDepositAmount = userMoneyView.getAvailableInvestments();
    }
}
