package kwangwoon.chambit.dontworry.domain.monthlyEarnings.dto;

import kwangwoon.chambit.dontworry.domain.monthlyEarnings.domain.MonthlyEarnings;
import lombok.Builder;
import lombok.Data;

@Data
public class MonthlyEarningsResponseDto {
    String monthDate;
    Double earningsRate;
    Long investmentMoney;
    Long earnings;

    @Builder
    public MonthlyEarningsResponseDto(MonthlyEarnings monthlyEarnings){
        this.monthDate = monthlyEarnings.getMonthDate();
        this.earningsRate = monthlyEarnings.getEarningsRate();
        this.investmentMoney = monthlyEarnings.getInvestmentMoney();
        this.earnings = monthlyEarnings.getEarnings();
    }
}
