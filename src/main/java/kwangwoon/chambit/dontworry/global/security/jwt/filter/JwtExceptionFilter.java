package kwangwoon.chambit.dontworry.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kwangwoon.chambit.dontworry.global.common.dto.ErrorResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (JwtException e){

            int status = 0;
            if(e.getMessage().contains("Access")){
                status = 401;
            }else{
                status = 402;
            }

            response.setStatus(status);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), ErrorResponse.builder().status(status).value(e.getMessage()).build());
            // json으로 변환하여 출력대상에 쓰임
            // 첫번째 인자는 출력대상 목적지, 두번째는 json으로 변환되는 객체
            // 여기서는 http의 목적지, 이때 목적지에 값들을 바꾼게 위의 설정들임
        } catch (IllegalArgumentException e){
            response.setStatus(400);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), ErrorResponse.builder().status(400).value(e.getMessage()).build());
        }
    }
}
