package com.nnk.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception with Http error 404, used when argument is not suitable.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
  public DataNotFoundException(String error) {
    super(error);
  }
}
