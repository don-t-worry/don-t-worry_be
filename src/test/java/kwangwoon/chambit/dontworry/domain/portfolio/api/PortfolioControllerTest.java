package kwangwoon.chambit.dontworry.domain.portfolio.api;

import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.HedgeHomeResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.PortfolioEditResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.PortfolioManageResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.dto.PortfolioRecommendDerivativeDto;
import kwangwoon.chambit.dontworry.domain.portfolio.service.PortfolioService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static kwangwoon.chambit.dontworry.domain.portfolio.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PortfolioControllerTest {
    @Autowired
    PortfolioController portfolioController;

    @MockBean
    PortfolioService portfolioService;

    @Test
    public void getHedgeHome_whenValidAuthProvided_thenReturnsSuccessResponse(){

        HedgeHomeResponseDto hedgeHomeResponseDto = getHedgeHomeResponseDto();

        when(portfolioService.getHedgeHome(any())).thenReturn(hedgeHomeResponseDto);

        UserDetails user = getAuthentication();

        ResponseEntity<?> hedgeHome = portfolioController.getHedgeHome(user);

        verify(portfolioService).getHedgeHome(any());

        Assertions.assertThat(hedgeHome).isEqualTo(ResponseEntity.ok(hedgeHomeResponseDto));

    }

    @Test
    public void getManage_whenValidProvided_thenReturnSuccessResponse(){

        PortfolioManageResponseDto portfolioManageDto = getPortfolioManageResponseDto();

        when(portfolioService.getPortfolioManage(any())).thenReturn(portfolioManageDto);

        UserDetails user = getAuthentication();

        ResponseEntity<?> portfolioManage = portfolioController.getPortfolioManage(user);

        verify(portfolioService).getPortfolioManage(any());

        Assertions.assertThat(portfolioManage).isEqualTo(ResponseEntity.ok(portfolioManageDto));
    }



    @Test
    public void getEdit_whenValidProvided_thenReturnSuccessResponse(){

        List<PortfolioEditResponseDto> portfolioEdit = getPortfolioEditResponseDtos();

        when(portfolioService.getPortfolioEdit((any()))).thenReturn(portfolioEdit);

        UserDetails user = getAuthentication();

        ResponseEntity<?> editList = portfolioController.getEditList(user);

        verify(portfolioService).getPortfolioEdit(any());

        Assertions.assertThat(editList).isEqualTo(ResponseEntity.ok(portfolioEdit));
    }

    @Test
    public void getPageDerivative_whenValidProvided_thenReturnSuccessResponse(){

        List<PortfolioRecommendDerivativeDto> result = getPageDerivative();

        when(portfolioService.getAllPortfolioRecommendDerivative(any())).thenReturn(result);

        UserDetails user = getAuthentication();

        ResponseEntity<?> recommend = portfolioController.getRecommend(user);

        verify(portfolioService).getAllPortfolioRecommendDerivative(any());

        Assertions.assertThat(recommend).isEqualTo(ResponseEntity.ok(result));

    }



//    @Test
//    public void getPageDerivative_whenValidProvided_thenReturnSuccessResponse(){
//
//        Result result = getPageDerivative();
//
//        when(portfolioService.getAllPortfolioRecommendDerivative(eq(result.pageRequest()),any())).thenReturn(result.dtos());
//
//        UserDetails user = getAuthentication();
//
//        ResponseEntity<?> recommend = portfolioController.getRecommend(0,user);
//
//        verify(portfolioService).getAllPortfolioRecommendDerivative(eq(result.pageRequest()),any());
//
//        Assertions.assertThat(recommend).isEqualTo(ResponseEntity.ok(result.dtos()));
//
//    }

}