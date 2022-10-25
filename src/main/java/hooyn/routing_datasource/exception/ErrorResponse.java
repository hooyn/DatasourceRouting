package hooyn.routing_datasource.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String cause;
    private final String message;
    private final String detailMessage;
    private final String path;

    public ErrorResponse(ErrorCode errorCode, String cause, String path, String detailMessage) {
        
        this.status = errorCode.getStatus().value();
        this.error = errorCode.name();
        this.cause = cause;
        this.message = errorCode.getMessage();
        this.detailMessage = detailMessage;
        this.path = path;
    }
}
