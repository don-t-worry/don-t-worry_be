package kwangwoon.chambit.dontworry.domain.usertransaction.repository;

import kwangwoon.chambit.dontworry.domain.usertransaction.domain.UserTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionHistoryRepository extends JpaRepository<UserTransactionHistory,Long> {
}
