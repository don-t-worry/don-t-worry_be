package kwangwoon.chambit.dontworry.domain.alarm.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmStatus;
import kwangwoon.chambit.dontworry.domain.alarm.enums.AlarmType;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@ToString(of = {"id","alarmStatus","alarmType"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {
    @Id
    @Column(name = "alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    @Setter
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Alarm(User user){
        this.user = user;
    }

}
