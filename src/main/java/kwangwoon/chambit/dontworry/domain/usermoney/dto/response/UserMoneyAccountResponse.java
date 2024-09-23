package kwangwoon.chambit.dontworry.domain.usermoney.dto.response;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import lombok.Data;

@Data
public class UserMoneyAccountResponse {
    private Long totalAccountAmount;

    public UserMoneyAccountResponse(UserMoneyView userMoneyView){
        this.totalAccountAmount = userMoneyView.getAccountBalance();
    }
}
