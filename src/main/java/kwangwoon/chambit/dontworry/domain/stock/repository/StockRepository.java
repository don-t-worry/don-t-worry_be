package kwangwoon.chambit.dontworry.domain.stock.repository;

import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    @Query("select s from Stock s where s.stockName like %:searchKeyword%")
    List<Stock> findBySearchKeyword(@Param("searchKeyword") String searchKeyword);

    @Query("select s from Stock s where s.id = :id")
    Optional<Stock> findByStockId(@Param("id") Long id);


}

