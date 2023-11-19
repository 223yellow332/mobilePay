package com.calmdown.mobilePay.global.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.errorCode.ErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import com.calmdown.mobilePay.global.exception.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 사용자 정의 에러코드 처리
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleCustomException(UserException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    // IllegalArgumentException 에러 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, e.getMessage());
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("handleIllegalArgument", ex);
        return handleExceptionInternal(ex, CommonErrorCode.INVALID_PARAMETER);
    }

    // Json으로 파싱할때 자료형타입으로 캐스트 실패시 발생
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("handleIllegalArgument", ex);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }
    
    // 대부분의 에러 처리
    @ExceptionHandler
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.warn("handleAllException", ex);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
    
    // RuntimeException과 대부분의 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }
    
    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }
    
    /*
     * 정의되지 않은 에러코드들은 body에 아무 값이 실리지 않기에 재정의
     * 에러 발생 지점도 로그로 추가
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn("handleIllegalArgument", ex);
        return ResponseEntity.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorResponse.builder()
                        .code(CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                        .message(ex.getMessage())
                        .build());
    }
}