package kwangwoon.chambit.dontworry.domain.usermoney.repository;

import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import kwangwoon.chambit.dontworry.global.common.ViewRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserMoneyViewRepository extends ViewRepository<UserMoneyView, Long> {
    @Query("select m from UserMoneyView m join fetch m.user u where u.username=:username")
    Optional<UserMoneyView> findByUsername(@Param("username") String username);
}
