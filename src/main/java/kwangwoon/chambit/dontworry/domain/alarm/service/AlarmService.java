package kwangwoon.chambit.dontworry.domain.alarm.service;

import kwangwoon.chambit.dontworry.domain.alarm.domain.Alarm;
import kwangwoon.chambit.dontworry.domain.alarm.dto.request.AlarmUpdateDto;
import kwangwoon.chambit.dontworry.domain.alarm.dto.response.AlarmLogResponseDto;
import kwangwoon.chambit.dontworry.domain.alarm.dto.response.AlarmResponseDto;
import kwangwoon.chambit.dontworry.domain.alarm.repository.AlarmLogRepository;
import kwangwoon.chambit.dontworry.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class AlarmService {
    private final AlarmLogRepository alarmLogRepository;
    private final AlarmRepository alarmRepository;

    public Page<AlarmLogResponseDto> getAlarmLog(Pageable pageable, UserDetails user){
        String username = user.getUsername();

        return alarmLogRepository.findByUsername(username, pageable)
                .map(AlarmLogResponseDto::new);
    }

    public List<AlarmResponseDto> getAlarm(UserDetails user){
        String username = user.getUsername();

        return alarmRepository.findByUsername(username).stream()
                .map(AlarmResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateAlarm(Long id, AlarmUpdateDto alarmUpdateDto){
        Alarm alarm = alarmRepository.findById(id).get();
        alarm.setAlarmStatus(alarmUpdateDto.getAlarmStatus());
    }


}
