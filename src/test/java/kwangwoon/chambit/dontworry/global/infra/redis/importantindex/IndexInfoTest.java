package kwangwoon.chambit.dontworry.global.infra.redis.importantindex;

import com.fasterxml.jackson.core.JsonProcessingException;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.CommodityRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.IndexRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.InterestRateRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IndexInfoTest {

    @Autowired
    RateRepository rateService;

    @Autowired
    InterestRateRepository interestRateService;

    @Autowired
    CommodityRepository commodityService;

    @Autowired
    IndexRepository indexService;

    @Test
    public void getRates(){
        System.out.println(rateService.getRates());
    }

    @Test
    public void getInterestRate(){
        System.out.println(interestRateService.getInterestRates());
    }
    @Test
    public void getCommodities() throws JsonProcessingException {
        System.out.println(commodityService.getCommodities());
    }
    @Test
    public void getIndexes(){
        System.out.println(indexService.getIndexes());
    }

}