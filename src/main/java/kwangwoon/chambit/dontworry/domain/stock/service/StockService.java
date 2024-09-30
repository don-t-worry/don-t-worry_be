package kwangwoon.chambit.dontworry.domain.stock.service;

import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.dto.response.StockSearchDto;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import kwangwoon.chambit.dontworry.global.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {
    private final StockRepository stockRepository;

    public PageResponseDto<StockSearchDto> getSearchStock(String searchKeyword, Pageable pageable){
        return new PageResponseDto<>(stockRepository.findBySearchKeyword(searchKeyword, pageable)
                .map(StockSearchDto::new));

    }
}
