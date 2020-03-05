package de.adito.relativedateexpression.token;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionTokenContainerTest {
  @Test
  void shouldHoldTokenInstanceCorrectly() {
    ExpressionTokenContainer container = new ExpressionTokenContainer();

    DurationToken durationToken = new DurationToken(Duration.ofDays(1));
    EndToken endToken = new EndToken(Duration.ofDays(1));
    StartToken startToken = new StartToken(Duration.ofDays(1));

    // Add multiple tokens for testing.
    container.addToken(durationToken);
    container.addToken(endToken);
    container.addToken(startToken);

    assertTrue(container.hasToken(durationToken.getClass()));
    assertTrue(container.hasToken(endToken.getClass()));
    assertTrue(container.hasToken(startToken.getClass()));

    assertEquals(container.getToken(durationToken.getClass()), durationToken);
    assertEquals(container.getToken(endToken.getClass()), endToken);
    assertEquals(container.getToken(startToken.getClass()), startToken);
  }
}
