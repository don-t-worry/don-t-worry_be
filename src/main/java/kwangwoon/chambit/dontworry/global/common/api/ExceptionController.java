package kwangwoon.chambit.dontworry.global.common.api;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import kwangwoon.chambit.dontworry.global.common.dto.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleRefreshTokenException(JwtException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(402)
                .value(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .value(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
