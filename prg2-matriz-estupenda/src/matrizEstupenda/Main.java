package matrizEstupenda;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int matrixDimension = in.nextInt();
    int[][] matrix = new int[matrixDimension][matrixDimension];

    MatrizEstupenda.getMatrixInput(in, matrix);

    if (MatrizEstupenda.checkIfMatrixIsGreat(matrix)) {
      System.out.println("La matriz es estupenda.");
    } else {
      System.out.println("La matriz NO es estupenda.");
    }

    MatrizEstupenda.showDivisorsOfNumber(MatrizEstupenda.getHighestNumberInMatrix(matrix));

    in.close();
  }
}