package adeo.leroymerlin.cdp;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

/**
 * The type Application error response.
 * handle in a more user friendly manner
 * the error message printing everytime
 * a rest call crash
 * the applicationSubErrorList handle the
 * case where during api validation we may
 * need to aggregate all the validation
 * errors in one shot.
 */
public class ApplicationErrorResponse {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    private String message;

    private String localizedMessage;

    private ApplicationErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    ApplicationErrorResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    ApplicationErrorResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.localizedMessage = ex.getLocalizedMessage();
    }

    ApplicationErrorResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.localizedMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

}
