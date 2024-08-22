package kwangwoon.chambit.dontworry.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.service.UserService;
import kwangwoon.chambit.dontworry.global.common.dto.ResponseDto;
import kwangwoon.chambit.dontworry.global.security.oauth.dto.CustomOauth2ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseDto<?> signUp(UserSignUpDto userSignUpDto){
        userService.signUp(userSignUpDto);
        return ResponseDto.success("success");
    }

    @Operation(summary = "헷지 타입 리스트 출력")
    @GetMapping("/hedgelist")
    public ResponseDto<?> getHedgeTypeList(){
        return ResponseDto.success(userService.getHedgeTypeList());
    }

    @Operation(summary = "유저의 닉네임 업데이트")
    @PutMapping("/user/update/name")
    public ResponseDto<?> updateName(String username, @AuthenticationPrincipal Authentication authentication){
        userService.updateName(username, authentication);
        return ResponseDto.success("success");
    }

    @Operation(summary = "유저의 헷지타입 업데이트")
    @PutMapping("/user/update/hedgetype")
    public ResponseDto<?> updateHedgeType(HedgeType hedgeType, @AuthenticationPrincipal Authentication authentication){
        userService.updateHedgeType(hedgeType, authentication);
        return ResponseDto.success("success");
    }
}
