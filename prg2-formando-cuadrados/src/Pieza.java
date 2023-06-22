package src;

public class Pieza {
  private char caracterAsociado = '0';
  private int numPiezasNecesarias = 0;

  public Pieza(char caracterAsociado, int numPiezasNecesarias) {
    this.caracterAsociado = caracterAsociado;
    this.numPiezasNecesarias = numPiezasNecesarias;
  }

  public char obtenerCaracterAsociado() {
    return this.caracterAsociado;
  }

  public int obtenerNumPiezasNecesarias() {
    return this.numPiezasNecesarias;
  }

  public void reducirNumPiezasNecesarias(int reduccion) {
    this.numPiezasNecesarias -= reduccion;
  }
}
