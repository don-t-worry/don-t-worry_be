package kwangwoon.chambit.dontworry.domain.user.enums;

import lombok.Getter;

@Getter
public enum HedgeType {
    MINIMAL(0,20,"최소 리스크 관리"), BALANCED(20,40, "균형 리스크 관리"), STABLE(40,60, "안정 리스크 관리");
    private final int upperHedgeRatio;
    private final int lowerHedgeRatio;
    private final String korean;

    HedgeType(int lowerHedgeRatio, int upperHedgeRatio,String korean){
        this.lowerHedgeRatio = lowerHedgeRatio;
        this.upperHedgeRatio = upperHedgeRatio;
        this.korean = korean;
    }
}
