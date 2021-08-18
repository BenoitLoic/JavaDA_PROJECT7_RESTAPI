package com.nnk.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadArgumentException extends RuntimeException {
  public BadArgumentException(String error) {
    super(error);

  }
}
