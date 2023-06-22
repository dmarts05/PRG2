package src;

import java.util.LinkedList;

public class Pizarra {
  private LinkedList<Character> caracteresCompartidos = new LinkedList<Character>();

  public synchronized void anadirCaracter(char caracter) {
    this.caracteresCompartidos.add(caracter);
  }

  public synchronized char obtenerCaracter() {
    if (this.caracteresCompartidos.isEmpty()) {
      return '0';
    } else {
      return this.caracteresCompartidos.removeFirst();
    }
  }

  public synchronized void imprimirCuadrado(String cuadrado) {
    System.out.print(cuadrado);
    Main.finalizar = true;
  }
}
