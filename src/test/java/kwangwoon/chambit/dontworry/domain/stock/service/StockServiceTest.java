package kwangwoon.chambit.dontworry.domain.stock.service;

import kwangwoon.chambit.dontworry.domain.stock.dto.response.StockSearchDto;
import kwangwoon.chambit.dontworry.global.common.dto.PageResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StockServiceTest {
    @Autowired
    StockService stockService;

    @Test
    public void 주식검색(){

        PageRequest pr = PageRequest.of(0,10, Sort.by("stockName"));

        PageResponseDto<StockSearchDto> list = stockService.getSearchStock("삼성", pr);
        System.out.println(list);
        Assertions.assertThat(list.getPageInfo().getSize()).isEqualTo(12);
    }
}