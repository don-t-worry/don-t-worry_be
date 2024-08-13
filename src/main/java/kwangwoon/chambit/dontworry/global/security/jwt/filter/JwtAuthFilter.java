package kwangwoon.chambit.dontworry.global.security.jwt.filter;

import io.jsonwebtoken.JwtException;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        if(!StringUtils.hasText(accessToken)){
            doFilter(request, response, filterChain);
            return;
        }

        if(!jwtUtil.validateToken(accessToken)){
            throw new JwtException("Access Token 만료");
        }
        if(jwtUtil.validateToken(accessToken)){
            Authentication authentication = jwtUtil.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);

    }
}
