package kwangwoon.chambit.dontworry.domain.alarm.repository;

import kwangwoon.chambit.dontworry.domain.alarm.domain.AlarmLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmLogRepository extends JpaRepository<AlarmLog,Long> {
    @Query("select l from AlarmLog l join fetch l.user u where u.username=:username")
    Page<AlarmLog> findByUsername(@Param("username") String username, Pageable pageable);
}
