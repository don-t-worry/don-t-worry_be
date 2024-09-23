package kwangwoon.chambit.dontworry.domain.alarm.dto.request;

import kwangwoon.chambit.dontworry.domain.alarm.domain.Alarm;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmStatus;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AlarmUpdateDto {
    private AlarmType alarmType;
    private AlarmStatus alarmStatus;
}
