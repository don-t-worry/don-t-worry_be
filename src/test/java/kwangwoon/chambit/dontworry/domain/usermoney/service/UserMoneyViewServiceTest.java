package kwangwoon.chambit.dontworry.domain.usermoney.service;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyViewResponse;
import kwangwoon.chambit.dontworry.domain.usermoney.repository.UserMoneyViewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static kwangwoon.chambit.dontworry.domain.usermoney.TestDataUtil.*;

@SpringBootTest
@Transactional
class UserMoneyViewServiceTest {
    @MockBean
    UserMoneyViewRepository userMoneyViewRepository;

    @Autowired
    UserMoneyViewService userMoneyViewService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getUserTradeInvest_when_thenSuccessDto(){

        User user = getUser();
        User save = userRepository.save(user);
        UserMoneyView userMoneyView = getUserMoneyView(save);


        Mockito.when(userMoneyViewRepository.findByUsername(Mockito.any())).thenReturn(Optional.ofNullable(userMoneyView));

        UserMoneyViewResponse userTradeInvest = userMoneyViewService.getUserTradeInvest(getAuthentication());

        Mockito.verify(userMoneyViewRepository).findByUsername(Mockito.any());

        Assertions.assertThat(userTradeInvest).isEqualTo(UserMoneyViewResponse.builder().userMoneyView(userMoneyView).build());

        System.out.println(userTradeInvest);
    }
}