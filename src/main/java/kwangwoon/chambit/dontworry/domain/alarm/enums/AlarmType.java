package kwangwoon.chambit.dontworry.domain.alarm.enums;

import lombok.Getter;

@Getter
public enum AlarmType {
    ARBITRAGE("거래내역을 확인해 보세요!","시세차익 포착 알림"), EXPIRE("시세차익과 헷지의 결과를 확인해 보세요!","파생상품 마감 알림");

    private final String subLog;
    private final String settingLog;

    AlarmType(String subLog, String settingLog){
        this.subLog = subLog;
        this.settingLog = settingLog;
    }
}
