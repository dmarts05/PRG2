package domino;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class DominoTest {
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
  public void testDominoChip() throws IllegalArgumentException {
    Chip nonRotableChip = new Chip(3, 1, false);
    Chip rotableChip = new Chip(4, 0, true);

    // Check that rotable chips swap their numbers
    assertTrue(rotableChip.rotateChip());
    assertEquals(0, rotableChip.getChipFirstNum());
    assertEquals(4, rotableChip.getChipSecondNum());

    // Check that non-rotable chips don't swap their numbers
    assertFalse(nonRotableChip.rotateChip());
    assertEquals(3, nonRotableChip.getChipFirstNum());
    assertEquals(1, nonRotableChip.getChipSecondNum());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDominoChip1() throws IllegalArgumentException {
    Chip chip = new Chip(-1, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDominoChip2() throws IllegalArgumentException {
    Chip chip = new Chip(7, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDominoChip3() throws IllegalArgumentException {
    Chip chip = new Chip(0, -1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDominoChip4() throws IllegalArgumentException {
    Chip chip = new Chip(0, 7, false);
  }

  @Test
  public void testTwoValidDominos() {
    String testInput = "3\n4\n0 1\n3 4\n2 1\n5 6\n2 2\n3 2\n2\n4\n0 1\n3 4\n1 4\n4 4\n3 2\n5 6\n0\n";
    String expectedOutput = "SÍ\nNO\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);

    assertEquals(expectedOutput, finalOutput.toString());
  }

  @Test
  public void testValidDominoWithRepeatedChips() {
    String testInput = "4\n5\n0 1\n3 4\n2 1\n5 6\n2 2\n2 2\n3 2\n0\n";
    String expectedOutput = "SÍ\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);

    assertEquals(expectedOutput, finalOutput.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDomino1() {
    String testInput = "-1\n4\n0 1\n3 4\n2 1\n5 6\n2 2\n3 2\n2\n4\n0 1\n3 4\n1 4\n4 4\n3 2\n5 6\n0\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDomino2() {
    String testInput = "3\n-1\n0 1\n3 4\n2 1\n5 6\n2 2\n3 2\n2\n4\n0 1\n3 4\n1 4\n4 4\n3 2\n5 6\n0\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedDomino3() {
    String testInput = "3\n4\n0 1\n3 4\n-3 1\n5 6\n2 2\n3 2\n2\n4\n0 1\n3 4\n1 4\n4 4\n3 2\n5 6\n0\n";

    InputStream input = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(input);

    ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(finalOutput);
    System.setOut(output);

    Main.main(new String[0]);
  }
}
