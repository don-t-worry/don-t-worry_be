package kwangwoon.chambit.dontworry.api.trade.api;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.domain.trade.api.TradeHistoryController;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHomeDto;
import kwangwoon.chambit.dontworry.domain.trade.service.TradeHistoryService;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.usermoney.api.UserMoneyHomePageController;
import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyViewResponse;
import kwangwoon.chambit.dontworry.domain.usermoney.service.UserMoneyViewService;
import kwangwoon.chambit.dontworry.domain.usertrade.api.UserTradeHistoryController;
import kwangwoon.chambit.dontworry.domain.usertrade.dto.response.UserTradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.usertrade.service.UserTradeHistoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kwangwoon.chambit.dontworry.api.trade.TestDataUtil.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class TradeApiTest {
    @Autowired
    UserMoneyHomePageController userMoneyViewController;

    @MockBean
    UserMoneyViewService userMoneyViewService;

    @Autowired
    UserTradeHistoryController userTradeHistoryController;

    @MockBean
    UserTradeHistoryService userTradeHistoryService;

    @Autowired
    TradeHistoryController tradeHistoryController;

    @MockBean
    TradeHistoryService tradeHistoryService;


    @Test
    public void getUserTradeInvest_when_thenSuccessDto(){

        User user = getUser();
        UserMoneyView userMoneyView = getUserMoneyView(user);




        when(userMoneyViewService.getUserTradeInvest(any())).thenReturn(new UserMoneyViewResponse(userMoneyView));

        ResponseEntity<?> userTradeInvest = userMoneyViewController.getUserTradeInvest(getAuthentication());

        verify(userMoneyViewService).getUserTradeInvest(any());

        Assertions.assertThat(userTradeInvest).isEqualTo(ResponseEntity.ok(new UserMoneyViewResponse(userMoneyView)));
    }

    @Test
    public void getUserTradeHistory_when_thenSuccessDto(){

        PageRequest pr = PageRequest.of(0,10, Sort.by("time").descending());

        List<Derivative> derivative = getDerivative();

        List<TradeHistory> tradeHistory = getTradeHistory(derivative);

        User user = getUser();

        Page<UserTradeHistoryResponseDto> page = getPage(getUserTradeHistory(user, tradeHistory), pr);




        when(userTradeHistoryService.getUserTradeHistory(any(),any())).thenReturn(page);

        ResponseEntity<?> userTradeHistory = userTradeHistoryController.getUserTradeHistory(0,getAuthentication());

        verify(userTradeHistoryService).getUserTradeHistory(any(),any());





        Assertions.assertThat(userTradeHistory).isEqualTo(ResponseEntity.ok(page));

    }

    @Test
    public void getAllTradeHistory_when_thenSuccessDto(){
        PageRequest pr = PageRequest.of(0,10, Sort.by("time").descending());
        List<Derivative> derivative = getDerivative();
        List<TradeHistory> tradeHistory = getTradeHistory(derivative);
        Page<TradeHistoryResponseDto> page = getPage(getTradeHistoryResponseDto(tradeHistory), pr);

        when(tradeHistoryService.getAllTradeHistory(any())).thenReturn(page);

        ResponseEntity<?> allTradeHistory = tradeHistoryController.getAllTradeHistory(0);

        verify(tradeHistoryService).getAllTradeHistory(any());



        Assertions.assertThat(allTradeHistory).isEqualTo(ResponseEntity.ok(page));

    }

    @Test
    public void getTradeHomeDto_when_thenSuccessDto(){

        List<Derivative> derivative = getDerivative();
        List<TradeHistory> tradeHistory = getTradeHistory(derivative);
        List<TradeHistoryResponseDto> tradeHistoryResponseDto = getTradeHistoryResponseDto(tradeHistory);
        ServiceMoneyView serviceMoney = getServiceMoney();

        TradeHomeDto tradeHomeDto1 = getTradeHomeDto(tradeHistoryResponseDto, serviceMoney);

        when(tradeHistoryService.getTradeHomeDto()).thenReturn(tradeHomeDto1);

        ResponseEntity<?> tradeHomeDto = tradeHistoryController.getTradeHomeDto();

        verify(tradeHistoryService).getTradeHomeDto();



        Assertions.assertThat(tradeHomeDto).isEqualTo(ResponseEntity.ok(tradeHomeDto1));

    }

}
