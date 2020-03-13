package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

public class FullToken extends AbstractToken<Boolean> {
  public FullToken(@NotNull Boolean value) {
    super("FULL", value);
  }
}
