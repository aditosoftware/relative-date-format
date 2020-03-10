package de.adito.relativedateformat.engine;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class DefaultResult implements RelativeDateResult {
  private LocalDateTime start;
  private LocalDateTime end;
  private Duration duration;

  public DefaultResult (LocalDateTime start, LocalDateTime end, Duration duration) {
    this.start = start;
    this.end = end;
    this.duration = duration;
  }

  @Override
  public @NotNull LocalDateTime getStart () {
    return start;
  }

  @Override
  public @NotNull LocalDateTime getEnd () {
    return end;
  }

  @Override
  public @NotNull Duration getDuration () {
    return duration;
  }
}
