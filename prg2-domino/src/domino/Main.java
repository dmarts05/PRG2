package domino;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    ArrayList<Domino> dominos = IO.getInputDominos(in);

    for (Domino domino : dominos) {
      if (domino.solveDomino()) {
        System.out.println("SÍ");
      } else {
        System.out.println("NO");
      }
    }

    in.close();
  }
}