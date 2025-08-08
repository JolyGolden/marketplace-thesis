//package nazar.zhanabergenov.shop_monolith;
//
//import jakarta.persistence.LockTimeoutException;
//import jakarta.validation.ConstraintViolationException;
//import lombok.Builder;
//import lombok.Data;
//import nazar.zhanabergenov.shop_monolith.order.exception.NotFoundException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.transaction.CannotAcquireLockException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpServletRequest;
//import java.time.Instant;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest req) {
//        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), null);
//    }
//
//    @ExceptionHandler({ MethodArgumentNotValidException.class })
//    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
//        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
//                .collect(Collectors.toMap(fe -> fe.getField(), fe -> fe.getDefaultMessage(), (a,b) -> a));
//        return build(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), details);
//    }
//
//    @ExceptionHandler({ ConstraintViolationException.class, HttpMessageNotReadableException.class, IllegalArgumentException.class })
//    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest req) {
//        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(), null);
//    }
//
//    @ExceptionHandler({ DataIntegrityViolationException.class })
//    public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
//        return build(HttpStatus.CONFLICT, "Data integrity violation", req.getRequestURI(), null);
//    }
//
//    // конкуренция/локи (для pessimistic lock)
//    @ExceptionHandler({ CannotAcquireLockException.class, LockTimeoutException.class })
//    public ResponseEntity<ErrorResponse> handleLock(Exception ex, HttpServletRequest req) {
//        return build(HttpStatus.SERVICE_UNAVAILABLE, "Resource is busy, please retry", req.getRequestURI(), null);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleAny(Exception ex, HttpServletRequest req) {
//        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI(), null);
//    }
//
//    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, String path, Map<String, String> details) {
//        return ResponseEntity.status(status).body(
//                ErrorResponse.builder()
//                        .timestamp(Instant.now().toString())
//                        .status(status.value())
//                        .error(status.getReasonPhrase())
//                        .message(message)
//                        .path(path)
//                        .details(details)
//                        .build()
//        );
//    }
//
//    @Data
//    @Builder
//    static class ErrorResponse {
//        private String timestamp;
//        private int status;
//        private String error;
//        private String message;
//        private String path;
//        private Map<String, String> details;
//    }
//}
