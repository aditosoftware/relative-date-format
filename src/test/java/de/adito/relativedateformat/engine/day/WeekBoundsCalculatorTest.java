package de.adito.relativedateformat.engine.day;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.DayOfWeek;
import java.util.stream.Stream;

class WeekBoundsCalculatorTest
{
  @ParameterizedTest
  @MethodSource("provideTestingBounds")
  void shouldResolveDefaultBoundCorrectly(
      DayOfWeek start, DayOfWeek end, boolean startTypeOf, boolean endTypeOf) {
    WeekBoundsCalculator.Bounds res = WeekBoundsCalculator.getBounds(start);

    if (startTypeOf) {
      Assertions.assertTrue(
          res.start instanceof DayOfWeek, "'start' is expected to be instance of DayOfWeek");
      Assertions.assertEquals(start, res.start);
    }
    if (!startTypeOf)
      Assertions.assertFalse(
          res.start instanceof DayOfWeek, "'start' is not expected to be instance of DayOfWeek");

    if (endTypeOf) {
      Assertions.assertTrue(
          res.end instanceof DayOfWeek, "'end' is expected to be instance of DayOfWeek");
      Assertions.assertEquals(end, res.end);
    }
    if (!endTypeOf)
      Assertions.assertFalse(
          res.end instanceof DayOfWeek, "'end' is not expected to be instance of DayOfWeek");
  }

  private static Stream<Arguments> provideTestingBounds() {
    return Stream.of(
        Arguments.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY, false, true), // 7 to 6
        Arguments.of(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, true, true), // 1 to 7
        Arguments.of(DayOfWeek.TUESDAY, DayOfWeek.MONDAY, false, true), // 2 to 1
        Arguments.of(DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY, false, true), // 3 to 2
        Arguments.of(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, false, true), // 4 to 3
        Arguments.of(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY, false, true), // 5 to 4
        Arguments.of(DayOfWeek.SATURDAY, DayOfWeek.FRIDAY, false, true)); // 6 to 5
  }
}
