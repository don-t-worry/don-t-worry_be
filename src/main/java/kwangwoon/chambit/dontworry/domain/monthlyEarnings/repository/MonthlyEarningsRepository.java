package kwangwoon.chambit.dontworry.domain.monthlyEarnings.repository;

import kwangwoon.chambit.dontworry.domain.monthlyEarnings.domain.MonthlyEarnings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyEarningsRepository extends JpaRepository<MonthlyEarnings, Long> {
    Page<MonthlyEarnings> findAll(Pageable pageable);
}
