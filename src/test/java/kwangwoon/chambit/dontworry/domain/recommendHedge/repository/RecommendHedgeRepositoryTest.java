package kwangwoon.chambit.dontworry.domain.recommendHedge.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kwangwoon.chambit.dontworry.domain.recommendHedge.domain.RecommendHedge;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Transactional
class RecommendHedgeRepositoryTest {
    @Autowired
    RecommendHedgeRepository recommendHedgeRepository;

    @Test
    public void test() throws JsonProcessingException {
        Optional<RecommendHedge> byStockIdAndHedgeType = recommendHedgeRepository.findByStockIdAndHedgeType(21227L, HedgeType.MINIMAL);
        System.out.println(byStockIdAndHedgeType.get());

        List<Map<String, Object>> pairs = List.of(
                Map.of("stock_id", 20124L, "hedge_type", "MINIMAL"),
                Map.of("stock_id", 20124L, "hedge_type", "BALANCED")
        );

        String jsonPairs = new ObjectMapper().writeValueAsString(pairs);

        List<Object[]> results = recommendHedgeRepository.findByStockIdAndHedgeTypeIn(jsonPairs);

        for(var s : results){
            for(var k : s){
                System.out.print(k + " ");
            }
            System.out.println();
        }
        System.out.println(results);

    }

}