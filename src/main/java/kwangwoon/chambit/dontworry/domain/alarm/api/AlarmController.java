package kwangwoon.chambit.dontworry.domain.alarm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.alarm.dto.request.AlarmUpdateDto;
import kwangwoon.chambit.dontworry.domain.alarm.dto.response.AlarmLogResponseDto;
import kwangwoon.chambit.dontworry.domain.alarm.dto.response.AlarmResponseDto;
import kwangwoon.chambit.dontworry.domain.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "알람 api")
@RequestMapping("/api/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/logs")
    @Operation(summary = "유저의 알람 로그 기록 조회", description = "무한 스크롤을 위한 페이지 입력 필요, 헤더 필요")
    public ResponseEntity<?> getAlarmLog(@RequestParam("page") int page, @AuthenticationPrincipal UserDetails user){
        PageRequest pr = PageRequest.of(page,10, Sort.by("date").descending());
        Page<AlarmLogResponseDto> alarmLog = alarmService.getAlarmLog(pr, user);

        return ResponseEntity.ok(alarmLog);
    }

    @GetMapping()
    @Operation(summary = "유저의 알람 상태 조회", description = "헤더 필요")
    public ResponseEntity<?> getAlarmLog(@AuthenticationPrincipal UserDetails user){
        List<AlarmResponseDto> alarm = alarmService.getAlarm(user);

        return ResponseEntity.ok(alarm);
    }

    @PutMapping("/{alarm_id}")
    @Operation(summary = "유저의 알람 상태 변경")
    public ResponseEntity<?> updateAlarm(@PathVariable("alarm_id") Long id, @RequestBody AlarmUpdateDto alarmUpdateDto){
        alarmService.updateAlarm(id, alarmUpdateDto);

        return ResponseEntity.ok("success");
    }
}
