package de.adito.relativedateformat.token;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTokenContainerTest {
  @Test
  void shouldHoldTokenInstanceCorrectly() {
    ExpressionTokenContainer container = new ExpressionTokenContainer();

    PeriodToken periodToken = new PeriodToken(Period.ofDays(1));
    EndToken endToken = new EndToken(Period.ofDays(1));
    StartToken startToken = new StartToken(Period.ofDays(1));

    // Add multiple tokens for testing.
    container.addToken(periodToken);
    container.addToken(endToken);
    container.addToken(startToken);

    assertTrue(container.hasToken(periodToken.getClass()));
    assertTrue(container.hasToken(endToken.getClass()));
    assertTrue(container.hasToken(startToken.getClass()));

    assertEquals(container.getToken(periodToken.getClass()), periodToken);
    assertEquals(container.getToken(endToken.getClass()), endToken);
    assertEquals(container.getToken(startToken.getClass()), startToken);
  }

  @Test
  void shouldKeepInsertionOrder() {
    ExpressionTokenContainer container = new ExpressionTokenContainer();

    RelToken relToken = new RelToken(RelToken.Type.ADJUSTED);
    UnitToken unitToken = new UnitToken(UnitToken.Unit.DAY);
    PeriodToken periodToken = new PeriodToken(Period.ofDays(30));

    container.addToken(relToken);
    container.addToken(unitToken);
    container.addToken(periodToken);

    List<IExpressionToken> tokensList = container.getAllTokens();

    assertEquals(relToken, tokensList.get(0));
    assertEquals(unitToken, tokensList.get(1));
    assertEquals(periodToken, tokensList.get(2));
  }

  @Test
  void shouldPerformEqualsCheckCorrectly() {
    ExpressionTokenContainer containerOne = new ExpressionTokenContainer();
    ExpressionTokenContainer containerTwo = new ExpressionTokenContainer();

    RelToken relToken = new RelToken(RelToken.Type.ADJUSTED);
    UnitToken unitToken = new UnitToken(UnitToken.Unit.DAY);
    PeriodToken periodToken = new PeriodToken(Period.ofDays(30));

    containerOne.addToken(relToken);
    containerTwo.addToken(relToken);

    assertEquals(containerOne, containerTwo);

    containerOne.addToken(unitToken);
    containerTwo.addToken(unitToken);

    assertEquals(containerOne, containerTwo);

    containerOne.addToken(periodToken);

    assertNotEquals(containerOne, containerTwo);
  }

  @Test
  void shouldPerformEqualsCheckOnRandomCorrectly() {
    ExpressionTokenContainer containerOne = new ExpressionTokenContainer();
    ExpressionTokenContainer containerTwo = new ExpressionTokenContainer();

    RelToken relToken = new RelToken(RelToken.Type.ADJUSTED);
    UnitToken unitToken = new UnitToken(UnitToken.Unit.DAY);
    PeriodToken periodToken = new PeriodToken(Period.ofDays(30));

    containerOne.addToken(relToken);
    containerOne.addToken(unitToken);
    containerOne.addToken(periodToken);

    assertNotEquals(containerOne, containerTwo);

    containerTwo.addToken(unitToken);
    containerTwo.addToken(periodToken);
    containerTwo.addToken(relToken);

    assertEquals(containerOne, containerTwo);
  }
}
