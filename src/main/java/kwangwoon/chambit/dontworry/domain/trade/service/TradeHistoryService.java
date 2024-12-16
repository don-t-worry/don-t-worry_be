package kwangwoon.chambit.dontworry.domain.trade.service;

import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.domain.servicemoney.repository.ServiceMoneyViewRepository;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHomeDto;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.trade.repository.TradeHistoryRepository;
import kwangwoon.chambit.dontworry.global.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeHistoryService {
    private final TradeHistoryRepository tradeHistoryRepository;
    private final ServiceMoneyViewRepository serviceMoneyViewRepository;

    public PageResponseDto<TradeHistoryResponseDto> getAllTradeHistory(Pageable pageable){
        return new PageResponseDto<>(tradeHistoryRepository.findByPage(pageable)
                .map(TradeHistoryResponseDto::new));
    }

    public TradeHomeDto getTradeHomeDto(){
        ServiceMoneyView serviceMoney = serviceMoneyViewRepository.findServiceMoney();

        return TradeHomeDto.builder().serviceMoneyView(serviceMoney).build();
    }

}
