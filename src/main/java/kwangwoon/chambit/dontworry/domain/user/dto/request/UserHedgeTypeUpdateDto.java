package kwangwoon.chambit.dontworry.domain.user.dto.request;

import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserHedgeTypeUpdateDto {
    HedgeType hedgeType;
}
