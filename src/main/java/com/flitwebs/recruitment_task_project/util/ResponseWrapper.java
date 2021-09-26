package com.flitwebs.recruitment_task_project.util;

import com.flitwebs.recruitment_task_project.constants.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseWrapper<T extends Object> {

  private ResponseStatus status;
  private String message;
  private T data;

  public ResponseWrapper<?> bundleResponse(ResponseStatus status, String message, T data) {

    this.status = status;
    this.message = message;
    this.data = data;
    return this;
  }
}
