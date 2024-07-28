package kwangwoon.chambit.dontworry.domain.user.domain;

import jakarta.persistence.*;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id" , "name"})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private HedgeType hedgeType;
}
