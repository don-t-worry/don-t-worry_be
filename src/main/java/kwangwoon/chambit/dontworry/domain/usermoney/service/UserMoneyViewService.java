package kwangwoon.chambit.dontworry.domain.usermoney.service;

import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyAccountResponse;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyInvestDepositResponse;
import kwangwoon.chambit.dontworry.domain.usermoney.dto.response.UserMoneyViewResponse;
import kwangwoon.chambit.dontworry.domain.usermoney.repository.UserMoneyViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMoneyViewService {
    private final UserMoneyViewRepository userMoneyViewRepository;

    public UserMoneyViewResponse getUserTradeInvest(UserDetails user){
        String username = user.getUsername();
        return userMoneyViewRepository.findByUsername(username)
                .map(UserMoneyViewResponse::new)
                .get();
    }

    public UserMoneyAccountResponse getAccountBalance(UserDetails user){
        String username = user.getUsername();
        return userMoneyViewRepository.findByUsername(username)
                .map(UserMoneyAccountResponse::new)
                .get();
    }

    public UserMoneyInvestDepositResponse getInvestDeposit(UserDetails user){
        String username = user.getUsername();
        return userMoneyViewRepository.findByUsername(username)
                .map(UserMoneyInvestDepositResponse::new)
                .get();
    }
}
