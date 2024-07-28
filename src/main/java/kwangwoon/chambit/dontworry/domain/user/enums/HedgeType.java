package kwangwoon.chambit.dontworry.domain.user.enums;

import lombok.Getter;

@Getter
public enum HedgeType {
    MINIMAL(0,20), BALANCED(20,40), STABLE(40,60);
    private final int upperHedgeRatio;
    private final int lowerHedgeRatio;

    HedgeType(int lowerHedgeRatio, int upperHedgeRatio){
        this.lowerHedgeRatio = lowerHedgeRatio;
        this.upperHedgeRatio = upperHedgeRatio;
    }
}
