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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static kwangwoon.chambit.dontworry.global.config.DomainConfig.FrontServer;

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


        Map<String,String> body = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if(oauth2Client.isExist()){
            TokenDto token = jwtUtil.createToken(username, role);
            response.addHeader(HttpHeaders.AUTHORIZATION, token.getAccessToken());

            User user = userRepository.findByUsername(username).get();

            body.put("name",user.getName());
            String jsonBody = objectMapper.writeValueAsString(body);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.write(jsonBody);

            response.setStatus(200);
//            response.sendRedirect(FrontServer.getPresentAddress() + "/hedge/home");
        }else{
            body.put("username",username);
            String jsonBody = objectMapper.writeValueAsString(body);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(jsonBody);

            response.setStatus(201);
//            response.sendRedirect(FrontServer.getPresentAddress() + "/signup/name?username="+username);
        }
    }
}
