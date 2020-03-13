package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

public class RelToken extends AbstractToken<RelToken.Type> {
  public RelToken(@NotNull Type value) {
    super("REL", value);
  }

  public enum Type {
    ADJUSTED,
    FIXED,
    MIXED
  }
}
