package kwangwoon.chambit.dontworry.domain.recommendHedge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.portfolio.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.portfolio.repository.PortfolioRepository;
import kwangwoon.chambit.dontworry.domain.recommendHedge.repository.RecommendHedgeRepository;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecommendHedgeServiceTest {

    @Autowired
    RecommendHedgeService recommendHedgeService;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    RecommendHedgeRepository recommendHedgeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockRepository stockRepository;


    @BeforeEach
    public void insert(){

        User user = userRepository.findByUsername("juhoho").get();

        Stock stock1 = stockRepository.findByStockId(21227L).get();
        Stock stock2 = stockRepository.findByStockId(21439L).get();
        Stock stock3 = stockRepository.findByStockId(20902L).get();

        List<Portfolio> portfolios = List.of(
                Portfolio.builder()
                        .user(user)
                        .stock(stock1)
                        .stockQuantity(20L)
                        .stockAveragePrice(3000L)
                        .build(),
                Portfolio.builder()
                        .user(user)
                        .stock(stock2)
                        .stockQuantity(30L)
                        .stockAveragePrice(4000L)
                        .build(),
                Portfolio.builder()
                        .user(user)
                        .stock(stock3)
                        .stockQuantity(40L)
                        .stockAveragePrice(5000L)
                        .build()
        );

        portfolioRepository.saveAll(portfolios);
    }

    @Test
    public void test() throws JsonProcessingException {

        List<Portfolio> portfolios = portfolioRepository.findByStockAllPrices("juhoho");
        for (PortfolioRecommendDerivativeDto dto : recommendHedgeService.getAllRecommendDerivative(portfolios)) {
            System.out.println(dto);
        }

    }
}