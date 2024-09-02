package kwangwoon.chambit.dontworry.domain.portfolio.repository;

import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
    //무한 스크롤이랑 상위 2개 보여줄때 이걸로 사용
    @Query("select p from Portfolio p join fetch p.user u left join fetch p.stock s left join fetch p.derivative d where u.username= :username")
    Page<Portfolio> findByUsernameAllDerivative(@Param("username")String username, Pageable pageable);

    @Query("select p from Portfolio p join fetch p.user u left join fetch p.stock s left join fetch p.derivative d where u.username= :username")
    List<Portfolio> findByUsernameAllDerivative(@Param("username")String username);

    @Query("select p from Portfolio p join fetch p.user u left join fetch p.stock s where u.username=:username")
    List<Portfolio> findByStockAllPrices(@Param("username")String username);

    @Modifying
    @Query("delete from Portfolio p where p.id in :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query("delete from Portfolio p where p.id=:id")
    void deleteById(@Param("id") Long id);
}
