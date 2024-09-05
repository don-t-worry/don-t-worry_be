package kwangwoon.chambit.dontworry.domain.trade.dto.response;

import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TradeHomeDto {
    Long serviceTotalBalance;
    Long profitRate;
    List<TradeHistoryResponseDto> serviceTradeHistory;

    @Builder
    public TradeHomeDto(ServiceMoneyView serviceMoneyView, List<TradeHistoryResponseDto> serviceTradeHistory){
        this.serviceTotalBalance = serviceMoneyView.getInvestmentBalance();
        this.profitRate = serviceMoneyView.getProfitRate();
        this.serviceTradeHistory = serviceTradeHistory;
    }
}
