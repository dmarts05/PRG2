package src;

public class HiloCuadradoDoble extends Thread {
  private int id = 0;
  private Pizarra pizarra;

  // Contadores de piezas restantes
  // ╔
  private int contadorPieza1 = 1;
  // ═
  private int contadorPieza2 = 2;
  // ╗
  private int contadorPieza3 = 1;
  // ║
  private int contadorPieza4 = 2;
  // ╚
  private int contadorPieza5 = 1;
  // ╝
  private int contadorPieza6 = 1;

  public HiloCuadradoDoble(int id, Pizarra pizarra) {
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
      case '\u2554':
        contadorPieza1--;
        break;
      case '\u2550':
        contadorPieza2--;
        break;
      case '\u2557':
        contadorPieza3--;
        break;
      case '\u2551':
        contadorPieza4--;
        break;
      case '\u255A':
        contadorPieza5--;
        break;
      case '\u255D':
        contadorPieza6--;
        break;
      default:
        break;
    }
  }

  private void imprimirCuadrado() {

    StringBuffer output = new StringBuffer();

    output.append("\u2554\u2550\u2557\n").append("\u2551" + this.id + "\u2551\n").append("\u255A\u2550\u255D\n");

    this.pizarra.imprimirCuadrado(output.toString());
  }
}
