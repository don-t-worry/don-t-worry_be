package kwangwoon.chambit.dontworry.domain.deviceToken.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "token", "deviceId"})
public class DeviceToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_token_id")
    private Long id;

    @Setter
    private String token;
    @Setter
    private String deviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public DeviceToken(User user, String token, String deviceId){
        this.user = user;
        this.token = token;
        this.deviceId = deviceId;
    }
}
