package kwangwoon.chambit.dontworry.domain.portfolio.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PortfolioDeleteDto {
    private List<Long> portfolioIds;
}
