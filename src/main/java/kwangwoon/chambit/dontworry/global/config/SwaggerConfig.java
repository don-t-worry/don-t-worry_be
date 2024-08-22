package kwangwoon.chambit.dontworry.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        Server server = new Server();
        server.setDescription("배포 서버 전용");
        server.setUrl("https://api.don-t-worry.com");

        Server localhost = new Server();
        localhost.setDescription("백엔드 개발시 사용");
        localhost.setUrl("http://localhost:8080");

        OpenAPI openAPI =  new OpenAPI()
                .info(new Info()
                        .title("돈워리 프로젝트 API")
                        .description("서버 api 제공")
                        .version("1.0.0"));

        openAPI.setServers(Arrays.asList(server, localhost));

        return openAPI;
    }
}
