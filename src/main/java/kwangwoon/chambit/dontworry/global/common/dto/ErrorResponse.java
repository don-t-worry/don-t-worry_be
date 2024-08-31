package kwangwoon.chambit.dontworry.global.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String value;

}
