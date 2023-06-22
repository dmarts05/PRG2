package src;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MainTest {
  private static InputStream in;
  private static PrintStream out;

  @Before
  public void setUp() throws Exception {
    in = System.in;
    out = System.out;
  }

  @After
  public void windDown() throws Exception {
    System.setIn(in);
    System.setOut(out);
  }

  @Test
  public void testMain1() throws InterruptedException {
    String testInput = "2\n" +
        "2500 2518\n" +
        "2550 255D\n" +
        "1 1\n" +
        "1000\n";
    String expectedOutput = "┌─┐\n" +
        "│0│\n" +
        "└─┘\n" +
        "╔═╗\n" +
        "║0║\n" +
        "╚═╝\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);

    assertEquals(expectedOutput, finalOutput.toString());
  }

  @Test
  public void testMain2() throws InterruptedException {
    String testInput = "2\n" +
        "2500 2518\n" +
        "2550 255D\n" +
        "3 3\n" +
        "1000\n";
    String expectedOutput = "┌─┐\n" +
        "│2│\n" +
        "└─┘\n" +
        "╔═╗\n" +
        "║0║\n" +
        "╚═╝\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);

    assertEquals(expectedOutput, finalOutput.toString());
  }
}
