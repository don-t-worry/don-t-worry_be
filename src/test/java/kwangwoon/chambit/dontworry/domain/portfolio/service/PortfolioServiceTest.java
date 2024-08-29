package kwangwoon.chambit.dontworry.domain.portfolio.service;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.repository.DerivativeRepository;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioInsertDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioUpdateDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.HedgeHomeResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.PortfolioEditResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.PortfolioManageResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.portfolio.repository.PortfolioRepository;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import kwangwoon.chambit.dontworry.domain.stock.service.StockService;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.domain.user.service.UserService;
import kwangwoon.chambit.dontworry.global.common.request.StockPriceService;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PortfolioServiceTest {
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DerivativeRepository derivativeRepository;


    @Autowired
    private StockPriceService stockPriceService;

    @BeforeEach
    public void insertData(){

        User user = userRepository.findById(1L).get();
        List<Object[]> stockHavingDerivative = derivativeRepository.findStockHavingDerivative();

        List<Portfolio> collect = IntStream.range(0, 30)
                .mapToObj(i -> {
                    Object[] objects = stockHavingDerivative.get(i);
                    Stock stock = (Stock) objects[0];
                    Derivative derivative = (Derivative) objects[1];

                    return Portfolio.builder()
                            .user(user)
                            .derivative(derivative)
                            .stock(stock)
                            .riskReductionRate((long) (i))
                            .derivativeQuantity((long) i * 20)
                            .stockQuantity((long) i * 10 + i)
                            .stockAveragePrice((long) i * 1000)
                            .build();
                })
                .collect(Collectors.toList());

        portfolioRepository.saveAll(collect);
    }
    private static Authentication getAuthentication() {
        Oauth2ClientDto oauth2ClientDto = Oauth2ClientDto.builder()
                .role("투자자")
                .username("jaeyounnn")
                .isExist(true)
                .build();

        CustomOauth2ClientDto customOauth2ClientDto = new CustomOauth2ClientDto(oauth2ClientDto);
        Authentication auth = new UsernamePasswordAuthenticationToken(customOauth2ClientDto,null,customOauth2ClientDto.getAuthorities());
        return auth;
    }

    @Test
    public void insertPortfolioTest(){

        Authentication auth = getAuthentication();

        PortfolioInsertDto dto = new PortfolioInsertDto(20000L,4000L,20L);

        long countBefore = portfolioRepository.count();
        portfolioService.insertPortfolio(dto,auth);
        long countAfter = portfolioRepository.count();

        Assertions.assertThat(countBefore+1).isEqualTo(countAfter);
    }



    @Test
    public void getPortfolioEditTest(){
        Authentication authentication = getAuthentication();
        PageRequest pageRequest = PageRequest.of(0,2, Sort.by("stockQuantity"));
        Page<PortfolioRecommendDerivativeDto> dtos = portfolioService.getAllPortfolioRecommendDerivative(pageRequest, authentication);

        Assertions.assertThat(dtos.stream().count()).isEqualTo(2);
    }

    @Test
    public void updatePortfolioTest(){
        List<Portfolio> all = portfolioRepository.findAll();
        long id = all.get(1).getId();

        Portfolio portfolioBefore = portfolioRepository.findById(id).get();

        Long priceBefore = portfolioBefore.getStockAveragePrice();
        Long quantityBefore = portfolioBefore.getStockQuantity();

        PortfolioUpdateDto updateDto = PortfolioUpdateDto.builder()
                .stockQuantity(quantityBefore + 20)
                .purchasePrice(priceBefore * 20)
                .build();
        portfolioService.updatePortfolio(id,updateDto);


        Portfolio portfolioAfter = portfolioRepository.findById(id).get();
        Long priceAfter = portfolioBefore.getStockAveragePrice();
        Long quantityAfter = portfolioBefore.getStockQuantity();

        Assertions.assertThat(priceAfter).isEqualTo(priceBefore * 20);
        Assertions.assertThat(quantityAfter).isEqualTo(quantityBefore + 20);
    }

    @Test
    public void deleteTest(){

        long countBefore = portfolioRepository.count();

        List<Portfolio> all = portfolioRepository.findAll();
        long id = all.get(1).getId();

        portfolioService.deletePortfolio(id);

        long countAfter = portfolioRepository.count();

        Assertions.assertThat(countBefore).isEqualTo(countAfter+1);
    }

    @Test
    public void deleteStocksTest(){
        long countBefore = portfolioRepository.count();

        List<Portfolio> all = portfolioRepository.findAll();

        List<Long> collect = LongStream.range(0, 10)
                .map(i -> i + all.get(0).getId())
                .boxed()
                .collect(Collectors.toList());

        portfolioService.deletePortfolios(collect);

        long countAfter = portfolioRepository.count();

        Assertions.assertThat(countBefore).isEqualTo(countAfter+10);

    }


    @Test
    public void getPortfolioEdit(){
        List<PortfolioEditResponseDto> portfolioEdit = portfolioService.getPortfolioEdit(getAuthentication());

        Assertions.assertThat(portfolioEdit).isNotEmpty();

        portfolioEdit.forEach(dto -> {
            Assertions.assertThat(dto).isNotNull();
        });

        Assertions.assertThat(portfolioEdit.size()).isEqualTo(30);
    }

    @Test
    public void getPortfolioManage(){
        PortfolioManageResponseDto portfolioManage = portfolioService.getPortfolioManage(getAuthentication());
        portfolioManage.getPieChart().forEach(System.out::println);

        portfolioManage.getStocks().forEach(System.out::println);

//        System.out.println(portfolioManage);
    }

    @Test
    public void getPortfolioHedgeHome(){
        HedgeHomeResponseDto hedgeHome = portfolioService.getHedgeHome(getAuthentication());

        System.out.println(hedgeHome);
    }

//    @Test
//    public void test(){
//        portfolioService.insertPortfolio();
//        portfolioService.deletePortfolio();
//        portfolioService.deletePortfolios();

//        portfolioService.getPortfolioEdit();
//        portfolioService.getPortfolioManage();
//        portfolioService.updatePortfolio();
//        portfolioService.getHedgeHome();

//        portfolioService.getAllPortfolioRecommendDerivative();
//    }


}