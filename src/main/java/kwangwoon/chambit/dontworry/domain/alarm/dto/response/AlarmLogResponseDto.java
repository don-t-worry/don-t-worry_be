package kwangwoon.chambit.dontworry.domain.alarm.dto.response;

import kwangwoon.chambit.dontworry.domain.alarm.domain.AlarmLog;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmLogResponseDto {
    private String log;
    private String subLog;
    private LocalDateTime date;

    public AlarmLogResponseDto(AlarmLog alarmLog){
        this.log = alarmLog.getLog();
        this.subLog = alarmLog.getAlarmType().getSubLog();
        this.date = alarmLog.getDate();
    }
}
