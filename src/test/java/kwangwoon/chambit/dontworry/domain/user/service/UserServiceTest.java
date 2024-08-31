package kwangwoon.chambit.dontworry.domain.user.service;

import jakarta.persistence.EntityManager;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import org.apache.catalina.session.PersistentManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void insertUser(){

        long countBefore = userRepository.count();

        UserSignUpDto dto = UserSignUpDto.builder()
                .username("kakao")
                .name("심재윤")
                .hedgeType(HedgeType.STABLE)
                .build();

        userService.signUp(dto);

        em.flush();
        em.clear();

        long countAfter = userRepository.count();

        Assertions.assertThat(countAfter).isEqualTo(countBefore+1);

    }

    @Test
    public void selectHedges(){
        System.out.println(userService.getHedgeTypeList());
    }

    @Test
    public void updateUsername(){
        UserSignUpDto dto = UserSignUpDto.builder()
                .username("kakao")
                .name("심재윤")
                .hedgeType(HedgeType.STABLE)
                .build();

        userService.signUp(dto);

        em.flush();
        em.clear();



        User userBefore = userRepository.findByUsername("kakao").get();
        String nameBefore = userBefore.getName();
        HedgeType hedgeTypeBefore = userBefore.getHedgeType();

        Oauth2ClientDto oauth2ClientDto = Oauth2ClientDto.builder()
                .role("ROLE_USER")
                .username("kakao")
                .isExist(true)
                .build();

        CustomOauth2ClientDto customOauth2ClientDto = new CustomOauth2ClientDto(oauth2ClientDto);


        userService.updateName("신건", customOauth2ClientDto);
        userService.updateHedgeType(HedgeType.BALANCED, customOauth2ClientDto);

        em.flush();
        em.clear();

        User userAfter = userRepository.findByUsername("kakao").get();
        String nameAfter = userAfter.getName();
        HedgeType hedgeTypeAfter = userAfter.getHedgeType();

        Assertions.assertThat(nameAfter).isNotEqualTo(nameBefore);
        Assertions.assertThat(hedgeTypeAfter).isNotEqualTo(hedgeTypeBefore);


    }
}