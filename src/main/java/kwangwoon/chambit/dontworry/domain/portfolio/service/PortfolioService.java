package kwangwoon.chambit.dontworry.domain.portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioInsertDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.request.PortfolioUpdateDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.*;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioElementDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.portfolio.repository.PortfolioRepository;
import kwangwoon.chambit.dontworry.domain.recommendHedge.service.RecommendHedgeService;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.infra.redis.stockPrice.PresentStockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PresentStockPriceService stockPriceService;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    private final RecommendHedgeService recommendHedgeService;

    // 추천 헷지상품 목록(2개랑, 모든 리스트)
    public Page<PortfolioRecommendDerivativeDto> getAllPortfolioRecommendDerivative(Pageable pageable, UserDetails principal){
        String username = principal.getUsername();

        return portfolioRepository.findByUsernameAllDerivative(username,pageable)
                .map(PortfolioRecommendDerivativeDto::new);
    }

    public List<PortfolioRecommendDerivativeDto> getAllPortfolioRecommendDerivative(UserDetails principal){
        String username = principal.getUsername();
        List<Portfolio> portfolios = portfolioRepository.findByUsername(username);

        try {
            return recommendHedgeService.getAllRecommendDerivative(portfolios);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    public HedgeHomeResponseDto getHedgeHome(UserDetails principal){
        String username = principal.getUsername();
        List<Portfolio> portfolios = portfolioRepository.findByStockAllPrices(username);

        try {
            List<PortfolioRecommendDerivativeDto> hedgeRecommend2 = recommendHedgeService.getTwoRecommendDerivative(portfolios);
            List<PortfolioPieDto> pieChart = getPortfolioPie(portfolios);
            return new HedgeHomeResponseDto(pieChart,hedgeRecommend2);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PortfolioManageResponseDto getPortfolioManage(UserDetails principal){
        String username = principal.getUsername();

        return getPortfolioInfo(username);
    }

    @Transactional
    public InsertUpdateResponseDto insertPortfolio(PortfolioInsertDto portfolioInsertDto, UserDetails principal){
        String username = principal.getUsername();
        User user = userRepository.findByUsername(username).get();

        Long stockId = portfolioInsertDto.getStockId();
        Stock stock = stockRepository.findByStockId(stockId).get();

        Portfolio portfolio = portfolioInsertDto.toPortfolio(user, stock);

        portfolioRepository.save(portfolio);

        Long presentStockPrice = stockPriceService.getPresentStockPrice(stock.getStockCode());

        return InsertUpdateResponseDto.builder()
                .presentPrice(presentStockPrice)
                .purchasePrice(portfolioInsertDto.getStockAveragePrice())
                .stockQuantity(portfolioInsertDto.getStockQuantity())
                .stock(stock)
                .build();
    }

    @Transactional
    public InsertUpdateResponseDto updatePortfolio(Long portfolioId, PortfolioUpdateDto portfolioUpdateDto){
        Portfolio portfolio = portfolioRepository.findById(portfolioId).get();

        portfolio.setStockQuantity(portfolioUpdateDto.getStockQuantity());
        portfolio.setStockAveragePrice(portfolioUpdateDto.getPurchasePrice());

        Stock stock = portfolio.getStock();

        Long presentStockPrice = stockPriceService.getPresentStockPrice(stock.getStockCode());

        return InsertUpdateResponseDto.builder()
                .presentPrice(presentStockPrice)
                .purchasePrice(portfolioUpdateDto.getPurchasePrice())
                .stockQuantity(portfolioUpdateDto.getStockQuantity())
                .stock(stock)
                .build();
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

        List<PortfolioPieDto> result = presentPortfolio.stream()
                .map(x -> {
                    long amount = x.getPresentPrice() * x.getStockQuantity();
                    float rate = (float) amount / sum;
                    return new PortfolioPieDto(x.getStockName(), rate);
                })
                .sorted((a, b) -> Float.compare(b.getRate(), a.getRate()))
                .toList();

        // 상위 6개 추출
        List<PortfolioPieDto> top6 = result.stream()
                .limit(6) // 상위 6개 추출
                .toList();

        // 나머지 데이터를 "기타"로 합치기
        float otherRate = result.stream()
                .skip(6) // 7번째부터 나머지 데이터
                .map(PortfolioPieDto::getRate) // 모든 rate 합산
                .reduce(0f, Float::sum); // 합계를 구함

        PortfolioPieDto others = new PortfolioPieDto("기타", otherRate);

        // 최종 리스트 만들기 (상위 6개 + 기타)
        List<PortfolioPieDto> pieChart = new ArrayList<>();
        pieChart.addAll(top6);
        if (otherRate > 0) { // 기타 데이터가 존재할 경우 추가
            pieChart.add(others);
        }

        return PortfolioManageResponseDto.builder()
                .portfolioValue(sum)
                .pieChart(pieChart)
                .stocks(presentPortfolio)
                .build();
    }

    // 파이차트
    private List<PortfolioPieDto> getPortfolioPie(List<Portfolio> stockAllPrices) {


        List<PortfolioElementDto> presentPortfolio = getPresentPortfolio(stockAllPrices);
        long sum = presentPortfolio.stream()
                .map(x -> x.getPresentPrice() * x.getStockQuantity())
                .mapToLong(Long::longValue)
                .sum();

        List<PortfolioPieDto> result = presentPortfolio.stream()
                .map(x -> {
                    long amount = x.getPresentPrice() * x.getStockQuantity();
                    float rate = (float) amount / sum;
                    return new PortfolioPieDto(x.getStockName(), rate);
                })
                .sorted((a, b) -> Float.compare(b.getRate(), a.getRate()))
                .toList();

        // 상위 6개 추출
        List<PortfolioPieDto> top6 = result.stream()
                .limit(6) // 상위 6개 추출
                .toList();

        // 나머지 데이터를 "기타"로 합치기
        float otherRate = result.stream()
                .skip(6) // 7번째부터 나머지 데이터
                .map(PortfolioPieDto::getRate) // 모든 rate 합산
                .reduce(0f, Float::sum); // 합계를 구함

        PortfolioPieDto others = new PortfolioPieDto("기타", otherRate);

        // 최종 리스트 만들기 (상위 6개 + 기타)
        List<PortfolioPieDto> finalResult = new ArrayList<>();
        finalResult.addAll(top6);
        if (otherRate > 0) { // 기타 데이터가 존재할 경우 추가
            finalResult.add(others);
        }

        return finalResult;
    }

    private List<PortfolioEditResponseDto> getEditPortfolio(List<Portfolio> portfolios){

        List<Long> presentStockPrices = stockPriceService.getPresentStockPrices(portfolios);
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

        List<Long> presentStockPrices = stockPriceService.getPresentStockPrices(portfolios);
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
