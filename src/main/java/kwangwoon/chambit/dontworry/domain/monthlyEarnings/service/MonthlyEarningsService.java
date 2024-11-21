package kwangwoon.chambit.dontworry.domain.monthlyEarnings.service;

import kwangwoon.chambit.dontworry.domain.monthlyEarnings.domain.MonthlyEarnings;
import kwangwoon.chambit.dontworry.domain.monthlyEarnings.dto.MonthlyEarningsResponseDto;
import kwangwoon.chambit.dontworry.domain.monthlyEarnings.repository.MonthlyEarningsRepository;
import kwangwoon.chambit.dontworry.global.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyEarningsService {
    private final MonthlyEarningsRepository monthlyEarningsRepository;

    public PageResponseDto<MonthlyEarningsResponseDto> getMonthlyEarnings(Pageable pageable){
        return new PageResponseDto<>(monthlyEarningsRepository.findAll(pageable)
                .map(MonthlyEarningsResponseDto::new));
    }
}
