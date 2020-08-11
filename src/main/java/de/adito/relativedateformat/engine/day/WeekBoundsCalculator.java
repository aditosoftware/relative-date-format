package de.adito.relativedateformat.engine.day;

import java.time.DayOfWeek;
import java.time.temporal.*;

/**
 * Implementation to calculate the start and ending of a week based on the start. The end will
 * always be the previous day of the start day.
 */
public class WeekBoundsCalculator {
  private WeekBoundsCalculator() {}

  /**
   * Will calculate the bounds for the week. The start and end bounds are represented by a {@link
   * TemporalAdjuster}, which can be applied to the relative date. Depending on the actual bounds,
   * the start may not be instanceof {@link DayOfWeek} as we need to apply a {@link
   * TemporalAdjusters#previousOrSame(DayOfWeek)} wrapper, adjusts the start into the previous day
   * and not the next. This happens when the ordinal number (Monday is 1, Sunday is 7, etc.) of the
   * end is lower than the start.
   *
   * @param start The start based on which we calculate the the bounds.
   * @return The calculated bounds.
   */
  public static Bounds getBounds(DayOfWeek start) {
    int startVal = start.getValue();

    // Calculate the end by the start minus one.
    int endVal = circular(startVal - 1);

    if (endVal < startVal)
      return new Bounds(TemporalAdjusters.previousOrSame(start), DayOfWeek.of(endVal));

    return new Bounds(start, DayOfWeek.of(endVal));
  }

  private static int circular(int input) {
    final int start = 1;
    final int end = 7;

    if (input < start) return end - ((-input + end) % end);
    else if (input > end) return (input % end) - 1;
    else return input;
  }

  /**
   * Holds the bounds for the week. The start and the end is represented by a {@link
   * TemporalAdjuster}. Each, the start and end, can be applied to the relative day.
   */
  public static class Bounds {
    public final TemporalAdjuster start;
    public final TemporalAdjuster end;

    public Bounds(TemporalAdjuster pStart, TemporalAdjuster pEnd) {
      start = pStart;
      end = pEnd;
    }
  }
}
