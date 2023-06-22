package funcionCn;

/**
 * Define los  métodos que implentan las distintas implementaciones de la función Cn
 **/
 
public interface IFuncionCn{
/**
	 * Implementación recursiva directa
	 * 
	 * Prec:
	 * @param n >= 0  
	 * @return devuelve el cálculo de la función C definida previamente
*/
	public double CnRD ( int n);

	/**
	 * Implementación de Cn completamente recursiva, sin uso de ningún bucle.
	 * 
	 * @param n >= 0
	 * @return devuelve el cálculo de la función C definida previamente
	 */
	public double CnCRD ( int n);

	
	/**
	 * Implementación de Cn iterativa directa usando un array.
	 * 
	 * @param n >= 0
	 * @return devuelve el cálculo de la función Cn definida previamente
	 */
	public  double CnI ( int n );
	
	/**
	 * Solución iterativa lineal de Cn. Esta solución se basa en la propiedad:
	 *    suma[n] = \sum_{i=0}^{n} C(i)
	 *            = C(n) +  \sum_{i=0}^{n-1} C(i) //sustituyendo C(n) y el sumatorio
	 *            = (2/n)*suma[n-1] + n + suma[n-1]
	 *    Esta def recurrente permite usar una variable acumuladora para calcular suma[n]
	 *    Ya no hay un doble for y por tanto se pasa de complejidad  cuadrática a complejidad lineal        
	 *            
	 * @param n >= 0
	 * @return
	 */
	public double CnIterativaLineal ( int n );
	
	/**
	 * La misma propiedad anterior permite una definición recursiva
	 * usando un método recursivo auxiliar para calcular el sumatorio. 
	 * 
	 * @param n >= 0
	 * @return
	 */
	public double CnRecursivaLineal ( int n );

}
