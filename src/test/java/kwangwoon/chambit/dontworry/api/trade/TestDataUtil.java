package kwangwoon.chambit.dontworry.api.trade;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.enums.OptionType;
import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHomeDto;
import kwangwoon.chambit.dontworry.domain.trade.enums.TradeType;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyViewResponse;
import kwangwoon.chambit.dontworry.domain.usertrade.domain.UserTradeHistory;
import kwangwoon.chambit.dontworry.domain.usertrade.dto.response.UserTradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataUtil {

    public static User getUser(){
        return User.builder()
                .hedgeType(HedgeType.STABLE)
                .role("ROLE_USER")
                .username("재윤")
                .name("재재윤")
                .build();
    }

    public static UserMoneyView getUserMoneyView(User user){
        return UserMoneyView
                .builder()
                .user(user)
                .accountBalance(500L)
                .activeInvestments(200L)
                .availableInvestments(300L)
                .investmentBalance(500L)
                .profit(400L)
                .profitRate(2L)
                .build();
    }

    public static UserMoneyViewResponse getUserMoneyViewResponse(UserMoneyView userMoneyView){
        return UserMoneyViewResponse.builder()
                .userMoneyView(userMoneyView)
                .build();
    }

    public static List<UserTradeHistoryResponseDto> getUserTradeHistory(User user, List<TradeHistory> tradeHistories){

        return tradeHistories.stream()
                .map(tradeHistory -> {
                    return UserTradeHistory.builder()
                            .user(user)
                            .tradeHistory(tradeHistory)
                            .time(tradeHistory.getTime())
                            .activeInvestments(tradeHistory.getActiveInvestments()/2)
                            .investmentBalance(tradeHistory.getInvestmentBalance()/2)
                            .profit(tradeHistory.getProfit()/2)
                            .build();
                })
                .map(UserTradeHistoryResponseDto::new)
                .collect(Collectors.toList());
    }


    public static UserDetails getAuthentication() {
        Oauth2ClientDto oauth2Client = Oauth2ClientDto.builder()
                .username("재윤")
                .role("ROLE_USER")
                .build();
        return new CustomOauth2ClientDto(oauth2Client);
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
                        .commission(5L)
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
                        .commission(5L)
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
                        .commission(5L)
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
                        .commission(5L)
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
                        .commission(5L)
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
                        .commission(5L)
                        .derivative(derivatives.get(5))
                        .investmentBalance(20000L)
                        .profit(2000L)
                        .build()
        );
    }


    public static List<TradeHistoryResponseDto> getTradeHistoryResponseDto(List<TradeHistory> tradeHistories){
        return tradeHistories.stream()
                .map(TradeHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    public static TradeHomeDto getTradeHomeDto(List<TradeHistoryResponseDto> tradeHistoryResponseDtos, ServiceMoneyView serviceMoneyView){
        return TradeHomeDto.builder()
                .serviceTradeHistory(tradeHistoryResponseDtos)
                .serviceMoneyView(serviceMoneyView)
                .build();
    }

    public static ServiceMoneyView getServiceMoney(){
        return ServiceMoneyView.builder()
                .activeInvestments(5000L)
                .profitRate(20L)
                .availableInvestments(15000L)
                .investmentBalance(20000L)
                .build();
    }

    public static <T> Page<T> getPage(List<T> list, Pageable pr){
        return new PageImpl<>(list,pr,list.size());
    }
}
