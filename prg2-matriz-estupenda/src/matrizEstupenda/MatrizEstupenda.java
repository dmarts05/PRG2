package matrizEstupenda;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrizEstupenda {
  public static void getMatrixInput(Scanner in, int[][] matrix) {
    getMatrixInputRec(in, matrix, 0, 0);
  }

  private static void getMatrixInputRec(Scanner in, int[][] matrix, int i, int j) {
    if (i < matrix.length) {
      if (j < matrix.length) {
        matrix[i][j] = in.nextInt();
        getMatrixInputRec(in, matrix, i, j + 1);
      } else {
        getMatrixInputRec(in, matrix, i + 1, 0);
      }
    }

    in.close();
  }

  private static List<Integer> getDivisorsOfNumber(int num) {
    List<Integer> divisors = new ArrayList<Integer>();

    getDivisorsOfNumberRec(divisors, num, 1);

    return divisors;
  }

  private static void getDivisorsOfNumberRec(List<Integer> divisors, int num, int currentNum) {
    if (currentNum < num) {
      if (num % currentNum == 0) {
        divisors.add(currentNum);
      }

      getDivisorsOfNumberRec(divisors, num, currentNum + 1);
    }
  }

  private static int getSumDivisorsOfNumber(int num) {
    int[] sum = new int[] { 0 };

    getSumDivisorsOfNumberRec(sum, num, 1);

    return sum[0];
  }

  private static void getSumDivisorsOfNumberRec(int[] sum, int num, int currentNum) {
    if (currentNum < num) {
      if (num % currentNum == 0) {
        sum[0] += currentNum;
      }

      getSumDivisorsOfNumberRec(sum, num, currentNum + 1);
    }
  }

  private static boolean checkIfNumbersAreFriends(int num1, int num2) {
    if (num1 == getSumDivisorsOfNumber(num2) && num2 == getSumDivisorsOfNumber(num1)) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkIfNumberIsPrime(int num) {
    if (getDivisorsOfNumber(num).size() == 1 || num == 1) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkIfDiagonalNumbersArePrime(int[][] matrix) {
    return checkIfDiagonalNumbersArePrimeRec(matrix, 0, 0);
  }

  private static boolean checkIfDiagonalNumbersArePrimeRec(int[][] matrix, int i, int j) {
    if (i < matrix.length) {
      if (j < matrix.length) {
        return checkIfNumberIsPrime(matrix[i][j]) && checkIfDiagonalNumbersArePrimeRec(matrix, i + 1, j + 1);
      }
    }

    return true;
  }

  private static boolean checkIfNonDiagonalNumbersAreFriends(int[][] matrix) {
    return checkIfNonDiagonalNumbersAreFriendsRec(matrix, 0, 0);
  }

  private static boolean checkIfNonDiagonalNumbersAreFriendsRec(int[][] matrix, int i, int j) {
    if (i < matrix.length) {
      if (i == j) {
        return checkIfNonDiagonalNumbersAreFriendsRec(matrix, i + 1, j);
      } else {
        if (j < matrix.length) {
          if (i == j) {
            return checkIfNonDiagonalNumbersAreFriendsRec(matrix, i, j + 1);
          } else {
            return checkIfNumbersAreFriends(matrix[i][j], matrix[j][i])
                && checkIfNonDiagonalNumbersAreFriendsRec(matrix, i, j + 1);
          }
        } else {
          return checkIfNumbersAreFriends(matrix[i][j], matrix[j][i])
              && checkIfNonDiagonalNumbersAreFriendsRec(matrix, i, j + 1);
        }
      }
    }

    return true;
  }

  public static boolean checkIfMatrixIsGreat(int[][] matrix) {
    return checkIfDiagonalNumbersArePrime(matrix) && checkIfNonDiagonalNumbersAreFriends(matrix);
  }

  public static int getHighestNumberInMatrix(int[][] matrix) {
    int[] highestNumber = new int[] { 0 };

    getHighestNumberInMatrixRec(matrix, highestNumber, 0, 0);

    return highestNumber[0];
  }

  private static void getHighestNumberInMatrixRec(int[][] matrix, int[] highestNumber, int i, int j) {
    if (i < matrix.length) {
      if (j < matrix.length) {
        if (matrix[i][j] > highestNumber[0]) {
          highestNumber[0] = matrix[i][j];
        }

        getHighestNumberInMatrixRec(matrix, highestNumber, i, j + 1);
      } else {
        getHighestNumberInMatrixRec(matrix, highestNumber, i + 1, 0);
      }
    }
  }

  public static void showDivisorsOfNumber(int num) {
    StringBuilder divisorsStr = new StringBuilder();

    showDivisorsOfNumberRec(divisorsStr, num, 1);

    // Delete last comma and blank space
    divisorsStr.deleteCharAt(divisorsStr.length() - 1);
    divisorsStr.deleteCharAt(divisorsStr.length() - 1);

    System.out.println(divisorsStr.toString());
  }

  private static void showDivisorsOfNumberRec(StringBuilder divisorsStr, int num, int currentNum) {
    if (currentNum < num) {
      if (num % currentNum == 0) {
        divisorsStr.append(currentNum).append(", ");
      }

      showDivisorsOfNumberRec(divisorsStr, num, currentNum + 1);
    }
  }
}
