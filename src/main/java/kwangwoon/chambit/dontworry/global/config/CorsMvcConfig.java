package kwangwoon.chambit.dontworry.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static kwangwoon.chambit.dontworry.global.config.DomainConfig.FrontServer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer { // 모든 요청이 컨트롤러에 도달하기 전에 적용됩니다
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("Authorization")
                .allowedOrigins(FrontServer.getPresentAddress());
    }
}
