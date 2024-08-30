package kwangwoon.chambit.dontworry.global.security.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.global.config.DomainConfig;
import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static kwangwoon.chambit.dontworry.global.config.DomainConfig.FrontServer;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2ClientDto oauth2Client = (CustomOauth2ClientDto) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = oauth2Client.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String username = oauth2Client.getUsername();

        if(oauth2Client.isExist()){
            TokenDto token = jwtUtil.createToken(username, role);
            response.addHeader(HttpHeaders.AUTHORIZATION, token.getAccessToken());
            response.sendRedirect(FrontServer.getPresentAddress() + "/hedge/home");
        }else{
            response.sendRedirect(FrontServer.getPresentAddress() + "/signup/name?username="+username);
        }
    }
}
