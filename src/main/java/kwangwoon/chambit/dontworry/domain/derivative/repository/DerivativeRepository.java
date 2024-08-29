package kwangwoon.chambit.dontworry.domain.derivative.repository;

import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DerivativeRepository extends JpaRepository<Derivative,Long> {
    @Query("select s, d from Derivative d join fetch d.stock s " +
            "where d.id in (select max(d1.id) from Derivative d1 group by d1.stock)")
    List<Object[]> findStockHavingDerivative();
}
