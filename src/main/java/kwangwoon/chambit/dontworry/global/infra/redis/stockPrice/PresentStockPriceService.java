package kwangwoon.chambit.dontworry.global.infra.redis.stockPrice;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresentStockPriceService {
    private final StringRedisTemplate template;

    public List<Long> getPresentStockPrices(List<Portfolio> portfolios){
        List<String> stockCodes = portfolios.stream()
                .map(x -> {
                    Stock stock = x.getStock();
                    return stock.getStockCode();
                })
                .collect(Collectors.toList());

        return template.opsForValue().multiGet(stockCodes).stream()
                .map(price -> price == null ? -1L : Long.parseLong(price))
                .collect(Collectors.toList());
    }

    public Long getPresentStockPrice(String stockCode){
        String price = template.opsForValue().get(stockCode);
        if(price == null){
            return -1L;
        }else{
            return Long.parseLong(price);
        }
    }
}
