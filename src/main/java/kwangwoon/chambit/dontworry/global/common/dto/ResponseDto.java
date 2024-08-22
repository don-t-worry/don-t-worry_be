package kwangwoon.chambit.dontworry.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // DTO 를 JSON으로 변환 시 null값인 field 제외
public class ResponseDto<T>{
    private Integer status;
    private T value;

    public ResponseDto(Integer status){
        this.status = status;
    }
    public static ResponseDto<?> addStatus(Integer status){
        return new ResponseDto<>(status);
    }
    public static ResponseDto<?> success(){
        return new ResponseDto<>(200);
    }
    public static <T> ResponseDto<T> success(T value) {
        return new ResponseDto<>(200, value);
    }
}
