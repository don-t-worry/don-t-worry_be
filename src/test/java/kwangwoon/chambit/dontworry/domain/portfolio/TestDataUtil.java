package kwangwoon.chambit.dontworry.domain.portfolio;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.HedgeHomeResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.PortfolioEditResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.PortfolioManageResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioElementDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioPieDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public class TestDataUtil {

    private final UserRepository userRepository;
    public static User user;

    public TestDataUtil(UserRepository userRepository){
        this.userRepository = userRepository;
        user = userRepository.findByUsername("kakao3648188757").get();
    }

    public static List<PortfolioRecommendDerivativeDto> getPageDerivative() {
        return getPortfolios().stream()
                .map(PortfolioRecommendDerivativeDto::new)
                .collect(Collectors.toList());
    }

//    public static Result getPageDerivative() {
//        List<PortfolioRecommendDerivativeDto> list = getPortfolios().stream()
//                .map(PortfolioRecommendDerivativeDto::new)
//                .collect(Collectors.toList());
//
//        PageRequest pageRequest = PageRequest.of(0,10, Sort.by("stockQuantity").descending());
//
//
//        Page<PortfolioRecommendDerivativeDto> dtos = new PageImpl<>(list,pageRequest,list.size());
//        Result result = new Result(pageRequest, dtos);
//        return result;
//    }

    public record Result(PageRequest pageRequest, Page<PortfolioRecommendDerivativeDto> dtos) {
    }

    public static List<PortfolioEditResponseDto> getPortfolioEditResponseDtos() {
        List<Portfolio> portfolios = getPortfolios();

        List<PortfolioEditResponseDto> portfolioEdit = List.of(
                PortfolioEditResponseDto.builder()
                        .portfolio(portfolios.get(0))
                        .presentPrice(3000L)
                        .build(),
                PortfolioEditResponseDto.builder()
                        .portfolio(portfolios.get(1))
                        .presentPrice(4000L)
                        .build(),
                PortfolioEditResponseDto.builder()
                        .portfolio(portfolios.get(2))
                        .presentPrice(5000L)
                        .build(),
                PortfolioEditResponseDto.builder()
                        .portfolio(portfolios.get(3))
                        .presentPrice(6000L)
                        .build()
        );
        return portfolioEdit;
    }

    public static PortfolioManageResponseDto getPortfolioManageResponseDto() {
        List<PortfolioElementDto> portfolioElementDtos = getPortfolioElementDtos();

        PortfolioManageResponseDto portfolioManageDto = PortfolioManageResponseDto.builder()
                .stocks(portfolioElementDtos)
                .pieChart(getPortfolioPieDtos())
                .build();
        return portfolioManageDto;
    }
    public static List<PortfolioElementDto> getPortfolioElementDtos() {
        List<Portfolio> portfolios = getPortfolios();

        List<PortfolioElementDto> portfolioElementDtos = List.of(
                PortfolioElementDto.builder()
                        .portfolio(portfolios.get(0))
                        .presentPrice(4000L)
                        .profit(1000L)
                        .profitRate(3F)
                        .build(),
                PortfolioElementDto.builder()
                        .portfolio(portfolios.get(1))
                        .presentPrice(5000L)
                        .profit(1000L)
                        .profitRate(3F)
                        .build(),
                PortfolioElementDto.builder()
                        .portfolio(portfolios.get(2))
                        .presentPrice(6000L)
                        .profit(1000L)
                        .profitRate(3F)
                        .build(),
                PortfolioElementDto.builder()
                        .portfolio(portfolios.get(3))
                        .presentPrice(7000L)
                        .profit(1000L)
                        .profitRate(3F)
                        .build()
        );
        return portfolioElementDtos;
    }



    public static UserDetails getAuthentication() {
        Oauth2ClientDto oauth2Client = Oauth2ClientDto.builder()
                .username("kakao3648188757")
                .role("ROLE_USER")
                .build();
        return new CustomOauth2ClientDto(oauth2Client);
    }

    public static HedgeHomeResponseDto getHedgeHomeResponseDto() {

        List<Portfolio> portfolios = getPortfolios();


        List<PortfolioRecommendDerivativeDto> derivativeDtos =
                List.of(
                        PortfolioRecommendDerivativeDto.builder()
                                .portfolio(portfolios.get(0))
                                .build(),
                        PortfolioRecommendDerivativeDto.builder()
                                .portfolio(portfolios.get(1))
                                .build(),
                        PortfolioRecommendDerivativeDto.builder()
                                .portfolio(portfolios.get(2))
                                .build(),
                        PortfolioRecommendDerivativeDto.builder()
                                .portfolio(portfolios.get(3))
                                .build()
                );

        List<PortfolioPieDto> pieDtos = getPortfolioPieDtos();


        HedgeHomeResponseDto hedgeHomeResponseDto = HedgeHomeResponseDto.builder()
                .hedgeRecommend2(derivativeDtos)
                .pieChart(pieDtos)
                .build();
        return hedgeHomeResponseDto;
    }

    public static List<Portfolio> getPortfolios() {
        List<Stock> stocks = getStocks();

        List<Derivative> derivatives = getDerivatives();

        List<Portfolio> portfolios = List.of(
                Portfolio.builder()
                        .user(user)
                        .stock(stocks.get(0))
                        .derivative(derivatives.get(0))
                        .derivativeQuantity(30L)
                        .riskReductionRate(30L)
                        .stockQuantity(20L)
                        .stockAveragePrice(3000L)
                        .build(),
                Portfolio.builder()
                        .user(user)
                        .stock(stocks.get(1))
                        .derivative(derivatives.get(1))
                        .derivativeQuantity(20L)
                        .riskReductionRate(20L)
                        .stockQuantity(30L)
                        .stockAveragePrice(4000L)
                        .build(),
                Portfolio.builder()
                        .user(user)
                        .stock(stocks.get(2))
                        .derivative(derivatives.get(2))
                        .derivativeQuantity(20L)
                        .riskReductionRate(20L)
                        .stockQuantity(40L)
                        .stockAveragePrice(5000L)
                        .build(),
                Portfolio.builder()
                        .user(user)
                        .stock(stocks.get(3))
                        .derivative(derivatives.get(3))
                        .derivativeQuantity(30L)
                        .riskReductionRate(30L)
                        .stockQuantity(50L)
                        .stockAveragePrice(6000L)
                        .build()
        );
        return portfolios;
    }

    public static List<PortfolioPieDto> getPortfolioPieDtos() {
        List<PortfolioPieDto> pieDtos = List.of(
                PortfolioPieDto.builder()
                        .stockName("삼성")
                        .rate(30f)
                        .build(),
                PortfolioPieDto.builder()
                        .stockName("엘지")
                        .rate(20f)
                        .build(),
                PortfolioPieDto.builder()
                        .stockName("sk")
                        .rate(20f)
                        .build(),
                PortfolioPieDto.builder()
                        .stockName("apple")
                        .rate(30f)
                        .build()
        );
        return pieDtos;
    }

    public static List<Derivative> getDerivatives() {


        return List.of(
                Derivative.builder().derivativeName("삼성 옵션").derivativeCode("삼성 옵션 코드").build(),
                Derivative.builder().derivativeName("엘지 옵션").derivativeCode("엘지 옵션 코드").build(),
                Derivative.builder().derivativeName("sk 옵션").derivativeCode("sk 옵션 코드").build(),
                Derivative.builder().derivativeName("apple 옵션").derivativeCode("apple 옵션 코드").build()
        );
    }

    public static List<Stock> getStocks() {

        return List.of(
                Stock.builder().stockName("삼성").imageUrl("삼성 이미지").build(),
                Stock.builder().stockName("엘지").imageUrl("엘지 이미지").build(),
                Stock.builder().stockName("sk").imageUrl("sk 이미지").build(),
                Stock.builder().stockName("apple").imageUrl("apple 이미지").build()
        );
    }
}
