package kwangwoon.chambit.dontworry.global.security.oauth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOauth2ClientDto implements OAuth2User {

    private final Oauth2ClientDto oauth2ClientDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return oauth2ClientDto.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oauth2ClientDto.getOauthName();
    }

    public String getUsername(){
        return oauth2ClientDto.getUsername();
    }

    public Boolean isExist(){
        return oauth2ClientDto.getIsExist();
    }
}
