package kwangwoon.chambit.dontworry.domain.user.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "name"})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Setter
    private String name;
    private String username;
    private String role;

    @Setter
    @Enumerated(EnumType.STRING)
    private HedgeType hedgeType;

    @Builder
    public User(String name, String username, HedgeType hedgeType, String role){
        this.name = name;
        this.username = username;
        this.hedgeType = hedgeType;
        this.role = role;
    }

}
