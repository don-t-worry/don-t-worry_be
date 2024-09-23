package kwangwoon.chambit.dontworry.domain.alarm.repository;

import kwangwoon.chambit.dontworry.domain.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {
    @Query("select a from Alarm a join fetch a.user u where u.username=:username")
    List<Alarm> findByUsername(@Param("username") String username);
}
