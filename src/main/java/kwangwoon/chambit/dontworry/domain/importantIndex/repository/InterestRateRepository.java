package kwangwoon.chambit.dontworry.domain.importantIndex.repository;

import kwangwoon.chambit.dontworry.domain.importantIndex.domain.InterestRate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class InterestRateRepository {


    private final StringRedisTemplate template;

    private final List<String> requireRate = Stream.of("한국", "미국")
            .map(key -> "interest_rate:"+key)
            .collect(Collectors.toList());

    public List<InterestRate> getInterestRates() {
        List<String> rates = template.opsForValue().multiGet(requireRate);

        return IntStream.range(0, requireRate.size())
                .mapToObj(i -> {
                    String countryKey = requireRate.get(i).replace("interest_rate:", ""); // "interest_rate:" 제거
                    Double rate = (rates.get(i) == null) ? -1D : Double.parseDouble(rates.get(i));
                    return new InterestRate(countryKey, rate);
                })
                .collect(Collectors.toList());
    }

}
