package kwangwoon.chambit.dontworry.domain.servicemoney.repository;

import kwangwoon.chambit.dontworry.domain.servicemoney.domain.ServiceMoneyView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ServiceMoneyViewRepositoryTest {

    @Autowired
    ServiceMoneyViewRepository serviceMoneyViewRepository;

    @Test
    public void test(){
        ServiceMoneyView serviceMoney = serviceMoneyViewRepository.findServiceMoney();
        System.out.println(serviceMoney);
    }

}