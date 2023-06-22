package domino;

import java.util.ArrayList;

/**
 * Represents a full domino set
 */
public class Domino {
  /**
   * Stores the amount of spaces between the fixed chips of the domino set
   */
  private int spaceCount;

  /**
   * Stores the two fixed chips of the domino set
   */
  private Chip[] fixedChips = new Chip[2];

  /**
   * Stores 14 rotable chips at most of the domino set
   */
  private ArrayList<Chip> rotableChips = new ArrayList<Chip>();

  /**
   * Creates a domino set
   * 
   * @param spaceCount       amount of available spaces between the fixed chips
   * @param rotableChipCount amount of available rotable chips
   * @param fixedChips       array including the two fixed chips
   * @param rotableChips     array including every rotable chip
   */
  public Domino(int spaceCount, int rotableChipCount, Chip[] fixedChips, ArrayList<Chip> rotableChips) {
    if (spaceCount <= 0 || rotableChipCount <= 0 || rotableChipCount < spaceCount || rotableChipCount > 14) {
      throw new IllegalArgumentException();
    }

    this.spaceCount = spaceCount;
    for (int i = 0; i < fixedChips.length; i++) {
      this.fixedChips[i] = fixedChips[i];
    }
    this.rotableChips.addAll(rotableChips);
  }

  /**
   * Tries to find a solution for a domino set with backtracking
   * 
   * @return true if the domino set has a solution, false otherwise
   */
  public boolean solveDomino() {
    Chip[] sol = new Chip[spaceCount + fixedChips.length];
    sol[0] = fixedChips[0];
    ArrayList<Chip> restrictedChips = new ArrayList<Chip>();

    return solveDominoAux(sol, 1, restrictedChips);
  }

  private boolean solveDominoAux(Chip[] sol, int currentPos, ArrayList<Chip> restrictedChips) {
    if (currentPos == sol.length) {
      return true;
    } else {
      if (sol[sol.length - 2] != null) {
        // We need to add the last fixed chip and check if the solution works
        if (validateChips(sol[currentPos - 1], fixedChips[1])) {
          sol[currentPos] = fixedChips[1];
          return solveDominoAux(sol, ++currentPos, restrictedChips);
        } else {
          // If it doesn't fit, we need to remove the last introduced chip and
          // restrict it from being used
          currentPos--;
          restrictedChips.add(sol[currentPos]);
          sol[currentPos] = null;
          return solveDominoAux(sol, currentPos, restrictedChips);
        }
      } else {
        for (Chip currentChip : rotableChips) {
          if (!checkIfArrayContainsChip(sol, currentChip) && !restrictedChips.contains(currentChip)
              && validateChips(sol[currentPos - 1], currentChip)) {
            // This means that the current chip fits in the required position and is not
            // currently restricted
            sol[currentPos] = currentChip;
            return solveDominoAux(sol, ++currentPos, restrictedChips);
          }
        }
      }
      // There are no unrestricted chips that fit in this place, so we need to get
      // back and change the previous chip (restricting it from being used)

      // If we reach the first chip, there is no solution
      if (--currentPos > 0) {
        restrictedChips.clear();
        restrictedChips.add(sol[currentPos]);
        sol[currentPos] = null;
        return solveDominoAux(sol, currentPos, restrictedChips);
      } else {
        return false;
      }
    }
  }

  /**
   * Checks whether two chips can be put together
   * 
   * @param firstChip  first chip (will not be rotated)
   * @param secondChip second chip (will be rotated)
   * @return true if the chips can be put together, false otherwise
   */
  public boolean validateChips(Chip firstChip, Chip secondChip) {
    if (firstChip.getChipSecondNum() == secondChip.getChipFirstNum()) {
      return true;
    } else {
      secondChip.rotateChip();
      if (firstChip.getChipSecondNum() == secondChip.getChipFirstNum()) {
        return true;
      } else {
        return false;
      }
    }
  }

  private boolean checkIfArrayContainsChip(Chip[] arr, Chip chip) {
    for (int i = 0; i < arr.length && arr[i] != null; i++) {
      if (arr[i].equals(chip)) {
        return true;
      }
    }

    return false;
  }
}
