package de.adito.relativedateexpression.token;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

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

  @Test
  void shouldKeepInsertionOrder() {
    ExpressionTokenContainer container = new ExpressionTokenContainer();

    RelToken relToken = new RelToken(RelToken.Type.ADJUSTED);
    ScopeToken scopeToken = new ScopeToken(ScopeToken.Scope.DAY);
    DurationToken durationToken = new DurationToken(Duration.ofDays(30));

    container.addToken(relToken);
    container.addToken(scopeToken);
    container.addToken(durationToken);

    List<IExpressionToken> tokensList = container.getAllTokens();

    assertEquals(relToken, tokensList.get(0));
    assertEquals(scopeToken, tokensList.get(1));
    assertEquals(durationToken, tokensList.get(2));
  }
}
