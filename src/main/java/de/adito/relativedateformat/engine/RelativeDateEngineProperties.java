package de.adito.relativedateformat.engine;

import org.jetbrains.annotations.Nullable;

import java.time.DayOfWeek;

/** Defines configuration properties for a {@link RelativeDateEngine}. */
public class RelativeDateEngineProperties {
  @Nullable private final DayOfWeek firstDayOfWeek;

  public RelativeDateEngineProperties(@Nullable DayOfWeek pFirstDayOfWeek) {
    firstDayOfWeek = pFirstDayOfWeek;
  }

  /**
   * Will return the first day of week to use for the adjusted expressions.
   *
   * @return The first day of week.
   */
  @Nullable
  public DayOfWeek getFirstDayOfWeek() {
    return firstDayOfWeek;
  }
}
