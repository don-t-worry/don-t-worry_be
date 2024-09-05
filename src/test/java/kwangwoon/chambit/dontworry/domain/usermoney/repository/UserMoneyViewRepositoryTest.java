package kwangwoon.chambit.dontworry.domain.usermoney.repository;

import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserMoneyViewRepositoryTest {
    @Autowired
    UserMoneyViewRepository userMoneyViewRepository;

    @Test
    public void test(){
        UserMoneyView userMoneyView = userMoneyViewRepository.findByUsername("juhoho").get();
        System.out.println(userMoneyView);
    }
}