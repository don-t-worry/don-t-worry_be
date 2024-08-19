package kwangwoon.chambit.dontworry.domain.user.dto.response;

import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.Data;

@Data
public class HedgeResponseDto {
    private HedgeType hedgeType;
    private String korean;
    private int minimum;
    private int maximum;

    public HedgeResponseDto(HedgeType hedgeType){
        this.hedgeType = hedgeType;
        this.korean = hedgeType.getKorean();
        this.minimum = hedgeType.getLowerHedgeRatio();
        this.maximum = hedgeType.getUpperHedgeRatio();
    }
}
