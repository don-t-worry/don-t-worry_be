package kwangwoon.chambit.dontworry.global.security.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
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
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static kwangwoon.chambit.dontworry.global.config.DomainConfig.*;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2ClientDto oauth2Client = (CustomOauth2ClientDto) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = oauth2Client.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String username = oauth2Client.getUsername();

        response.setStatus(201);
        String referer = request.getHeader("Referer"); // Referer 헤더 값 가져오기
        String refererHost = "";
        if (referer != null) {
            try {
                // URL에서 호스트와 포트만 추출
                URL refererUrl = new URL(referer);
                refererHost = refererUrl.getProtocol() + "://" + refererUrl.getHost();
                if (refererUrl.getPort() != -1) {
                    refererHost += ":" + refererUrl.getPort(); // 포트가 존재하면 추가
                }
                refererHost += "/";
            } catch (MalformedURLException e) {
                System.err.println("Invalid Referer URL: " + referer);
            }
        }

        System.out.println("Processed Referer: " + refererHost);

        if(refererHost.isEmpty() || refererHost.equals(LocalHttp.getAddress())){
            response.sendRedirect(LocalHttp.getAddress() + "sign-in?username="+username);
        } else if(refererHost.equals(FrontServer.getAddress())){
            response.sendRedirect(FrontServer.getAddress() + "sign-in?username="+username);
        } else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown state");
        }
    }
}
