package kwangwoon.chambit.dontworry.domain.usertrade.dto.response;

import kwangwoon.chambit.dontworry.domain.derivative.enums.OptionType;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.usertrade.domain.UserTradeHistory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTradeHistoryResponseDto {
    String derivativeName;
    OptionType optionType;
    Long tradePrice;
    Double commission;
    Double settlementAmount;
    LocalDateTime time;

    @Builder
    public UserTradeHistoryResponseDto(UserTradeHistory userTradeHistory){
        TradeHistory tradeHistory = userTradeHistory.getTradeHistory();
        this.derivativeName = tradeHistory.getDerivative().getDerivativeName();
        this.optionType = tradeHistory.getDerivative().getOptionType();
        this.tradePrice = userTradeHistory.getActiveInvestments();
        this.commission = (double)tradeHistory.getCommission();
        this.settlementAmount =  (userTradeHistory.getActiveInvestments() * (1 - (commission / 100)));
        this.time = tradeHistory.getTime();
    }
}
