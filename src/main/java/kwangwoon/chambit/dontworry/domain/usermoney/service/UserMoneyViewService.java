package kwangwoon.chambit.dontworry.domain.usermoney.service;

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

    public UserMoneyViewResponse getUserTradeInvest(@AuthenticationPrincipal UserDetails user){
        String username = user.getUsername();
        return userMoneyViewRepository.findByUsername(username)
                .map(UserMoneyViewResponse::new)
                .get();
    }
}
