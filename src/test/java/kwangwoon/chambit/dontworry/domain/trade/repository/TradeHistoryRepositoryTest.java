package kwangwoon.chambit.dontworry.domain.trade.repository;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.repository.DerivativeRepository;
import kwangwoon.chambit.dontworry.domain.trade.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class TradeHistoryRepositoryTest {
    @Autowired
    TradeHistoryRepository tradeHistoryRepository;

    @Autowired
    DerivativeRepository derivativeRepository;


    @BeforeEach
    public void insertData(){
        List<Derivative> derivatives = TestDataUtil.getDerivative();

        List<Derivative> savedDerivatives = derivativeRepository.saveAll(derivatives);

        List<TradeHistory> tradeHistories = TestDataUtil.getTradeHistory(savedDerivatives);

        tradeHistoryRepository.saveAll(tradeHistories);
    }

    @Test
    public void test(){
        PageRequest pr = PageRequest.of(0,10, Sort.by("time").descending());
        Page<TradeHistory> byPage = tradeHistoryRepository.findByPage(pr);

        for(var s : byPage){
            System.out.println(s);
        }
    }
}