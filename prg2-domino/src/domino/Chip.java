package domino;

/**
 * Represents a domino chip
 * 
 * @author dmarts05
 */
public class Chip {

  /**
   * Stores the first number of the chip
   */
  private int firstNum;

  /**
   * Stores the second number of the chip
   */
  private int secondNum;

  /**
   * Indicates whether a chip can be rotated
   */
  private boolean rotable;

  /**
   * Creates a chip
   * Prec: 0 <= firstNum <= 6 and 0 <= secondNum <= 6
   * 
   * @param firstNum  first number of the chip
   * @param secondNum second number of the chip
   * @param rotable   whether the chip rotable or not (true or false)
   * @throws IllegalArgumentException
   */
  public Chip(int firstNum, int secondNum, boolean rotable) {
    if (firstNum < 0 || firstNum > 6 || secondNum < 0 || secondNum > 6) {
      throw new IllegalArgumentException();
    }
    this.firstNum = firstNum;
    this.secondNum = secondNum;
    this.rotable = rotable;
  }

  /**
   * Returns the first number of the chip
   * 
   * @return first number of the chip
   */
  public int getChipFirstNum() {
    return this.firstNum;
  }

  /**
   * Returns the second number of the chip
   * 
   * @return second number of the chip
   */
  public int getChipSecondNum() {
    return this.secondNum;
  }

  /**
   * If a chip can be rotated, it swaps the first number and the second number of
   * that chip
   * 
   * @return true if the chip was successfully rotated; false otherwise
   */
  public boolean rotateChip() {
    if (rotable) {
      int aux = this.firstNum;
      this.firstNum = this.secondNum;
      this.secondNum = aux;
      return true;
    } else {
      return false;
    }
  }
}
