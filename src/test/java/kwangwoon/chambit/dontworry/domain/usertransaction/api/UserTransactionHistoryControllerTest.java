package kwangwoon.chambit.dontworry.domain.usertransaction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kwangwoon.chambit.dontworry.domain.usermoney.domain.UserMoneyView;
import kwangwoon.chambit.dontworry.domain.usermoney.repository.UserMoneyViewRepository;
import kwangwoon.chambit.dontworry.domain.usertransaction.TestDataUtil;
import kwangwoon.chambit.dontworry.domain.usertransaction.dto.request.UserTransactionInsertDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserTransactionHistoryControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserMoneyViewRepository userMoneyViewRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void test() throws Exception {



        UserDetails authentication = TestDataUtil.getRealAuthentication();

        UserMoneyView beforeUserMoneyView = userMoneyViewRepository.findByUsername(authentication.getUsername()).get();

        UserTransactionInsertDto userTransactionInsertDto = TestDataUtil.getUserTransactionInsertDto();

        String content = new ObjectMapper().writeValueAsString(userTransactionInsertDto);

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build()
                .perform(
                        post("/api/transaction")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.user(authentication))
                );
        result
                .andDo(print())
                .andExpect(status().isOk());

        em.flush();
        em.clear();

        UserMoneyView afterUserMoneyView = userMoneyViewRepository.findByUsername(authentication.getUsername()).get();

        System.out.println(afterUserMoneyView);
        System.out.println(beforeUserMoneyView);

    }

}