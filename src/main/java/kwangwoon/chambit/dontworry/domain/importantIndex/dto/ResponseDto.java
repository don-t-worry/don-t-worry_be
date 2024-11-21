package kwangwoon.chambit.dontworry.domain.importantIndex.dto;

import kwangwoon.chambit.dontworry.domain.importantIndex.domain.Commodity;
import kwangwoon.chambit.dontworry.domain.importantIndex.domain.Index;
import kwangwoon.chambit.dontworry.domain.importantIndex.domain.InterestRate;
import kwangwoon.chambit.dontworry.domain.importantIndex.domain.Rate;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseDto {
    List<Index> index;
    List<Rate> rate;
    List<Commodity> commodity;
    List<InterestRate> interestRate;
}
