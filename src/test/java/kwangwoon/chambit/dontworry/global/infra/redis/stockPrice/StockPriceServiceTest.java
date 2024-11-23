package kwangwoon.chambit.dontworry.global.infra.redis.stockPrice;

import kwangwoon.chambit.dontworry.domain.portfolio.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StockPriceServiceTest {

    @Autowired
    PresentStockPriceService stockPriceService;

    @Test
    public void test(){


        List<Portfolio> portfolios = TestDataUtil.getPortfolios();
        List<Long> presentStocks = stockPriceService.getPresentStockPrices(portfolios);

        for(int i=0; i<presentStocks.size(); i++){
            System.out.println("portfolio : " + portfolios.get(i) + " presentPrice : " + presentStocks.get(i));
        }

    }
}