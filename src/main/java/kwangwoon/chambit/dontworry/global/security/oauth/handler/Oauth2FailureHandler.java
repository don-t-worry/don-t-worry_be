package kwangwoon.chambit.dontworry.global.security.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.global.config.DomainConfig;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static kwangwoon.chambit.dontworry.global.config.DomainConfig.FrontServer;
import static kwangwoon.chambit.dontworry.global.config.DomainConfig.LocalHttp;

@Component
public class Oauth2FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

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
            response.sendRedirect(LocalHttp.getAddress());
        } else if(refererHost.equals(FrontServer.getAddress())){
            response.sendRedirect(FrontServer.getAddress());
        } else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown state");
        }
    }
}
