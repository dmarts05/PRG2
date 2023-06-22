package tests;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import zigzag.Main;

public class MainTests {
    private static InputStream in;
    private static PrintStream out;

    /**
     * Este método se ejecuta antes de la ejecución de cada test.
     * Permite almacenar los flujos que tiene la máquina java
     * para la entrada y salida estándar
     * 
     * @throws Exception
     */
    @Before
    public void realizaAntesDeCadaTest() throws Exception {
        in = System.in;
        out = System.out;
    }

    /**
     * Este método se ejecuta después de cada test. Permite restaurar
     * los flujos de entrada y salida estándar
     * 
     * @throws Exception
     */
    @After
    public void realizaDespuésDeCadaTest() throws Exception {
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    public void testEjemplo1() {

        String entradaTest = "1 3 1\n" +
                "2 2 2\n" +
                "3 1 3";
        String salidaEsperadaTest = "2\n" +
                "1 3-1\n" +
                "|/ / \n" +
                "2 2 2\n" +
                " / /|\n" +
                "3-1 3\n" +
                "\n" +
                "1 3-1\n" +
                "| | |\n" +
                "2 2 2\n" +
                "| | |\n" +
                "3-1 3\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());

    }

    @Test
    public void sinEntrada() {

        String entradaTest = "";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());

    }

    @Test
    public void matrizDeLetras() {

        String entradaTest = "a c a\n" +
                "b b b\n" +
                "c a c\n";

        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());

    }

    @Test
    public void matrizDeLetrasYNumeros() {

        String entradaTest = "1 3 1\n" +
                "2 b 2\n" +
                "3 1 3\n";

        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());

    }

    @Test
    public void matrizConDecimales() {

        String entradaTest = "1 3 1\n" +
                "2 2.5 2\n" +
                "3 1 3\n";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizOrden1() {

        String entradaTest = "1";
        String salidaEsperadaTest = "1\n" +
                "1\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizOrden1Letra() {

        String entradaTest = "a";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizOrden1Decimal() {

        String entradaTest = "5.2";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizOrden1Negativa() {

        String entradaTest = "-6";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizOrden1Cero() {

        String entradaTest = "0";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizConNegativos() {

        String entradaTest = "1 3 1\n" +
                "2 2 2\n" +
                "3 -1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizConCero() {

        String entradaTest = "1 0 1\n" +
                "2 2 2\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizConNúmerosAltos() {

        String entradaTest = "1 3 1\n" +
                "2 2 20\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void matrizMezcladaMala() {

        String entradaTest = "1 0 1\n" +
                "b 2 20\n" +
                "3 1 -3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void filasIncoherentes() {

        String entradaTest = "1 0 1\n" +
                "2 2 2 2\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void filaVacia() {

        String entradaTest = "1 3 1\n" +
                "2 2 2\n" +
                "\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void numeroSeparadoPor2Espacios() {

        String entradaTest = "1 3 1\n" +
                "2 2  2\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void numeroSeparadoresRaros() {

        String entradaTest = "1 3 1\n" +
                "2 2-2\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void numeroSeparadoresRaros2() {

        String entradaTest = "1 3 1\n" +
                "2 2,2\n" +
                "3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void espacioAntesDeLinea() {

        String entradaTest = " 1 3 1\n" +
                " 2 2,2\n" +
                " 3 1 3";
        String salidaEsperadaTest = "Entrada incorrecta.\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void testEjemplo() {
        String entradaTest = "1 3 1\n";
        String salidaEsperadaTest = "0\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void testCruce3x2() {
        String entradaTest = "1 3\n" +
                "4 2\n" +
                "5 6\n";
        String salidaEsperadaTest = "0\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void testCruceSolo1Numero() {
        String entradaTest = "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1\n";
        String salidaEsperadaTest = "0\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void test4x4() {
        String entradaTest = "7 6 2 1\n" +
                "4 8 5 3\n" +
                "5 3 9 4\n" +
                "7 6 2 1\n";
        String salidaEsperadaTest = "0\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void testEjemplo2() {
        String entradaTest = "1 2 1\n";
        String salidaEsperadaTest = "1\n" +
                "1-2-1\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void testLinea() {
        String entradaTest = "1 2 3 4 5 6 7 8 9\n";
        String salidaEsperadaTest = "1\n" +
                "1-2-3-4-5-6-7-8-9\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void test3x3UnaSolucion() {
        String entradaTest = "1 2 3\n" +
                "6 5 4\n" +
                "7 8 9\n";
        String salidaEsperadaTest = "1\n" +
                "1-2-3\n" +
                "    |\n" +
                "6-5-4\n" +
                "|    \n" +
                "7-8-9\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void test3x3() {
        String entradaTest = "1 2 3\n" +
                "6 5 4\n" +
                "7 8 9\n";
        String salidaEsperadaTest = "1\n" +
                "1-2-3\n" +
                "    |\n" +
                "6-5-4\n" +
                "|    \n" +
                "7-8-9\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

    @Test
    public void test5x5() {
        String entradaTest = "1 2 6 7 6\n" +
                "3 5 8 5 7\n" +
                "4 9 4 8 4\n" +
                "1 3 9 3 5\n" +
                "2 1 2 6 7\n";
        String salidaEsperadaTest = "1\n" +
                "1-2 6-7 6\n" +
                " / / / /|\n" +
                "3 5 8 5 7\n" +
                "|/ / / / \n" +
                "4 9 4 8 4\n" +
                " / / / /|\n" +
                "1 3 9 3 5\n" +
                "|/ / / / \n" +
                "2 1-2 6-7\n";

        InputStream nuevo_in = new ByteArrayInputStream(entradaTest.getBytes());
        System.setIn(nuevo_in);
        ByteArrayOutputStream salidaRealTest = new ByteArrayOutputStream();
        PrintStream nuevo_out = new PrintStream(salidaRealTest);
        System.setOut(nuevo_out);

        String[] args = { "" };
        Main.main(args);

        assertEquals(salidaEsperadaTest, salidaRealTest.toString());
    }

}
