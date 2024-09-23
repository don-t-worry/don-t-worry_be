package kwangwoon.chambit.dontworry.domain.alarm.dto.response;

import kwangwoon.chambit.dontworry.domain.alarm.domain.Alarm;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmStatus;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType;
import lombok.Data;

@Data
public class AlarmResponseDto {

    private Long id;
    private AlarmType alarmType;
    private AlarmStatus alarmStatus;

    public AlarmResponseDto(Alarm alarm){
        this.id = alarm.getId();
        this.alarmType = alarm.getAlarmType();
        this.alarmStatus = alarm.getAlarmStatus();
    }

}
