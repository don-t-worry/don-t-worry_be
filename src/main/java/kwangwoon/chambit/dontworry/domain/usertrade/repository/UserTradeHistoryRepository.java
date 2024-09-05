package kwangwoon.chambit.dontworry.domain.usertrade.repository;

import kwangwoon.chambit.dontworry.domain.usertrade.domain.UserTradeHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTradeHistoryRepository extends JpaRepository<UserTradeHistory,Long> {
    @Query("select ut from UserTradeHistory ut join fetch ut.user u join fetch ut.tradeHistory t where u.username=:username")
    Page<UserTradeHistory> findByUsername(@Param("username") String username, Pageable pageable);
}
