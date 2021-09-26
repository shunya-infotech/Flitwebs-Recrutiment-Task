package com.flitwebs.recruitment_task_project.exception.handler;

import com.twilio.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class TwilioExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<?> handleApiException(ApiException apiException, WebRequest webRequest) {

    log.error("exception at : " + webRequest.getDescription(false));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Exception: " + apiException.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception exception, WebRequest webRequest) {

    log.error("exception at : " + webRequest.getDescription(false));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception: " + exception);
  }
}
