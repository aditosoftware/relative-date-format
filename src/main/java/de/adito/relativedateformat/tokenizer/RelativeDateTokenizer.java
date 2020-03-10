package de.adito.relativedateformat.tokenizer;

import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;

public interface RelativeDateTokenizer {
  /**
   * Will split the given expression into its parts and create a {@link IExpression} from it. This
   * will never return `null`.
   *
   * @param expression The expression to tokenize
   * @return The tokenized expression.
   * @throws TokenizeException If anything fails during the tokenization.
   */
  IExpression tokenize(String expression);

  /**
   * Will create a new {@link RelativeDateTokenizer} instance.
   *
   * @return new RelativeDateTokenizer.
   */
  public static RelativeDateTokenizer get() {
    return new DefaultRelativeDateTokenizer();
  }
}
