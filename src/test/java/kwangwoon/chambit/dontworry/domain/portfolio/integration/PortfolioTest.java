package kwangwoon.chambit.dontworry.domain.portfolio.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kwangwoon.chambit.dontworry.domain.derivative.domain.Derivative;
import kwangwoon.chambit.dontworry.domain.derivative.repository.DerivativeRepository;
import kwangwoon.chambit.dontworry.domain.portfolio.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.domain.portfolio.dto.response.HedgeHomeResponseDto;
import kwangwoon.chambit.dontworry.domain.portfolio.repository.PortfolioRepository;
import kwangwoon.chambit.dontworry.domain.portfolio.service.PortfolioService;
import kwangwoon.chambit.dontworry.domain.stock.domain.Stock;
import kwangwoon.chambit.dontworry.domain.stock.repository.StockRepository;
import kwangwoon.chambit.dontworry.global.config.SecurityConfig;
import kwangwoon.chambit.dontworry.global.security.oauth.service.Oauth2ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static kwangwoon.chambit.dontworry.domain.portfolio.TestDataUtil.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class PortfolioTest {

    @Autowired
    private WebApplicationContext context;


    @Test
    public void getHedgeHome_whenValidAuthProvided_thenReturnsSuccessResponse() throws Exception {

        UserDetails user = getAuthentication();

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build()
                .perform(
                        get("/api/portfolio/hedgehome")
                                .with(SecurityMockMvcRequestPostProcessors.user(user))
                );
        result
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getEdit_whenValidProvided_thenReturnSuccessResponse() throws Exception {
        UserDetails user = getAuthentication();

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build()
                .perform(
                        get("/api/portfolio/edit")
                                .with(SecurityMockMvcRequestPostProcessors.user(user))
                );
        result
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getManage_whenValidProvided_thenReturnSuccessResponse() throws Exception {
        UserDetails user = getAuthentication();

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build()
                .perform(
                        get("/api/portfolio/manage")
                                .with(SecurityMockMvcRequestPostProcessors.user(user))
                );

        result
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getPageDerivative_whenValidProvided_thenReturnSuccessResponse() throws Exception {
        UserDetails user = getAuthentication();

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build()
                .perform(
                        get("/api/portfolio/hedgeall")
                                .with(SecurityMockMvcRequestPostProcessors.user(user))
//                                .param("page","0")
                );

        result
                .andDo(print())
                .andExpect(status().isOk());
    }

}







