package kwangwoon.chambit.dontworry.domain.portfolio.service;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioInsertDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioUpdateDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.*;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioElementDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.portfolio.repository.PortfolioRepository;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.common.request.StockPriceService;
import kwangwoon.chambit.dontworry.global.infra.redis.stockPrice.PresentStockPriceService;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PresentStockPriceService stockPriceService;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    // 추천 헷지상품 목록(2개랑, 모든 리스트)
    public Page<PortfolioRecommendDerivativeDto> getAllPortfolioRecommendDerivative(Pageable pageable, UserDetails principal){
        String username = principal.getUsername();

        return portfolioRepository.findByUsernameAllDerivative(username,pageable)
                .map(PortfolioRecommendDerivativeDto::new);
    }

    public List<PortfolioRecommendDerivativeDto> getAllPortfolioRecommendDerivative(UserDetails principal){
        String username = principal.getUsername();
        return portfolioRepository.findByUsernameAllDerivative(username)
                .stream()
                .map(PortfolioRecommendDerivativeDto::new)
                .collect(Collectors.toList());
    }



    public HedgeHomeResponseDto getHedgeHome(UserDetails principal){
        String username = principal.getUsername();

        PageRequest pageRequest = PageRequest.of(0,2, Sort.by("stockQuantity").descending());

        List<PortfolioRecommendDerivativeDto> hedgeRecommend2 = getPortfolioRecommendDerivative2(username, pageRequest);
        List<PortfolioPieDto> pieChart = getPortfolioPie(username);
        String name = userRepository.findByUsername(username).get().getName();

        return new HedgeHomeResponseDto(pieChart,hedgeRecommend2);
    }

    public PortfolioManageResponseDto getPortfolioManage(UserDetails principal){
        String username = principal.getUsername();

        return getPortfolioInfo(username);
    }

    @Transactional
    public void insertPortfolio(PortfolioInsertDto portfolioInsertDto, UserDetails principal){
        String username = principal.getUsername();
        User user = userRepository.findByUsername(username).get();

        Long stockId = portfolioInsertDto.getStockId();
        Stock stock = stockRepository.findByStockId(stockId).get();

        Portfolio portfolio = portfolioInsertDto.toPortfolio(user, stock);

        portfolioRepository.save(portfolio);
    }

    @Transactional
    public void updatePortfolio(Long portfolioId, PortfolioUpdateDto portfolioUpdateDto){
        Portfolio portfolio = portfolioRepository.findById(portfolioId).get();

        portfolio.setStockQuantity(portfolioUpdateDto.getStockQuantity());
        portfolio.setStockAveragePrice(portfolioUpdateDto.getPurchasePrice());
    }

    @Transactional
    public void deletePortfolios(List<Long> portfolioIds){
        portfolioRepository.deleteByIds(portfolioIds);
    }

    @Transactional
    public void deletePortfolio(Long portfolioId){
        portfolioRepository.deleteById(portfolioId);
    }

    public List<PortfolioEditResponseDto> getPortfolioEdit(UserDetails principal){
        String username = principal.getUsername();

        List<Portfolio> portfolios = portfolioRepository.findByStockAllPrices(username);

        return getEditPortfolio(portfolios);
    }


    // 여기서부터는 private 메서드

    private List<PortfolioRecommendDerivativeDto> getPortfolioRecommendDerivative2(String username, PageRequest pageRequest) {
        List<PortfolioRecommendDerivativeDto> hedgeRecommend2 = portfolioRepository.findByUsernameAllDerivative(username, pageRequest)
                .map(PortfolioRecommendDerivativeDto::new)
                .stream()
                .collect(Collectors.toList());
        return hedgeRecommend2;
    }
    private PortfolioManageResponseDto getPortfolioInfo(String username) {
        List<Portfolio> stockAllPrices = portfolioRepository.findByStockAllPrices(username);


        List<PortfolioElementDto> presentPortfolio = getPresentPortfolio(stockAllPrices);

        long sum = presentPortfolio.stream()
                .map(x -> x.getPresentPrice() * x.getStockQuantity())
                .mapToLong(Long::longValue)
                .sum();

        List<PortfolioPieDto> pieChart = presentPortfolio.stream()
                .map(x -> {
                    long amount = x.getPresentPrice() * x.getStockQuantity();
                    float rate = (float) amount / sum;


                    if (rate < 0.01) {
                        return new PortfolioPieDto("기타", rate);
                    } else {
                        return new PortfolioPieDto(x.getStockName(), rate);
                    }
                })
                .collect(Collectors.groupingBy(
                        PortfolioPieDto::getStockName,
                        Collectors.reducing(
                                (a, b) -> new PortfolioPieDto(a.getStockName(), a.getRate() + b.getRate())
                        )
                ))
                .entrySet().stream()
                .map(entry -> entry.getValue()
                        .map(dto -> new PortfolioPieDto(entry.getKey(), dto.getRate()))
                        .orElse(new PortfolioPieDto(entry.getKey(), 0f)))
                .sorted((a, b) -> Float.compare(b.getRate(), a.getRate()))
                .collect(Collectors.toList());

        return PortfolioManageResponseDto.builder()
                .pieChart(pieChart)
                .stocks(presentPortfolio)
                .build();
    }

    // 파이차트
    private List<PortfolioPieDto> getPortfolioPie(String username) {
        List<Portfolio> stockAllPrices = portfolioRepository.findByStockAllPrices(username);


        List<PortfolioElementDto> presentPortfolio = getPresentPortfolio(stockAllPrices);
        long sum = presentPortfolio.stream()
                .map(x -> x.getPresentPrice() * x.getStockQuantity())
                .mapToLong(Long::longValue)
                .sum();

        return presentPortfolio.stream()
                .map(x -> {
                    long amount = x.getPresentPrice() * x.getStockQuantity();
                    float rate = (float) amount / sum;

                    if(rate < 0.01){
                        return new PortfolioPieDto("기타",rate);
                    }else{
                        return new PortfolioPieDto(x.getStockName(),rate);
                    }
                })
                .collect(Collectors.groupingBy(
                        PortfolioPieDto::getStockName,
                        Collectors.reducing(
                                (a, b) -> new PortfolioPieDto(a.getStockName(), a.getRate() + b.getRate())
                        )
                ))
                .entrySet().stream()
                .map(entry -> entry.getValue()
                        .map(dto -> new PortfolioPieDto(entry.getKey(), dto.getRate()))
                        .orElse(new PortfolioPieDto(entry.getKey(), 0f)))
                .sorted((a, b) -> Float.compare(b.getRate(), a.getRate()))
                .collect(Collectors.toList());
    }

    private List<PortfolioEditResponseDto> getEditPortfolio(List<Portfolio> portfolios){

        List<Long> presentStockPrices = stockPriceService.getPresentStockPrice(portfolios);
        List<PortfolioEditResponseDto> result = new ArrayList<>();
        for(int i=0; i< portfolios.size(); i++){
            result.add(new PortfolioEditResponseDto(portfolios.get(i), presentStockPrices.get(i)));
        }

        result.sort(Comparator.comparing(item ->
            new BigDecimal(item.getStockQuantity()).multiply(new BigDecimal(item.getPresentPrice()))
        ));

        return result;
    }

    private List<PortfolioElementDto> getPresentPortfolio(List<Portfolio> portfolios) {

        List<Long> presentStockPrices = stockPriceService.getPresentStockPrice(portfolios);
        List<PortfolioElementDto> result = new ArrayList<>();

        for(int i=0; i<portfolios.size(); i++){
            Long presentStockPrice = presentStockPrices.get(i);
            Portfolio p = portfolios.get(i);

            long profit = (presentStockPrice - p.getStockAveragePrice()) * p.getStockQuantity();
            float profitRate = ((float) profit / (p.getStockAveragePrice() * p.getStockQuantity())) * 100;

            result.add(new PortfolioElementDto(p, presentStockPrice, profit, profitRate));
        }

        result.sort(Comparator.comparing(item ->
                new BigDecimal(item.getStockQuantity()).multiply(new BigDecimal(item.getPresentPrice()))
        ));

        return result;
    }

}
