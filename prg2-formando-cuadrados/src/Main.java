package src;

import java.util.Scanner;

public class Main {

	public static volatile boolean finalizar = false;

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);

		Pizarra pizarra = new Pizarra();

		int numHilosProductores = sc.nextInt();
		Thread[] prods = new Thread[numHilosProductores];
		for (int i = 0; i < numHilosProductores; i++) {
			prods[i] = new HiloProductor(sc.nextInt(16), sc.nextInt(16), pizarra);
			prods[i].start();
		}

		int numHilosCuadradosSimples = sc.nextInt();
		Thread[] cuadSimples = new Thread[numHilosCuadradosSimples];
		for (int i = 0; i < numHilosCuadradosSimples; i++) {
			cuadSimples[i] = new Thread(new HiloCuadradoSimple(i, pizarra));
			cuadSimples[i].start();
		}

		int numHilosCuadradosDobles = sc.nextInt();
		Thread[] cuadDobles = new Thread[numHilosCuadradosDobles];
		for (int i = 0; i < numHilosCuadradosDobles; i++) {
			cuadDobles[i] = new Thread(new HiloCuadradoDoble(i, pizarra));
			cuadDobles[i].start();
		}

		int duracion = sc.nextInt();
		int slice = 100;
		while (duracion > 0 && !finalizar) {
			Thread.sleep(100);
			duracion -= slice;
		}

		for (int i = 0; i < numHilosProductores; i++) {
			prods[i].interrupt();
		}
		for (int i = 0; i < numHilosProductores; i++) {
			prods[i].join();
		}

		for (int i = 0; i < numHilosCuadradosSimples; i++) {
			cuadSimples[i].interrupt();
		}
		for (int i = 0; i < numHilosCuadradosSimples; i++) {
			cuadSimples[i].join();
		}

		for (int i = 0; i < numHilosCuadradosDobles; i++) {
			cuadDobles[i].interrupt();
		}
		for (int i = 0; i < numHilosCuadradosDobles; i++) {
			cuadDobles[i].join();
		}
	}
}
