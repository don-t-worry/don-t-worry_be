package kwangwoon.chambit.dontworry.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 성공시 바로 토큰 정보 헤더에 담아서 return함")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpDto userSignUpDto){
        User user = userService.signUp(userSignUpDto);

        String token = userService.getToken(user.getUsername(), user.getRole());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,token);

        Map<String,String> body = new HashMap<>();
        body.put("name",user.getName());

        return ResponseEntity.ok().headers(headers).body(body);
    }

    @Operation(summary = "헷지 타입 리스트 출력")
    @GetMapping("/hedgelist")
    public ResponseEntity<?> getHedgeTypeList(){
        return ResponseEntity.ok(userService.getHedgeTypeList());
    }

    @Operation(summary = "유저의 닉네임 업데이트", description = "토큰 정보 필요함")
    @PutMapping("/user/update/name")
    public ResponseEntity<?> updateName(@RequestBody String updateName, @AuthenticationPrincipal UserDetails user){
        userService.updateName(updateName, user);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "유저의 헷지타입 업데이트", description = "토큰 정보 필요함")
    @PutMapping("/user/update/hedgetype")
    public ResponseEntity<?> updateHedgeType(@RequestBody HedgeType hedgeType, @AuthenticationPrincipal UserDetails user){
        userService.updateHedgeType(hedgeType, user);
        return ResponseEntity.ok("success");
    }
}
