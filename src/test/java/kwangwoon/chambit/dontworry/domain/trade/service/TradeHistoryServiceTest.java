package kwangwoon.chambit.dontworry.domain.trade.service;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.repository.DerivativeRepository;
import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.domain.servicemoney.repository.ServiceMoneyViewRepository;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHomeDto;
import kwangwoon.chambit.dontworry.domain.trade.repository.TradeHistoryRepository;
import kwangwoon.chambit.dontworry.global.common.dto.PageResponseDto;
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
import java.util.stream.Collectors;

import static kwangwoon.chambit.dontworry.domain.trade.TestDataUtil.*;

@SpringBootTest
@Transactional
class TradeHistoryServiceTest {

    @Autowired
    private TradeHistoryService tradeHistoryService;

    @MockBean
    private TradeHistoryRepository tradeHistoryRepository;

    @MockBean
    private ServiceMoneyViewRepository serviceMoneyViewRepository;

    @Autowired
    private DerivativeRepository derivativeRepository;

    @Test
    public void getAllTradeHistory_when_thenSuccessfulDto(){
        PageRequest pr = PageRequest.of(0,10, Sort.by("time").descending());

        List<Derivative> derivatives = derivativeRepository.saveAll(getDerivative());
        List<TradeHistory> tradeHistories = getTradeHistory(derivatives);
        Page<TradeHistory> result = getPage(tradeHistories,pr);

        Mockito.when(tradeHistoryRepository.findByPage(pr)).thenReturn(result);

        PageResponseDto<TradeHistoryResponseDto> allTradeHistory = tradeHistoryService.getAllTradeHistory(pr);

        Mockito.verify(tradeHistoryRepository).findByPage(pr);

        Assertions.assertThat(allTradeHistory).isEqualTo(result.map(TradeHistoryResponseDto::new));
    }

    @Test
    public void getTradeHomeDto_when_thenSuccessfulDto(){
        PageRequest pr = PageRequest.of(0,3, Sort.by("time").descending());

        List<Derivative> derivatives = derivativeRepository.saveAll(getDerivative());
        List<TradeHistory> tradeHistories = getTradeHistory(derivatives);
        Page<TradeHistory> result = getPage(tradeHistories,pr);

        ServiceMoneyView serviceMoneyView = getServiceMoney();

        Mockito.when(tradeHistoryRepository.findByPage(pr)).thenReturn(result);
        Mockito.when(serviceMoneyViewRepository.findServiceMoney()).thenReturn(serviceMoneyView);

        TradeHomeDto tradeHomeDto = tradeHistoryService.getTradeHomeDto();

        Mockito.verify(tradeHistoryRepository).findByPage(pr);
        Mockito.verify(serviceMoneyViewRepository).findServiceMoney();

        TradeHomeDto build = TradeHomeDto.builder().serviceMoneyView(serviceMoneyView).build();

        Assertions.assertThat(build).isEqualTo(tradeHomeDto);

        System.out.println(tradeHomeDto);
    }
}