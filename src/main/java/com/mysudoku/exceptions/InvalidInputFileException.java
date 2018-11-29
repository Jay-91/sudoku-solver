package com.mysudoku.exceptions;

@SuppressWarnings("serial")
public class InvalidInputFileException extends Exception {

  public InvalidInputFileException(String message) {
    super(message);
  }
}
