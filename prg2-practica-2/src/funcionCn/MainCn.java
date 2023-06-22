package funcionCn;
/**
1. Se desea calcular el valor C(n) dado por
  C(n) = 2/n Sum_{i=0}^{n-1}C(i) + n con C(0) = 1.
Se pide:
 1. Dar una solución recursiva del problema
 2. Dar una solución recursiva sin uso de ningún bucle
 3. Dar una solución iterativa
 4. ¿cuál de las dos es computacionalmente más interesante?
 5. ¿Puedes conseguir una solución con complejidad lineal en n?
 6. ¿puedes conseguir una solución recursiva lineal en n? 

Analizar con un profiler en Eclipse (JVM Monitor) las llamadas a las
4 funciones con n=30 
 */

import java.util.Scanner;

public class MainCn {
	public static void main (String[] args){
		Scanner scan = new Scanner(System.in);
		
		int n = scan.nextInt();
		
		scan.close();
		
		FuncionCn funcionCn = new FuncionCn();
		
		System.out.format("Implementación recursiva directa: %.4f\n", funcionCn.CnRD(n));
		System.out.format("Implementación completamente recursiva: %.4f\n", funcionCn.CnCRD(n));
		System.out.format("Implementación iterativa directa: %.4f\n",funcionCn.CnI(n));
		System.out.format("Implementación iterativa lineal: %.4f\n",funcionCn.CnIterativaLineal(n));
		System.out.format("Implementación recursiva lineal: %.4f\n",funcionCn.CnRecursivaLineal(n));
	}
}	
