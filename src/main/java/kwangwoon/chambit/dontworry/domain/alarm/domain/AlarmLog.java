package kwangwoon.chambit.dontworry.domain.alarm.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@ToString(of = {"id","alarmStatus","alarmType"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmLog {

    @Id
    @Column(name = "alarm_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String log;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
