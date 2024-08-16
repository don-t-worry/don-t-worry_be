package kwangwoon.chambit.dontworry.global.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public String getHealthCheck(){
        return "hi";
    }
}
