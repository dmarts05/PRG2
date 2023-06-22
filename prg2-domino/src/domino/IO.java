package domino;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gathers information related to user input and displays results
 */
public class IO {

  /**
   * Transforms user input into dominos
   * 
   * @param in
   * @return list containing every domino setup the user has introduced
   */
  public static ArrayList<Domino> getInputDominos(Scanner in) {
    ArrayList<Domino> dominos = new ArrayList<Domino>();

    int spaceCount = 1;
    int rotableChipCount;
    Chip[] fixedChips = new Chip[2];
    ArrayList<Chip> rotableChips = new ArrayList<Chip>();

    while (true) {
      // Empty arrays for each iteration
      for (int i = 0; i < fixedChips.length; i++) {
        fixedChips[i] = null;
      }
      rotableChips.clear();

      // Get amount of spaces
      spaceCount = in.nextInt();
      // If there are no spaces there are no more domino sets
      if (spaceCount == 0) {
        break;
      }

      // Get amount of rotable chips
      rotableChipCount = in.nextInt();

      // Get initial chips
      for (int i = 0; i < fixedChips.length; i++) {
        fixedChips[i] = new Chip(in.nextInt(), in.nextInt(), false);
      }

      // Get rotable chips
      for (int i = 0; i < rotableChipCount; i++) {
        rotableChips.add(new Chip(in.nextInt(), in.nextInt(), true));
      }

      dominos.add(new Domino(spaceCount, rotableChipCount, fixedChips, rotableChips));
    }

    return dominos;
  }
}
