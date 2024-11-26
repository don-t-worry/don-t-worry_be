package kwangwoon.chambit.dontworry.domain.deviceToken.repository;

import kwangwoon.chambit.dontworry.domain.deviceToken.domain.DeviceToken;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    //시세차익 알람 on
    @Query("select d from User u join fetch Alarm a join fetch DeviceToken d " +
            "where a.alarmType = kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType.ARBITRAGE " +
            "and a.alarmStatus = kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmStatus.ON")
    List<DeviceToken> findDeviceTokenArbitrageOn();

    //파생상품 마감 알람 on
    @Query("select d from User u join fetch Alarm a join fetch DeviceToken d " +
            "where a.alarmType = kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType.EXPIRE " +
            "and a.alarmStatus = kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmStatus.ON")
    List<DeviceToken> findDeviceTokenExpireOn();

    @Query("select d from DeviceToken d where d.user=:user and d.deviceId=:deviceId")
    Optional<DeviceToken> findByDeviceIdAndUser(@Param("user")User user, @Param("deviceId")String deviceId);
}
