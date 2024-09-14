package kwangwoon.chambit.dontworry.domain.user.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static kwangwoon.chambit.dontworry.domain.portfolio.TestDataUtil.getAuthentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UserTest {
    @Autowired
    private WebApplicationContext context;

    @Test
    public void getLogout() throws Exception {

        UserDetails user = getAuthentication();

        ResultActions result = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build()
                .perform(
                        get("/logout")
                                .with(SecurityMockMvcRequestPostProcessors.user(user))
                                .header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWthbzM2NDgxODg3NTciLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI2MzE2NjA1LCJleHAiOjE3MjYzMjAyMDV9.cT-wmDb_HR78wyRvFv71oJ59EmcO1FF93KwAWJVfOd8")
                );
        result
                .andDo(print())
                .andExpect(status().is3xxRedirection());

    }


}
