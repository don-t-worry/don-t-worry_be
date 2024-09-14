package kwangwoon.chambit.dontworry.domain.trade;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.enums.OptionType;
import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.enums.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class TestDataUtil {

    public static <T> Page<T> getPage(List<T> list, Pageable pr){
        return new PageImpl(list, pr, list.size());
    }

    public static ServiceMoneyView getServiceMoney(){
        return ServiceMoneyView.builder()
                .activeInvestments(5000L)
                .profitRate(20D)
                .availableInvestments(15000L)
                .investmentBalance(20000L)
                .build();
    }

    public static List<Derivative> getDerivative(){
        return List.of(
                Derivative.builder()
                        .derivativeName("삼성 옵션")
                        .derivativeCode("삼성 옵션 코드")
                        .optionType(OptionType.PUT)
                        .build(),
                Derivative.builder()
                        .derivativeName("엘지 옵션")
                        .derivativeCode("엘지 옵션 코드")
                        .optionType(OptionType.CALL)
                        .build(),
                Derivative.builder()
                        .derivativeName("sk 옵션")
                        .derivativeCode("sk 옵션 코드")
                        .optionType(OptionType.PUT)
                        .build(),
                Derivative.builder()
                        .derivativeName("apple 옵션")
                        .derivativeCode("apple 옵션 코드")
                        .optionType(OptionType.CALL)
                        .build(),
                Derivative.builder()
                        .derivativeName("한투증 옵션")
                        .derivativeCode("한투증 옵션 코드")
                        .optionType(OptionType.CALL)
                        .build(),
                Derivative.builder()
                        .derivativeName("미래에셋 옵션")
                        .derivativeCode("미래에셋 옵션 코드")
                        .optionType(OptionType.PUT)
                        .build()
        );
    }

    public static List<TradeHistory> getTradeHistory(List<Derivative> derivatives){

        return List.of(
                TradeHistory.builder()
                        .price(200L)
                        .count(20L)
                        .time(LocalDateTime.of(2024,9,4,12,12))
                        .tradeType(TradeType.BUY)
                        .activeInvestments(4000L)
                        .commission(5D)
                        .derivative(derivatives.get(0))
                        .investmentBalance(20000L)
                        .profit(2000L)
                        .build(),
                TradeHistory.builder()
                        .price(300L)
                        .count(30L)
                        .time(LocalDateTime.of(2024,9,4,12,13))
                        .tradeType(TradeType.SELL)
                        .activeInvestments(9000L)
                        .commission(5D)
                        .derivative(derivatives.get(1))
                        .investmentBalance(20000L)
                        .profit(3000L)
                        .build(),
                TradeHistory.builder()
                        .price(400L)
                        .count(40L)
                        .time(LocalDateTime.of(2024,9,4,12,14))
                        .tradeType(TradeType.BUY)
                        .activeInvestments(16000L)
                        .commission(5D)
                        .derivative(derivatives.get(2))
                        .investmentBalance(20000L)
                        .profit(2000L)
                        .build(),
                TradeHistory.builder()
                        .price(400L)
                        .count(20L)
                        .time(LocalDateTime.of(2024,9,4,12,15))
                        .tradeType(TradeType.SELL)
                        .activeInvestments(8000L)
                        .commission(5D)
                        .derivative(derivatives.get(3))
                        .investmentBalance(20000L)
                        .profit(2000L)
                        .build(),
                TradeHistory.builder()
                        .price(500L)
                        .count(20L)
                        .time(LocalDateTime.of(2024,9,4,12,16))
                        .tradeType(TradeType.BUY)
                        .activeInvestments(10000L)
                        .commission(5D)
                        .derivative(derivatives.get(4))
                        .investmentBalance(20000L)
                        .profit(2000L)
                        .build(),
                TradeHistory.builder()
                        .price(300L)
                        .count(20L)
                        .time(LocalDateTime.of(2024,9,4,12,17))
                        .tradeType(TradeType.SELL)
                        .activeInvestments(6000L)
                        .commission(5D)
                        .derivative(derivatives.get(5))
                        .investmentBalance(20000L)
                        .profit(2000L)
                        .build()
                );
    }
}
