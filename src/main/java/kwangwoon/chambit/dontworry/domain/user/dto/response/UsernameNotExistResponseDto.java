package kwangwoon.chambit.dontworry.domain.user.dto.response;

import lombok.Data;


@Data
public class UsernameNotExistResponseDto implements UsernameResponseDto{
    private String username;

    public UsernameNotExistResponseDto(String username){
        this.username = username;
    }

}
