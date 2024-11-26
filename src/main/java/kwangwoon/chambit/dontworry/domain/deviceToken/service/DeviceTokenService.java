package kwangwoon.chambit.dontworry.domain.deviceToken.service;

import kwangwoon.chambit.dontworry.domain.deviceToken.domain.DeviceToken;
import kwangwoon.chambit.dontworry.domain.deviceToken.dto.ExamDto;
import kwangwoon.chambit.dontworry.domain.deviceToken.repository.DeviceTokenRepository;
import kwangwoon.chambit.dontworry.domain.fcm.service.FcmService;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;
    private final FcmService fcmService;

    public void saveToken(User user, String token, String deviceId){
        DeviceToken deviceToken = DeviceToken.builder()
                .user(user)
                .token(token)
                .deviceId(deviceId)
                .build();
        deviceTokenRepository.save(deviceToken);
    }

    @Transactional
    public void updateToken(User user, String token, String deviceId){

        DeviceToken deviceToken = deviceTokenRepository.findByDeviceIdAndUser(user, deviceId).get();
        System.out.println(deviceToken);
        deviceToken.setToken(token);
        System.out.println(deviceToken);
    }

    //파생상품 마감
    @Scheduled(cron = "0 0 6 ? * 5#2", zone = "Asia/Seoul")
    public void expire() throws IOException {
        int currentMonth = LocalDate.now().getMonthValue();
        String title = "[" + currentMonth + "]" + "내일 파생상품이 만기됩니다.";
        String body = "시세차익과 헷지의 결과를 확인해 보세요!";

        List<DeviceToken> deviceTokenExpireOn = deviceTokenRepository.findDeviceTokenExpireOn();
        for(DeviceToken token : deviceTokenExpireOn){
            fcmService.sendMessageTo(token.getToken(), title,body);
        }
    }

    public ExamDto sendExamMessage(ExamDto examDto) throws IOException {
        fcmService.sendMessageTo(examDto.getToken(),examDto.getTitle(),examDto.getBody());
        return examDto;
    }

    //시세차익 포착

}
