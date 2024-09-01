package kwangwoon.chambit.dontworry.domain.stock.service;

import kwangwoon.chambit.dontworry.domain.stock.dto.response.StockSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        List<StockSearchDto> list = stockService.getSearchStock("삼성");
        System.out.println(list);
        System.out.println(list.size());
    }
}