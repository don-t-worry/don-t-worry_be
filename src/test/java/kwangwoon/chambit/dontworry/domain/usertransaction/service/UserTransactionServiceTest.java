package kwangwoon.chambit.dontworry.domain.usertransaction.service;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.domain.usertransaction.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.usertransaction.domain.UserTransactionHistory;
import kwangwoon.chambit.dontworry.domain.usertransaction.repository.UserTransactionHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static kwangwoon.chambit.dontworry.domain.usertransaction.TestDataUtil.*;

@SpringBootTest
@Transactional
class UserTransactionServiceTest {

    @Autowired
    UserTransactionHistoryService userTransactionService;

    @Autowired
    UserRepository userRepository;

    @MockBean
    UserTransactionHistoryRepository userTransactionHistoryRepository;

    @Test
    public void getOccurTransaction_when_then(){

        User user = getUser();
        User save = userRepository.save(user);

        UserTransactionHistory userTransactionHistory = getUserTransactionHistory(save);

        Mockito.when(userTransactionHistoryRepository.save(Mockito.any())).thenReturn(userTransactionHistory);

        UserTransactionHistory transactionHistory = userTransactionService.occurTransaction(getUserTransactionInsertDto(), getAuthentication());

        Assertions.assertThat(transactionHistory).isEqualTo(userTransactionHistory);

        System.out.println(transactionHistory);
    }

}