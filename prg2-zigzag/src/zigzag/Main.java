package zigzag;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    Zigzag zigzag = IO.getInputZigzag(in);
    if (zigzag != null) {
      ArrayList<Node[][]> solutions = new ArrayList<Node[][]>(
          zigzag.solveZigzag());

      System.out.println(solutions.size());

      for (int i = 0; i < solutions.size(); i++) {
        if (i != 0 && i != solutions.size()) {
          System.out.print("\n");
        }

        System.out.print(IO.zigzagSolutionToString(solutions.get(i)));
      }
    }

    in.close();
  }
}