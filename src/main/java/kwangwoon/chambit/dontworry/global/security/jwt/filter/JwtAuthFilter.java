package kwangwoon.chambit.dontworry.global.security.jwt.filter;

import io.jsonwebtoken.JwtException;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.global.infra.redis.RefreshToken;
import kwangwoon.chambit.dontworry.global.infra.redis.RefreshTokenService;
import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
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
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IllegalArgumentException {
        String accessTokenHeader = request.getHeader("Authorization");

        if(!StringUtils.hasText(accessTokenHeader)){
            doFilter(request, response, filterChain);
            return;
        }
        // 토큰이 없는 부분을 처리하는 로직

        if(request.getRequestURI().equals("/refresh")){
            doFilter(request, response, filterChain);
            return;
        }
        //refresh를 처리하는 로직

        String accessToken = accessTokenHeader.split(" ")[1];
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(accessToken);

        if(!jwtUtil.validateToken(accessToken)){
            if(jwtUtil.validateToken(refreshToken.getRefreshToken())){
                throw new JwtException("Access Token 만료");
            }else{
                throw new JwtException("Refresh Token 만료");
            }
        }
        if(jwtUtil.validateToken(accessToken)){
            if(refreshToken.getIsLogout().equals("true")){
                throw new JwtException("Log out 유저");
            }
            Authentication authentication = jwtUtil.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);

    }
}
