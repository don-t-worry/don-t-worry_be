package kwangwoon.chambit.dontworry.domain.trade.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TradeHomeDto {
    Long serviceTotalBalance;
    Double profitRate;

    @Builder
    public TradeHomeDto(ServiceMoneyView serviceMoneyView){
        this.serviceTotalBalance = serviceMoneyView.getInvestmentBalance();
        this.profitRate = serviceMoneyView.getProfitRate();
    }
}
