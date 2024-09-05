package kwangwoon.chambit.dontworry.domain.usertrade.repository;

import kwangwoon.chambit.dontworry.domain.usertrade.domain.UserTradeHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTradeHistoryRepositoryTest {
    @Autowired
    UserTradeHistoryRepository userTradeHistoryRepository;

    @Test
    public void test(){
        PageRequest pr = PageRequest.of(0,10, Sort.by("time"));

        Page<UserTradeHistory> juhoho = userTradeHistoryRepository.findByUsername("juhoho", pr);
        System.out.println(juhoho);
    }

}