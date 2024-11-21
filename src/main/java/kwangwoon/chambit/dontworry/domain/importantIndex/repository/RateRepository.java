package kwangwoon.chambit.dontworry.domain.importantIndex.repository;

import kwangwoon.chambit.dontworry.domain.importantIndex.domain.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RateRepository {

    private final IndexInfoRepository indexInfoService;

    public List<Rate> getRates() {
        return indexInfoService.getEntitiesByPattern("rate:*",Rate.class);
    }
}
