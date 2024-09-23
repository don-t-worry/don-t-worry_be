package kwangwoon.chambit.dontworry.global.security.oauth.service;

import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.KakaoResponse;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2ClientDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.Oauth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2ClientService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Oauth2Response oauth2Response = null;

        if(registrationId.equals("kakao")){
            oauth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        String username = oauth2Response.getProvider() + "" + oauth2Response.getId();

        Oauth2ClientDto oauth2ClientDto = new Oauth2ClientDto(username,"",true);

        return new CustomOauth2ClientDto(oauth2ClientDto);
    }

//    private Oauth2ClientDto getOauthClientDto(String username) {
//        return userRepository.findByUsername(username)
//                .map(client -> Oauth2ClientDto.builder()
//                        .role(client.getRole())
//                        .username(client.getUsername())
//                        .isExist(true)
//                        .build()
//                )
//                .orElseGet(() -> Oauth2ClientDto.builder()
//                        .username(username)
//                        .isExist(false)
//                        .role("ROLE_USER")
//                        .build()
//                );
//    }
}
