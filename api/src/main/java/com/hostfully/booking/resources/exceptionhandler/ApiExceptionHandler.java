package com.hostfully.booking.resources.exceptionhandler;

import com.hostfully.booking.domain.exceptions.InvalidRangeException;
import com.hostfully.booking.domain.exceptions.RangeNotAvailableException;
import com.hostfully.booking.domain.exceptions.ReservationNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            ReservationNotFoundException.class,
    })
    public ResponseEntity<?> handleReservationNotFound(ReservationNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = new Problem(LocalDateTime.now(), status, detail, request.getRequestURI());

        return new ResponseEntity<>(problem, status);
    }

    @ExceptionHandler({
            RangeNotAvailableException.class,
    })
    public ResponseEntity<?> handleRangeNotAvailable(RangeNotAvailableException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();

        Problem problem = new Problem(LocalDateTime.now(), status, detail, request.getRequestURI());

        return new ResponseEntity<>(problem, status);
    }

    @ExceptionHandler({
            InvalidRangeException.class,
    })
    public ResponseEntity<?> handleInvalidRange(InvalidRangeException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();

        Problem problem = new Problem(LocalDateTime.now(), status, detail, request.getRequestURI());

        return new ResponseEntity<>(problem, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        Problem problem = new Problem(LocalDateTime.now(), status, fieldErrors, ((ServletWebRequest)request).getRequest().getRequestURI());

        return new ResponseEntity<>(problem, status);
    }
}
