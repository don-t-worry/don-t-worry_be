package kwangwoon.chambit.dontworry.domain.user.service;

import kwangwoon.chambit.dontworry.domain.alarm.domain.Alarm;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmStatus;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType;
import kwangwoon.chambit.dontworry.domain.alarm.repository.AlarmRepository;
import kwangwoon.chambit.dontworry.domain.deviceToken.service.DeviceTokenService;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UsernameExistDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.HedgeResponseDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.UsernameExistResponseDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.UsernameNotExistResponseDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.UsernameResponseDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.infra.redis.refreshToken.RefreshToken;
import kwangwoon.chambit.dontworry.global.infra.redis.refreshToken.RefreshTokenService;
import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    private final AlarmRepository alarmRepository;

    private final DeviceTokenService deviceTokenService;

    @Transactional
    public User signUp(UserSignUpDto userSignUpDto){
        User user = userSignUpDto.toUser();
        User savedUser = userRepository.save(user);

        saveAlarm(savedUser);

        deviceTokenService.saveToken(savedUser,userSignUpDto.getToken(), userSignUpDto.getDeviceId());

        return savedUser;
    }

    private void saveAlarm(User savedUser) {
        Alarm alarm1 = new Alarm(savedUser);
        alarm1.setAlarmStatus(AlarmStatus.ON);
        alarm1.setAlarmType(AlarmType.ARBITRAGE);

        Alarm alarm2 = new Alarm(savedUser);
        alarm2.setAlarmStatus(AlarmStatus.ON);
        alarm2.setAlarmType(AlarmType.EXPIRE);

        alarmRepository.save(alarm1);
        alarmRepository.save(alarm2);
    }

    @Transactional
    public UsernameResponseDto existUsername(UsernameExistDto usernameExistDto){
        Optional<User> optionalUser = userRepository.findByUsername(usernameExistDto.getUsername());

        if(optionalUser.isEmpty()){
            return new UsernameNotExistResponseDto(usernameExistDto.getUsername());
        }else{
            User user = optionalUser.get();

            deviceTokenService.updateToken(user, usernameExistDto.getToken(), usernameExistDto.getDeviceId());

            TokenDto token = jwtUtil.createToken(user.getUsername(), user.getRole());
            return new UsernameExistResponseDto(user.getName(), token.getAccessToken());
        }
    }

    public String getToken(String username, String role){
        TokenDto token = jwtUtil.createToken(username, role);
        return token.getAccessToken();
    }

    public List<HedgeResponseDto> getHedgeTypeList(){
        return Arrays.stream(HedgeType.values())
                .map(HedgeResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateName(String updateName, UserDetails principal){
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).get();
        user.setName(updateName);
    }

    @Transactional
    public void updateHedgeType(HedgeType updateHedge, UserDetails principal){
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).get();
        user.setHedgeType(updateHedge);
    }

    @Transactional
    public void deletionUser(UserDetails user){
        String username = user.getUsername();
        userRepository.deleteByUsername(username);

        RefreshToken refreshToken = refreshTokenService.findByUsername(username);
        refreshTokenService.setLogout(refreshToken.getAccessToken());
    }

}
