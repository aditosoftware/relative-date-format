package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

public class UnitToken extends AbstractToken<UnitToken.Unit> {
  public UnitToken (@NotNull Unit value) {
    super("UNIT", value);
  }

  public enum Unit {
    YEAR,
    MONTH,
    WEEK,
    DAY
  }
}
