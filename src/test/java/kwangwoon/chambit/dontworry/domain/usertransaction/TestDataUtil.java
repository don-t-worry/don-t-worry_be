package kwangwoon.chambit.dontworry.domain.usertransaction;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.usertransaction.domain.UserTransactionHistory;
import kwangwoon.chambit.dontworry.domain.usertransaction.dto.request.UserTransactionInsertDto;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.AccountType;
import kwangwoon.chambit.dontworry.domain.usertransaction.enums.TransactionType;
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

    public static UserTransactionHistory getUserTransactionHistory(User user){
        return UserTransactionHistory.builder()
                .user(user)
                .funds(1000L)
                .transactionType(TransactionType.DEPOSIT)
                .accountType(AccountType.ACCOUNT)
                .build();
    }
    public static UserDetails getAuthentication() {
        Oauth2ClientDto oauth2Client = Oauth2ClientDto.builder()
                .username("재윤")
                .role("ROLE_USER")
                .build();
        return new CustomOauth2ClientDto(oauth2Client);
    }
    public static UserTransactionInsertDto getUserTransactionInsertDto(){
        return UserTransactionInsertDto.builder()
                .accountType(AccountType.ACCOUNT)
                .funds(1000L)
                .transactionType(TransactionType.DEPOSIT)
                .build();
    }

    public static UserDetails getRealAuthentication() {
        Oauth2ClientDto oauth2Client = Oauth2ClientDto.builder()
                .username("kakao3648188757")
                .role("ROLE_USER")
                .build();
        return new CustomOauth2ClientDto(oauth2Client);
    }
}
