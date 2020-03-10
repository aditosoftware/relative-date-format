package de.adito.relativedateformat.engine.exception;

import de.adito.relativedateformat.engine.DefaultRelativeDateEngine;

/**
 * Represents a exception which represents a calculation exception within the {@link
 * DefaultRelativeDateEngine}.
 */
public class EngineException extends RuntimeException {
  public EngineException (String message) {
    super(message);
  }
}
