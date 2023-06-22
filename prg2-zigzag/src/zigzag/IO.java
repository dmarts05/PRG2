package zigzag;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gathers information related to user input and displays results
 */
public class IO {
  /**
   * Transforms user input into a Zigzag object
   * 
   * @param in Scanner object
   * @return a Zigzag object containing the board that the user introduced
   */
  public static Zigzag getInputZigzag(Scanner in) {
    Zigzag zigzag = null;
    boolean validInput = true;
    int rows = 0;
    int columns = 0;

    ArrayList<String[]> inputRows = new ArrayList<String[]>();

    while (in.hasNextLine()) {
      // Get line from input and transform it into an array by spliting black spaces
      String[] currentLine = in.nextLine().split(" ");

      // Store tokens
      inputRows.add(currentLine);
    }

    // Get number of rows
    rows = inputRows.size();

    // Rows need to be between 1 and 10
    if (rows <= 0 || rows >= 11) {
      validInput = false;
    }

    // Get number of columns
    if (validInput) {
      columns = inputRows.get(0).length;
    }

    // Columns need to be between 1 and 10
    if (columns <= 0 || columns >= 11) {
      validInput = false;
    }

    // Create board containing valid nodes
    Node board[][] = new Node[rows][columns];
    int currentRow = 0;
    for (String[] inputRow : inputRows) {
      int currentColumn = 0;
      for (int i = 0; i < inputRow.length && validInput; i++) {
        // Check if columns are out of bounds
        if (currentColumn >= columns) {
          // Columns are inconsistent
          validInput = false;
          break;
        }

        int nodeValue = 0;
        try {
          nodeValue = Integer.parseInt(inputRow[i]);
        } catch (NumberFormatException e) {
          // Not an integer, exiting...
          validInput = false;
          break;
        }

        Node node = null;
        try {
          node = new Node(nodeValue, currentRow, currentColumn);
        } catch (IllegalArgumentException e) {
          // nodeValue is not between 1 and 9
          validInput = false;
          break;
        }

        board[currentRow][currentColumn] = node;
        currentColumn++;
      }

      currentRow++;
    }

    // Check that created board has no empty cells
    for (int i = 0; i < rows && validInput; i++) {
      for (int j = 0; j < columns && validInput; j++) {
        if (board[i][j] == null) {
          // Columns are inconsistent
          validInput = false;
          break;
        }
      }
    }

    if (validInput) {
      zigzag = new Zigzag(board);
    } else {
      printInputError();
    }

    return zigzag;
  }

  /**
   * Prints an input error
   */
  private static void printInputError() {
    System.out.println("Entrada incorrecta.");
  }

  /**
   * Transforms a solution board into a string representing the solution
   * 
   * @param board Resolved board
   * @return String containing the solution of the given board
   */
  public static String zigzagSolutionToString(Node[][] board) {
    String[][] solutionMatrix = new String[board.length + board.length - 1][board[0].length + board[0].length - 1];

    int solutionRow = 0;
    int solutionColumn = 0;

    for (int i = 0; i < solutionMatrix.length; i++) {
      for (int j = 0; j < solutionMatrix[0].length; j++) {
        solutionMatrix[i][j] = " ";
      }
    }

    StringBuilder solution = new StringBuilder();

    // Board is looped through normally while solutionMatrix jumps from node to node
    for (int i = 0; i < board.length; i++, solutionRow += 2) {
      for (int j = 0; j < board[0].length; j++, solutionColumn += 2) {
        Node currentNode = board[i][j];
        int currentNodeLinkType = currentNode.getLinkType();

        // Place node
        solutionMatrix[solutionRow][solutionColumn] = String.valueOf(currentNode.getValue());

        // Put link in its correct row and column
        switch (currentNodeLinkType) {
          // Upper row
          case 1:
            solutionMatrix[solutionRow - 1][solutionColumn - 1] = transformLinkType(currentNodeLinkType);
            break;
          case 2:
            solutionMatrix[solutionRow - 1][solutionColumn] = transformLinkType(currentNodeLinkType);
            break;
          case 3:
            solutionMatrix[solutionRow - 1][solutionColumn + 1] = transformLinkType(currentNodeLinkType);
            break;
          // Current row
          case 4:
            solutionMatrix[solutionRow][solutionColumn - 1] = transformLinkType(currentNodeLinkType);
            break;
          case 6:
            solutionMatrix[solutionRow][solutionColumn + 1] = transformLinkType(currentNodeLinkType);
            break;
          // Lower row
          case 7:
            solutionMatrix[solutionRow + 1][solutionColumn - 1] = transformLinkType(currentNodeLinkType);
            break;
          case 8:
            solutionMatrix[solutionRow + 1][solutionColumn] = transformLinkType(currentNodeLinkType);
            break;
          case 9:
            solutionMatrix[solutionRow + 1][solutionColumn + 1] = transformLinkType(currentNodeLinkType);
            break;
          default:
            break;
        }
      }

      solutionColumn = 0;
    }

    // Transform matrix into a String
    for (int i = 0; i < solutionMatrix.length; i++) {
      for (int j = 0; j < solutionMatrix[0].length; j++) {
        solution.append(solutionMatrix[i][j]);
      }

      solution.append("\n");
    }

    return solution.toString();
  }

  /**
   * Transforms a link type number into a representing character
   * 
   * @param linkType Link type number
   * @return A character that represents that given number
   */
  private static String transformLinkType(int linkType) {
    switch (linkType) {
      case 1:
      case 9:
        return "\\";
      case 2:
      case 8:
        return "|";
      case 3:
      case 7:
        return "/";
      case 4:
      case 6:
        return "-";

      default:
        return "";
    }
  }
}
