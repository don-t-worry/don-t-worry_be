package kwangwoon.chambit.dontworry.domain.stock.service;

import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.dto.response.StockSearchDto;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {
    private final StockRepository stockRepository;

    public List<StockSearchDto> getSearchStock(String searchKeyword){
        return stockRepository.findBySearchKeyword(searchKeyword)
                .stream()
                .map(StockSearchDto::new)
                .collect(Collectors.toList());
    }
}
