package kwangwoon.chambit.dontworry.global.common.request;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StockPriceServiceTest {

    @Autowired
    StockPriceService stockPriceService;

    @Test
    public void getRealTimePrice(){
        Stock stock1 = Stock.builder()
                .stockCode("005930")
                .stockName("삼성")
                .imageUrl("ㄴㅇㄹㄴㅇ")
                .build();
        Stock stock2 = Stock.builder()
                .stockCode("000660")
                .stockName("SK하이닉스")
                .imageUrl("ㄴㅇㄹㄴㅇ")
                .build();


        List<Portfolio> chunk = List.of(
                Portfolio.builder()
                        .stockAveragePrice(2L)
                        .stockQuantity(2L)
                        .stock(stock1)
                        .build(),
                Portfolio.builder()
                        .stockAveragePrice(3L)
                        .stockQuantity(3L)
                        .stock(stock2)
                        .build()
        );

        List<Long> block = stockPriceService.getPresentStockPrice(chunk).collectList().block();

        System.out.println(block);
    }

}