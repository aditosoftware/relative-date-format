package de.adito.relativedateexpression.engine;

/**
 * Represents a exception which represents a calculation exception within the {@link
 * RelativeDateExpressionEngine}.
 */
public class ExpressionCalculateException extends Exception {
  public ExpressionCalculateException(String message) {
    super(message);
  }
}
