package kwangwoon.chambit.dontworry.domain.deviceToken.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExamDto {
    private String token;
    private String title;
    private String body;
}
