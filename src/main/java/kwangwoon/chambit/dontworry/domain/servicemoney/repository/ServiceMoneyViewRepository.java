package kwangwoon.chambit.dontworry.domain.servicemoney.repository;

import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import kwangwoon.chambit.dontworry.global.common.ViewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceMoneyViewRepository extends ViewRepository<ServiceMoneyView, Long> {
    @Query("select s from ServiceMoneyView s")
    ServiceMoneyView findServiceMoney();
}
