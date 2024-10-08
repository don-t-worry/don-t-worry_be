package kwangwoon.chambit.dontworry.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserHedgeTypeUpdateDto;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserNameUpdateDto;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UserSignUpDto;
import kwangwoon.chambit.dontworry.domain.user.dto.request.UsernameExistDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.UsernameExistResponseDto;
import kwangwoon.chambit.dontworry.domain.user.dto.response.UsernameResponseDto;
import kwangwoon.chambit.dontworry.domain.user.enums.HedgeType;
import kwangwoon.chambit.dontworry.domain.user.service.UserService;
import kwangwoon.chambit.dontworry.global.security.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/user/exists")
    @Operation(summary = "", description = "")
    public ResponseEntity<?> existUsername(@RequestBody UsernameExistDto usernameExistDto){
        UsernameResponseDto response = userService.existUsername(usernameExistDto);

        if(response instanceof UsernameExistResponseDto){
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION,((UsernameExistResponseDto) response).getToken());

            return new ResponseEntity<>(response,headers, HttpStatus.ACCEPTED); //202
        }else{
            return new ResponseEntity<>(response,HttpStatus.NON_AUTHORITATIVE_INFORMATION); //203
        }
    }

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
    @PutMapping("/user/name")
    public ResponseEntity<?> updateName(@RequestBody UserNameUpdateDto userNameUpdateDto, @AuthenticationPrincipal UserDetails user){
        userService.updateName(userNameUpdateDto.getName(), user);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "유저의 헷지타입 업데이트", description = "토큰 정보 필요함")
    @PutMapping("/user/hedgetype")
    public ResponseEntity<?> updateHedgeType(@RequestBody UserHedgeTypeUpdateDto userHedgeTypeUpdateDto, @AuthenticationPrincipal UserDetails user){
        userService.updateHedgeType(userHedgeTypeUpdateDto.getHedgeType(), user);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "로그아웃 핸들러", description = "프론트에서 헤더 삭제시켜줘야함")
    @PostMapping("/logout")
    public void fakeLogout(){}

    @Operation(summary = "회원탈퇴", description = "프론트에서 헤더 삭제해야함")
    @DeleteMapping("/user")
    public ResponseEntity<?> deletionUser(@AuthenticationPrincipal UserDetails user){
        userService.deletionUser(user);
        return ResponseEntity.ok("success");
    }
}
