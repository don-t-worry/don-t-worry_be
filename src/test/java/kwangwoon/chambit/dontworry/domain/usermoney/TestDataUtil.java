package kwangwoon.chambit.dontworry.domain.usermoney;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import org.springframework.security.core.userdetails.UserDetails;

public class TestDataUtil {
    public static User getUser(){
        return User.builder()
                .hedgeType(HedgeType.STABLE)
                .role("ROLE_USER")
                .username("재윤")
                .name("재재윤")
                .build();
    }

    public static UserMoneyView getUserMoneyView(User user){
        return UserMoneyView
                .builder()
                .user(user)
                .accountBalance(500L)
                .activeInvestments(200L)
                .availableInvestments(300L)
                .investmentBalance(500L)
                .profit(400L)
                .profitRate(2L)
                .build();
    }

    public static UserDetails getAuthentication() {
        Oauth2ClientDto oauth2Client = Oauth2ClientDto.builder()
                .username("재윤")
                .role("ROLE_USER")
                .build();
        return new CustomOauth2ClientDto(oauth2Client);
    }
}
