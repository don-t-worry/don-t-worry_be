package kwangwoon.chambit.dontworry.domain.stock.repository;

import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    @Query("select s from Stock s where s.stockName like %:searchKeyword%")
    Page<Stock> findBySearchKeyword(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select s from Stock s where s.id = :id")
    Optional<Stock> findByStockId(@Param("id") Long id);


}

