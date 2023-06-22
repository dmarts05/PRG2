package src;

public class HiloCuadradoSimple extends Thread {
  private int id = 0;
  private Pizarra pizarra;

  // Contadores de piezas restantes
  // ┌
  private int contadorPieza1 = 1;
  // ─
  private int contadorPieza2 = 2;
  // ┐
  private int contadorPieza3 = 1;
  // │
  private int contadorPieza4 = 2;
  // └
  private int contadorPieza5 = 1;
  // ┘
  private int contadorPieza6 = 1;

  public HiloCuadradoSimple(int id, Pizarra pizarra) {
    this.id = id % 9;
    this.pizarra = pizarra;
  }

  public void run() {
    try {
      while (!Main.finalizar) {
        Thread.sleep(1);
        char caracterObtenido = obtenerCaracterPizarra();
        usarCaracter(caracterObtenido);

        if (!esCuadradoIncompleto()) {
          imprimirCuadrado();
        }
      }

    } catch (InterruptedException e) {
      return;
    }
  }

  private boolean esCuadradoIncompleto() {
    return this.contadorPieza1 > 0 || this.contadorPieza2 > 0 || this.contadorPieza3 > 0 || this.contadorPieza4 > 0
        || this.contadorPieza5 > 0 || this.contadorPieza6 > 0;
  }

  private char obtenerCaracterPizarra() throws InterruptedException {
    return this.pizarra.obtenerCaracter();
  }

  private void usarCaracter(char caracter) {
    switch (caracter) {
      case '\u250c':
        contadorPieza1--;
        break;
      case '\u2500':
        contadorPieza2--;
        break;
      case '\u2510':
        contadorPieza3--;
        break;
      case '\u2502':
        contadorPieza4--;
        break;
      case '\u2514':
        contadorPieza5--;
        break;
      case '\u2518':
        contadorPieza6--;
        break;
      default:
        break;
    }
  }

  private void imprimirCuadrado() {
    StringBuffer output = new StringBuffer();

    output.append("\u250c\u2500\u2510\n").append("\u2502" + this.id + "\u2502\n").append("\u2514\u2500\u2518\n");

    this.pizarra.imprimirCuadrado(output.toString());
  }
}
