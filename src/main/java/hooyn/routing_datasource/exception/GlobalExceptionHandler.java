package hooyn.routing_datasource.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @Value("${spring.servlet.multipart.maxFileSize}")
    private String maxFileSize;
    
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> customHandleException(CustomException e, WebRequest wr) {
        
        ErrorCode errorCode;
        String cause = "empty";
        String message;
        
        if (e.getErrorCode() != null){
            errorCode = e.getErrorCode();
            message = e.getMessage();
            // e.printStackTrace();
        }
        else {
            cause = e.getCause().getClass().getSimpleName();
            message = e.getCause().getMessage();
            errorCode = checkErrorCode(cause);
        }

        ErrorResponse errorResponse = new ErrorResponse(errorCode, cause, ((ServletWebRequest)wr).getRequest().getRequestURI(), message);

        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest wr) {
        
        ErrorCode errorCode;
        String cause = e.getClass().getSimpleName();
        String message = checkDetailMessage(cause, e);

        errorCode = checkErrorCode(cause);

        return ResponseEntity
                .status(errorCode.getStatus().value())
                .body(new ErrorResponse(errorCode, cause, ((ServletWebRequest)wr).getRequest().getRequestURI(), message));
    }

    // 예외 이름으로 Status 구별 (상시 추가)
    private ErrorCode checkErrorCode(String code){

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        
        if (code.equals("AccessDeniedException"))
            errorCode = ErrorCode.FORBIDDEN;
        else if (code.equals("HttpMessageNotReadableException") || code.equals("MissingServletRequestParameterException") || code.equals("JsonParseException")
                    || code.equals("FileSizeLimitExceededException") || code.equals("MethodArgumentTypeMismatchException") || code.equals("HttpMediaTypeNotSupportedException"))
            errorCode = ErrorCode.BAD_REQUEST;

        return errorCode;
    }

    // 예외 이름으로 Message 구별 (상시 추가)
    private String checkDetailMessage(String code, Exception e){

        String message = e.getMessage();
        
        if (code.equals("HttpMessageNotReadableException") || code.equals("MissingServletRequestParameterException"))
            message = "요청 데이터가 필요합니다.";
        else if (code.equals("JsonParseException"))
            message = "데이터 포맷이 맞지 않습니다.";
        else if (code.equals("FileSizeLimitExceededException"))
            message = "업로드 가능한 최대 파일 사이즈는 " + this.maxFileSize + " 입니다.";
        else if (code.equals("MethodArgumentTypeMismatchException"))
            message = "요청 파라미터의 타입이 올바르지 않습니다.";
        else if (code.equals("HttpMediaTypeNotSupportedException"))
            message = "Content-Type 이 맞지 않습니다.";

        return message;
    }
}
