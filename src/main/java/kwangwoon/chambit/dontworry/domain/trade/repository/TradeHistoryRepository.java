package kwangwoon.chambit.dontworry.domain.trade.repository;

import kwangwoon.chambit.dontworry.domain.trade.domain.TradeHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory,Long> {
//    @Query("select t from TradeHistory t join fetch t.derivative d where t.profit is not null")
//    Page<TradeHistory> findByPage(Pageable pageable);

    @EntityGraph(attributePaths = {"derivative"})
    @Query("select t from TradeHistory t where t.profit is not null")
    Page<TradeHistory> findByPage(Pageable pageable);
}
