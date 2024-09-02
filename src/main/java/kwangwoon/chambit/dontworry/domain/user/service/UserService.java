package kwangwoon.chambit.dontworry.domain.user.service;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.HedgeResponseDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.global.security.jwt.dto.TokenDto;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public User signUp(UserSignUpDto userSignUpDto){
        User user = userSignUpDto.toUser();
        return userRepository.save(user);
    }

    public String getToken(String username, String role){
        TokenDto token = jwtUtil.createToken(username, role);
        return token.getAccessToken();
    }

    public List<HedgeResponseDto> getHedgeTypeList(){
        return Arrays.stream(HedgeType.values())
                .map(HedgeResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateName(String updateName, UserDetails principal){
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).get();
        user.setName(updateName);
    }

    @Transactional
    public void updateHedgeType(HedgeType updateHedge, UserDetails principal){
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).get();
        user.setHedgeType(updateHedge);
    }

}
