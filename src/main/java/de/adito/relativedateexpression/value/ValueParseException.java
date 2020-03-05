package de.adito.relativedateexpression.value;

/**
 * Represents a exception which will be thrown if a value could not be parsed by a {@link
 * IValueParser}.
 */
public class ValueParseException extends Exception {
  public ValueParseException(String message) {
    super(message);
  }
}
