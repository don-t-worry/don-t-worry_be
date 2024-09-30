package kwangwoon.chambit.dontworry.domain.usertrade.service;

import kwangwoon.chambit.dontworry.domain.usertrade.dto.response.UserTradeHistoryResponseDto;
import kwangwoon.chambit.dontworry.domain.usertrade.repository.UserTradeHistoryRepository;
import kwangwoon.chambit.dontworry.global.common.dto.PageResponseDto;
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

    public PageResponseDto<UserTradeHistoryResponseDto> getUserTradeHistory(Pageable pageable, @AuthenticationPrincipal UserDetails user){
        return new PageResponseDto<>(userTradeHistoryRepository.findByUsername(user.getUsername(), pageable)
                .map(UserTradeHistoryResponseDto::new));
    }
}
