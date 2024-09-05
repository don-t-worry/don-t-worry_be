package kwangwoon.chambit.dontworry.domain.trade.service;

import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.domain.servicemoney.repository.ServiceMoneyViewRepository;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHomeDto;
import kwangwoon.chambit.dontworry.domain.trade.dto.response.TradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.trade.repository.TradeHistoryRepository;
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

    public Page<TradeHistoryResponseDto> getAllTradeHistory(Pageable pageable){
        return tradeHistoryRepository.findByPage(pageable)
                .map(TradeHistoryResponseDto::new);
    }

    public TradeHomeDto getTradeHomeDto(){
        PageRequest pr = PageRequest.of(0,3, Sort.by("time").descending());
        List<TradeHistoryResponseDto> tradeHistories = tradeHistoryRepository.findByPage(pr)
                .stream()
                .map(TradeHistoryResponseDto::new)
                .collect(Collectors.toList());
        ServiceMoneyView serviceMoney = serviceMoneyViewRepository.findServiceMoney();

        return TradeHomeDto.builder().serviceMoneyView(serviceMoney).serviceTradeHistory(tradeHistories).build();
    }

}
