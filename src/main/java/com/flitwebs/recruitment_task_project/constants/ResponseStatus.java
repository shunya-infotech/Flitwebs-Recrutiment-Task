package com.flitwebs.recruitment_task_project.constants;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Slf4j
public class ResponseStatus {

  private static ResponseStatus responseStatus = null;

  private Integer code;
  private String message;
  private static final Map<Integer, String> status = new HashMap<>();

  static {
    status.put(1001, "Old User");
    status.put(1002, "New User");
    status.put(1003, "OTP Sent");
    status.put(1004, "Request");
    status.put(1005, "Validation");
    status.put(1006, "Get");
    status.put(2001, "Unexpected");
  }

  public static ResponseStatus get(Integer code) {

    responseStatus = Optional.ofNullable(responseStatus).orElseGet(() -> new ResponseStatus());

    return Optional.ofNullable(status.containsKey(code))
        .filter(val -> val)
        .map(
            s -> {
              responseStatus.code = code;
              responseStatus.message = status.get(code);
              return responseStatus;
            })
        .orElseGet(
            () -> {
              responseStatus.code = code;
              responseStatus.message = "Invalid status code Requested";
              return responseStatus;
            });
  }
}
