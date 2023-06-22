package src;

import java.util.concurrent.ThreadLocalRandom;

public class HiloProductor extends Thread {
  private int topeInferior = 0;
  private int topeSuperior = 0;
  private Pizarra pizarra;
  private final char[] caracteresDisponibles = { '\u2500', '\u2502', '\u250c', '\u2510', '\u2514', '\u2518', '\u2550',
      '\u2551', '\u2554', '\u2557', '\u255A', '\u255D' };

  public HiloProductor(int topeInferior, int topeSuperior, Pizarra pizarra) {
    // Asignamos índices inferiores y superiores del array de caracteres disponibles
    for (int i = 0; i < caracteresDisponibles.length; i++) {
      if (caracteresDisponibles[i] == topeInferior) {
        this.topeInferior = i;
      } else if (caracteresDisponibles[i] == topeSuperior) {
        this.topeSuperior = i;
      }
    }

    this.pizarra = pizarra;
  }

  public void run() {
    try {
      while (!Main.finalizar) {
        Thread.sleep(1);
        char caracterGenerado = generarCaracter();
        anadirCaracterPizarra(caracterGenerado);
      }
    } catch (InterruptedException e) {
      return;
    }
  }

  private char generarCaracter() {
    return this.caracteresDisponibles[ThreadLocalRandom.current().nextInt(this.topeInferior, this.topeSuperior + 1)];
  }

  private void anadirCaracterPizarra(char caracter) throws InterruptedException {
    this.pizarra.anadirCaracter(caracter);
  }
}
