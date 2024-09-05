package kwangwoon.chambit.dontworry.domain.usertrade.service;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.repository.DerivativeRepository;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.repository.TradeHistoryRepository;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.domain.usertrade.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.usertrade.domain.UserTradeHistory;
import kwangwoon.chambit.dontworry.domain.usertrade.dto.response.UserTradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.usertrade.repository.UserTradeHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kwangwoon.chambit.dontworry.domain.usertrade.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTradeHistoryServiceTest {
    @MockBean
    UserTradeHistoryRepository userTradeHistoryRepository;

    @Autowired
    UserTradeHistoryService userTradeHistoryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DerivativeRepository derivativeRepository;

    @Autowired
    TradeHistoryRepository tradeHistoryRepository;

    @Test
    public void getUserTradeHistory_when_thenSuccessDto(){

        PageRequest pr = PageRequest.of(0,10, Sort.by("time"));

        User user = getUser();
        List<Derivative> derivative = getDerivative();

        User saveUser = userRepository.save(user);
        List<Derivative> derivatives = derivativeRepository.saveAll(derivative);

        List<TradeHistory> tradeHistory = getTradeHistory(derivatives);
        List<TradeHistory> tradeHistories = tradeHistoryRepository.saveAll(tradeHistory);

        List<UserTradeHistory> userTradeHistory = getUserTradeHistory(saveUser, tradeHistories);

        Page<UserTradeHistory> given = getPage(userTradeHistory, pr);




        Mockito.when(userTradeHistoryRepository.findByUsername(Mockito.any(),Mockito.any())).thenReturn(given);

        Page<UserTradeHistoryResponseDto> then = userTradeHistoryService.getUserTradeHistory(pr,getAuthentication());

        Mockito.verify(userTradeHistoryRepository).findByUsername(Mockito.any(),Mockito.any());

        System.out.println("given");
        for (UserTradeHistoryResponseDto userTradeHistoryResponseDto : given.map(UserTradeHistoryResponseDto::new)) {
            System.out.println(userTradeHistoryResponseDto);
        }

        System.out.println("then");
        for (UserTradeHistoryResponseDto userTradeHistoryResponseDto : then) {
            System.out.println(userTradeHistoryResponseDto);
        }


        Assertions.assertThat(then).isEqualTo(given.map(UserTradeHistoryResponseDto::new));

    }
}