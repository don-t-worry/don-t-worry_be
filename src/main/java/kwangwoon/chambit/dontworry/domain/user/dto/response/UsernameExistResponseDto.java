package kwangwoon.chambit.dontworry.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kwangwoon.chambit.dontworry.domain.user.domain.User;
import lombok.Data;

@Data
public class UsernameExistResponseDto implements UsernameResponseDto{
    private String name;

    @JsonIgnore
    private String token;

    public UsernameExistResponseDto(String name, String token){
        this.name = name;
        this.token = token;
    }
}
