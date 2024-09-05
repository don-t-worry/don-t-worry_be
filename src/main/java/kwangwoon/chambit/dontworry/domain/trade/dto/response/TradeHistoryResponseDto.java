package kwangwoon.chambit.dontworry.domain.trade.dto.response;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.enums.OptionType;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.enums.TradeType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TradeHistoryResponseDto {
    String derivativeName;
    OptionType optionType;
    Long tradePrice;
    Double commission;
    Double settlementAmount;
    LocalDateTime time;

    @Builder
    public TradeHistoryResponseDto(TradeHistory tradeHistory){
        this.derivativeName = tradeHistory.getDerivative().getDerivativeName();
        this.optionType = tradeHistory.getDerivative().getOptionType();
        this.tradePrice = tradeHistory.getPrice();
        this.commission = (double) tradeHistory.getCommission();
        this.settlementAmount = (tradeHistory.getPrice() * (1-(commission/100)));
        this.time = tradeHistory.getTime();
    }

}
