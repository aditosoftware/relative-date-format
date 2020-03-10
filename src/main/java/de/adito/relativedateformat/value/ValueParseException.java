package de.adito.relativedateformat.value;

/**
 * Represents a exception which will be thrown if a value could not be parsed by a {@link
 * ValueParser}.
 */
public class ValueParseException extends RuntimeException {
  public ValueParseException(String message) {
    super(message);
  }
}
