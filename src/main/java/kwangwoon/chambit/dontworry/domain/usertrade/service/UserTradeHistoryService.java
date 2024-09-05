package kwangwoon.chambit.dontworry.domain.usertrade.service;

import kwangwoon.chambit.dontworry.domain.usertrade.dto.response.UserTradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.usertrade.repository.UserTradeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTradeHistoryService {
    private final UserTradeHistoryRepository userTradeHistoryRepository;

    public Page<UserTradeHistoryResponseDto> getUserTradeHistory(Pageable pageable,@AuthenticationPrincipal UserDetails user){
        return userTradeHistoryRepository.findByUsername(user.getUsername(), pageable)
                .map(UserTradeHistoryResponseDto::new);
    }
}
