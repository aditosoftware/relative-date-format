package de.adito.relativedateexpression.engine;

import de.adito.relativedateexpression.expression.IExpression;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

/** Describes the result for {@link IExpressionEngine#resolve(IExpression)}. */
public interface IResult {
  /**
   * Will return the start of the time frame.
   *
   * @return The start of the time frame.
   */
  @NotNull
  LocalDateTime getStart();

  /**
   * Will return the end of the time frame.
   *
   * @return The end of the time frame.
   */
  @NotNull
  LocalDateTime getEnd();

  /**
   * Will return the duration of the time frame. Equals to: end - start.
   *
   * @return The duration of the time frame.
   */
  @NotNull
  Duration getDuration();
}
