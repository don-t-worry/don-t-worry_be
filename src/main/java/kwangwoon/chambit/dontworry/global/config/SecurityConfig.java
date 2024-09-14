package kwangwoon.chambit.dontworry.global.config;

import kwangwoon.chambit.dontworry.global.infra.redis.RefreshToken;
import kwangwoon.chambit.dontworry.global.infra.redis.RefreshTokenService;
import kwangwoon.chambit.dontworry.global.security.jwt.filter.JwtAuthFilter;
import kwangwoon.chambit.dontworry.global.security.jwt.filter.JwtExceptionFilter;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import kwangwoon.chambit.dontworry.global.security.oauth.handler.Oauth2FailureHandler;
import kwangwoon.chambit.dontworry.global.security.oauth.handler.Oauth2SuccessHandler;
import kwangwoon.chambit.dontworry.global.security.oauth.service.Oauth2ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import static kwangwoon.chambit.dontworry.global.config.DomainConfig.FrontServer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Oauth2ClientService oauth2ClientService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2FailureHandler oauth2FailureHandler;
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable); // non browser clients(웹이 아닌) 서비스라면 비활성화 해도 됨, 우리는 rest api서버임
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable); // 이 설정 없으면 기본 로그인 창으로 이동함

        http.
                sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //세션을 만들지 않겠다

        http.
                oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(oauth2ClientService))
                        .failureHandler(oauth2FailureHandler)
                        .successHandler(oauth2SuccessHandler)
                );

        http.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.addLogoutHandler((request, response, authentication) ->{
                String accessTokenHeader = request.getHeader("Authorization");
                String accessToken = accessTokenHeader.split(" ")[1];

                refreshTokenService.setLogout(accessToken);

                SecurityContextHolder.clearContext();
            });
            logout.logoutSuccessHandler((request, response, authentication) ->{
                response.sendRedirect(FrontServer.getPresentAddress());
            });
        });

        http.
                authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/refresh","/login", "/", "/signup","/swagger-ui/**" ,"/v3/api-docs/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/hello").hasRole("USER")
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
                );

        http
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,JwtAuthFilter.class);

        http // 요청이 보안 필터 체인을 통과할 때 적용됩니다.
                .cors((corsCustomizer -> corsCustomizer.configurationSource(request -> {

                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(List.of(FrontServer.getPresentAddress()));
                    configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 요청 허용
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);
                    configuration.setExposedHeaders(Collections.singletonList("Authorization")); // 우리 쪽에서 데이터를 주는 경우 설정
                    return configuration;
                })));

        return http.build();
    }
}
