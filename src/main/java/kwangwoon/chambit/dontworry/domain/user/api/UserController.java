package kwangwoon.chambit.dontworry.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> signUp(UserSignUpDto userSignUpDto){
        userService.signUp(userSignUpDto);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "헷지 타입 리스트 출력")
    @GetMapping("/hedgelist")
    public ResponseEntity<?> getHedgeTypeList(){
        return ResponseEntity.ok(userService.getHedgeTypeList());
    }

    @Operation(summary = "유저의 닉네임 업데이트")
    @PutMapping("/user/update/name")
    public ResponseEntity<?> updateName(String username, @AuthenticationPrincipal UserDetails user){
        userService.updateName(username, user);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "유저의 헷지타입 업데이트")
    @PutMapping("/user/update/hedgetype")
    public ResponseEntity<?> updateHedgeType(HedgeType hedgeType, @AuthenticationPrincipal UserDetails user){
        userService.updateHedgeType(hedgeType, user);
        return ResponseEntity.ok("success");
    }
}
