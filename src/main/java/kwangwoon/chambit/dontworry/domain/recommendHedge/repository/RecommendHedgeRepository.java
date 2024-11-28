package kwangwoon.chambit.dontworry.domain.recommendHedge.repository;

import kwangwoon.chambit.dontworry.domain.recommendHedge.domain.RecommendHedge;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecommendHedgeRepository extends JpaRepository<RecommendHedge, Long> {
    @Query("select r from RecommendHedge r where r.stock.id=:stockId and r.hedgeType=:hedgeType")
    Optional<RecommendHedge> findByStockIdAndHedgeType(@Param("stockId") Long stockId, @Param("hedgeType")HedgeType hedgeType);

    @Query(value = "SELECT r.stock_id, r.hedge_type, r.risk_reduction_rate, d.derivative_name, d.derivative_code, s.stock_name, s.image_url, r.quantity " +
            "FROM recommend_hedge r " +
            "JOIN stock s ON r.stock_id = s.stock_id " +
            "JOIN derivative d ON r.derivative_id = d.derivative_id " +
            "WHERE JSON_CONTAINS(:pairs, JSON_OBJECT('stock_id', r.stock_id, 'hedge_type', r.hedge_type))",
            nativeQuery = true)
    List<Object[]> findByStockIdAndHedgeTypeIn(@Param("pairs") String jsonPairs);


}
