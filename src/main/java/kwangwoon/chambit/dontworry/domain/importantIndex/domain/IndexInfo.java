package kwangwoon.chambit.dontworry.domain.importantIndex.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kwangwoon.chambit.dontworry.domain.importantIndex.enums.ChangeStatus;
import lombok.*;
import org.springframework.data.annotation.Id;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public abstract class IndexInfo {
    @Id
    private String code;
    @JsonIgnore
    private String symbolCode;
    private String name;
    private ChangeStatus change;
    private Double changePrice;
    private Double changeRate;
    private Double tradePrice;
}
