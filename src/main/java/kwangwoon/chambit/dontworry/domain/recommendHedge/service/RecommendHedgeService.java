package kwangwoon.chambit.dontworry.domain.recommendHedge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.recommendHedge.domain.RecommendHedge;
import kwangwoon.chambit.dontworry.domain.recommendHedge.repository.RecommendHedgeRepository;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendHedgeService {
    private final RecommendHedgeRepository recommendHedgeRepository;

    public List<PortfolioRecommendDerivativeDto> getAllRecommendDerivative(List<Portfolio> portfolios) throws JsonProcessingException {
        return getRecommendDerivative(portfolios, Integer.MAX_VALUE);
    }
    public List<PortfolioRecommendDerivativeDto> getTwoRecommendDerivative(List<Portfolio> portfolios) throws JsonProcessingException {
        return getRecommendDerivative(portfolios, 2);
    }

    private List<PortfolioRecommendDerivativeDto> getRecommendDerivative(List<Portfolio> portfolios, int maxValue) throws JsonProcessingException {
        Map<String, Long> quantities = new HashMap<>();
        List<Map<String, Object>> pairs = getPairs(portfolios, quantities);
        return getPortfolioRecommendDerivativeDtos(pairs, quantities, maxValue);
    }

    private List<Map<String, Object>> getPairs(List<Portfolio> portfolios, Map<String, Long> quantities) {
        List<Map<String, Object>> pairs = portfolios.stream()
                .sorted((p1,p2) -> Long.compare(p2.getStockQuantity(), p1.getStockQuantity()))
                .map(portfolio -> {
                    Object stockId = portfolio.getStock() != null ? portfolio.getStock().getId() : null;
                    Object hedgeType = portfolio.getUser() != null ? portfolio.getUser().getHedgeType().name() : null;

                    quantities.put(String.valueOf(stockId) + String.valueOf(hedgeType), portfolio.getStockQuantity());

                    return Map.of("stock_id", stockId, "hedge_type", hedgeType);
                })
                .collect(Collectors.toList());
        return pairs;
    }

    private List<PortfolioRecommendDerivativeDto> getPortfolioRecommendDerivativeDtos(List<Map<String, Object>> pairs, Map<String, Long> quantities, int limit) throws JsonProcessingException {
        String jsonPairs = new ObjectMapper().writeValueAsString(pairs);
        List<Object[]> recommendHedges = recommendHedgeRepository.findByStockIdAndHedgeTypeIn(jsonPairs);
        List<PortfolioRecommendDerivativeDto> result = new ArrayList<>();
        for(var hedge : recommendHedges){
            String quantityKey = String.valueOf(hedge[0]) + String.valueOf(hedge[1]);
            Long stockQuantity = quantities.get(quantityKey);
            PortfolioRecommendDerivativeDto dto = new PortfolioRecommendDerivativeDto(hedge, stockQuantity);

            result.add(dto);
        }

        return result.stream().limit(limit).collect(Collectors.toList());
    }

}
