package com.hostfully.booking.resources.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

       private LocalDateTime timestamp;
       private int status;
       private String message;
       private List<String> messages;
       private String path;

    public Problem(LocalDateTime timestamp, HttpStatus status, String message, String path) {
        this.timestamp = timestamp;
        this.status = status.value();
        this.message = message;
        this.path = path;
    }

    public Problem(LocalDateTime timestamp, HttpStatus status, List<FieldError> errors, String path) {
        this.timestamp = timestamp;
        this.status = status.value();
        this.path = path;
        this.messages = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    public Problem() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
