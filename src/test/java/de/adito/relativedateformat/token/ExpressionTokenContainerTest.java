package de.adito.relativedateformat.token;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

  @Test
  void shouldPerformEqualsCheckCorrectly() {
    ExpressionTokenContainer containerOne = new ExpressionTokenContainer();
    ExpressionTokenContainer containerTwo = new ExpressionTokenContainer();

    RelToken relToken = new RelToken(RelToken.Type.ADJUSTED);
    ScopeToken scopeToken = new ScopeToken(ScopeToken.Scope.DAY);
    DurationToken durationToken = new DurationToken(Duration.ofDays(30));

    containerOne.addToken(relToken);
    containerTwo.addToken(relToken);

    assertEquals(containerOne, containerTwo);

    containerOne.addToken(scopeToken);
    containerTwo.addToken(scopeToken);

    assertEquals(containerOne, containerTwo);

    containerOne.addToken(durationToken);

    assertNotEquals(containerOne, containerTwo);
  }

  @Test
  void shouldPerformEqualsCheckOnRandomCorrectly() {
    ExpressionTokenContainer containerOne = new ExpressionTokenContainer();
    ExpressionTokenContainer containerTwo = new ExpressionTokenContainer();

    RelToken relToken = new RelToken(RelToken.Type.ADJUSTED);
    ScopeToken scopeToken = new ScopeToken(ScopeToken.Scope.DAY);
    DurationToken durationToken = new DurationToken(Duration.ofDays(30));

    containerOne.addToken(relToken);
    containerOne.addToken(scopeToken);
    containerOne.addToken(durationToken);

    assertNotEquals(containerOne, containerTwo);

    containerTwo.addToken(scopeToken);
    containerTwo.addToken(durationToken);
    containerTwo.addToken(relToken);

    assertEquals(containerOne, containerTwo);
  }
}
