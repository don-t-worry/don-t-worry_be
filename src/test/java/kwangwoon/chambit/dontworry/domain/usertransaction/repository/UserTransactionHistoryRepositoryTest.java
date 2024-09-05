package kwangwoon.chambit.dontworry.domain.usertransaction.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.domain.usertransaction.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.usertransaction.domain.UserTransactionHistory;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.AccountType;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.TransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static kwangwoon.chambit.dontworry.domain.usertransaction.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTransactionHistoryRepositoryTest {
    @Autowired
    UserTransactionHistoryRepository userTransactionHistoryRepository;
    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void insert(){

        User user = getUser();

        User save = userRepository.save(user);

        em.flush();
        em.clear();

        long beforeCount = userTransactionHistoryRepository.count();

        UserTransactionHistory transactionHistory = getUserTransactionHistory(save);

        userTransactionHistoryRepository.save(transactionHistory);

        em.flush();
        em.clear();

        long afterCount = userTransactionHistoryRepository.count();


        Assertions.assertThat(afterCount).isEqualTo(beforeCount+1);

    }
}