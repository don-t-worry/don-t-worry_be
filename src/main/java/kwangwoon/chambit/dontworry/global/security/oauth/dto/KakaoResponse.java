package kwangwoon.chambit.dontworry.global.security.oauth.dto;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;


public class KakaoResponse implements Oauth2Response{

    private final Map<String, Object> attribute;
    private final Map<String, Object> account;

    public KakaoResponse(Map<String,Object> attribute){
        this.attribute = attribute;
        this.account = (Map<String, Object>) attribute.get("kakao_account");
        for(var s : attribute.keySet()){
            System.out.println( s + " " + attribute.get(s));
        }
    }
    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getId() {
        return String.valueOf(attribute.get("id"));
    }

    @Override
    public String getName() {
        Map<String,Object> profile = (Map<String, Object>) account.get("profile");
        return (String) profile.get("nickname");
    }
}
