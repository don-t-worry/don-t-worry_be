package kwangwoon.chambit.dontworry.domain.importantIndex.service;

import kwangwoon.chambit.dontworry.domain.importantIndex.dto.ResponseDto;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.CommodityRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.IndexRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.InterestRateRepository;
import kwangwoon.chambit.dontworry.domain.importantIndex.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportantIndexService {
    private final CommodityRepository commodityRepository;
    private final IndexRepository indexRepository;
    private final InterestRateRepository interestRateRepository;
    private final RateRepository rateRepository;

    public ResponseDto getAllIndexes(){
        return ResponseDto.builder()
                .commodity(commodityRepository.getCommodities())
                .index(indexRepository.getIndexes())
                .interestRate(interestRateRepository.getInterestRates())
                .rate(rateRepository.getRates())
                .build();
    }
}
