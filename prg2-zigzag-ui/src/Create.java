import java.awt.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create {
    private JFrame frame;
    private JPanel optionsPanel;
    private JPanel zigzagGridPanel;
    private JPanel footerPanel;
    private JSpinner rowsSpinner;
    private JSpinner columnsSpinner;

    private JFileChooser fileChooser;

    private UndoManager um = new UndoManager();

    private int zigzagGridRows;
    private int zigzagGridColumns;

    public Create() {
        // *** OPTIONS PANEL ***

        // Rows label
        JLabel rowsLabel = new JLabel("FILAS: ");
        // Rows spinner
        rowsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 9, 1));

        // Columns label
        JLabel columnsLabel = new JLabel("COLUMNAS: ");
        // Columns spinner
        columnsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 9, 1));

        // Set size button
        JButton setSizeBtn = new JButton("CAMBIAR/RESETEAR");
        setSizeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateZigzagGrid(null);
            }
        });

        // File chooser (save Zigzag grid)
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileTxtFilter());

        // Save to file button
        SaveToFileListener stfListener = new SaveToFileListener();
        JButton saveToFileBtn = new JButton("GUARDAR ARCHIVO");
        saveToFileBtn.addActionListener(stfListener);

        // Load file button
        LoadFileListener lfListener = new LoadFileListener();
        JButton loadFileBtn = new JButton("CARGAR ARCHIVO");
        loadFileBtn.addActionListener(lfListener);

        // Resolve button
        JButton resolveBtn = new JButton("RESOLVER");
        resolveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEveryTextFieldCorrect()) {
                    JOptionPane.showMessageDialog(frame,
                            "Se debe introducir un número entre 1 y 9 en todas las celdas.", "Entrada incorrecta",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Get Zigzag object from given input
                    String str = transformZigzagGridToString(zigzagGridPanel.getComponents(), zigzagGridColumns);
                    Zigzag zigzag = getInputZigzag(str);

                    // Update resolve window with new Zigzag
                    FrameController.exitFrame("Resolver");
                    Resolve resolveWindow = new Resolve(zigzag);
                    FrameController.frames.add(resolveWindow.getFrame());

                    // Switch current window to new resolve window
                    FrameController.changeCurrentlyShownFrame("Resolver");
                }
            }
        });

        // Options Panel settings and components
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        optionsPanel.add(rowsLabel);
        optionsPanel.add(rowsSpinner);
        optionsPanel.add(columnsLabel);
        optionsPanel.add(columnsSpinner);
        optionsPanel.add(setSizeBtn);
        optionsPanel.add(saveToFileBtn);
        optionsPanel.add(loadFileBtn);
        optionsPanel.add(resolveBtn);

        // *** ZIGZAG GRID PANEL ***

        zigzagGridPanel = new JPanel();
        generateZigzagGrid(null);

        // *** FOOTER PANEL ***

        // Footer label
        JLabel footerLabel = new JLabel("dmarts05 - ULE");

        // Footer panel settings and components
        footerPanel = new JPanel();
        footerPanel.add(footerLabel);

        // *** MENU BAR ***

        ChangeWindowListener cwListener = new ChangeWindowListener();
        JMenuItem startWindowBtn = new JMenuItem("Inicio");
        startWindowBtn.addActionListener(cwListener);
        JMenuItem createWindowBtn = new JMenuItem("Crear");
        createWindowBtn.addActionListener(cwListener);
        JMenuItem resolveWindowBtn = new JMenuItem("Resolver");
        resolveWindowBtn.addActionListener(cwListener);

        JMenu changeWindowMenu = new JMenu("Cambiar Ventana");
        changeWindowMenu.add(startWindowBtn);
        changeWindowMenu.add(createWindowBtn);
        changeWindowMenu.add(resolveWindowBtn);

        UndoRedoListener urListener = new UndoRedoListener();
        JMenuItem undoBtn = new JMenuItem("Deshacer");
        undoBtn.addActionListener(urListener);

        JMenuItem redoBtn = new JMenuItem("Rehacer");
        redoBtn.addActionListener(urListener);

        JMenu undoRedoMenu = new JMenu("Deshacer/Rehacer");
        undoRedoMenu.add(undoBtn);
        undoRedoMenu.add(redoBtn);
        undoRedoMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuCanceled(MenuEvent e) {
                return;
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                return;
            }

            @Override
            public void menuSelected(MenuEvent e) {
                // Disable buttons if undo / redo is not available
                if (um.canUndo()) {
                    undoBtn.setEnabled(true);
                } else {
                    undoBtn.setEnabled(false);
                }

                if (um.canRedo()) {
                    redoBtn.setEnabled(true);
                } else {
                    redoBtn.setEnabled(false);
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(changeWindowMenu);
        menuBar.add(undoRedoMenu);

        // *** FRAME ***

        // Frame settings
        frame = new JFrame("Crear");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);

        // Arrange panels in frame
        frame.getContentPane().add(optionsPanel, BorderLayout.NORTH);
        frame.getContentPane().add(zigzagGridPanel, BorderLayout.CENTER);
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
    }

    private void generateZigzagGrid(Zigzag zigzag) {
        // Remove previous grid components
        for (Component c : zigzagGridPanel.getComponents()) {
            zigzagGridPanel.remove(c);
        }

        // Reset undo / redo lists
        um.discardAllEdits();

        Node[][] board = null;
        if (zigzag != null) {
            board = zigzag.getBoard();
        }

        // Change both spinners values if zigzag is not null
        if (zigzag != null) {
            rowsSpinner.setValue(board.length);
            columnsSpinner.setValue(board[0].length);
        }

        // Set number of rows and columns
        zigzagGridRows = (Integer) rowsSpinner.getValue();
        zigzagGridColumns = (Integer) columnsSpinner.getValue();

        // Set new layout and create new components
        zigzagGridPanel.setLayout(new GridLayout(zigzagGridRows, zigzagGridColumns, 10, 10));

        for (int i = 0; i < zigzagGridRows; i++) {
            for (int j = 0; j < zigzagGridColumns; j++) {
                // Create text field
                JTextField tf;
                if (zigzag != null) {
                    tf = new JTextField(Integer.toString(board[i][j].getValue()));
                } else {
                    tf = new JTextField("1");
                }
                tf.setFont(new Font("", Font.PLAIN, 60));
                tf.setHorizontalAlignment(JTextField.CENTER);

                // Add undo / redo functionality
                tf.getDocument().addUndoableEditListener(new UndoableEditListener() {
                    @Override
                    public void undoableEditHappened(UndoableEditEvent e) {
                        um.addEdit(e.getEdit());
                    }
                });

                // Add text field to grid
                zigzagGridPanel.add(tf);
            }
        }

        // Refresh panel
        zigzagGridPanel.revalidate();
        zigzagGridPanel.repaint();
    }

    private boolean isEveryTextFieldCorrect() {
        Pattern pattern = Pattern.compile("[1-9]");

        for (Component component : zigzagGridPanel.getComponents()) {
            JTextField tf = (JTextField) component;
            Matcher matcher = pattern.matcher(tf.getText());

            if (!matcher.find() || tf.getText().length() > 1) {
                return false;
            }
        }

        return true;
    }

    public JFrame getFrame() {
        return frame;
    }

    private String transformZigzagGridToString(Component[] tfComponents, int columns) {
        StringBuffer output = new StringBuffer();

        int currentColumn = 1;
        for (Component component : tfComponents) {
            JTextField tf = (JTextField) component;
            output.append(tf.getText()).append(" ");

            if (currentColumn < columns) {
                currentColumn++;
            } else {
                output.deleteCharAt(output.length() - 1);
                output.append("\n");
                currentColumn = 1;
            }
        }

        return output.toString();
    }

    private Zigzag getInputZigzag(String str) {
        Zigzag zigzag = null;
        boolean validInput = true;
        int rows = 0;
        int columns = 0;

        ArrayList<String[]> inputRows = new ArrayList<String[]>();

        String[] strRows = str.split("\n");

        for (int i = 0; i < strRows.length; i++) {
            // Get line from input and transform it into an array by spliting black spaces
            String[] currentLine = strRows[i].split(" ");

            // Store line
            inputRows.add(currentLine);
        }

        // Get number of rows
        rows = inputRows.size();

        // Rows need to be between 1 and 10
        if (rows <= 0 || rows >= 11) {
            validInput = false;
        }

        // Get number of columns
        if (validInput) {
            columns = inputRows.get(0).length;
        }

        // Columns need to be between 1 and 10
        if (columns <= 0 || columns >= 11) {
            validInput = false;
        }

        // Create board containing valid nodes
        Node board[][] = new Node[rows][columns];
        int currentRow = 0;
        for (String[] inputRow : inputRows) {
            int currentColumn = 0;
            for (int i = 0; i < inputRow.length && validInput; i++) {
                // Check if columns are out of bounds
                if (currentColumn >= columns) {
                    // Columns are inconsistent
                    validInput = false;
                    break;
                }

                int nodeValue = 0;
                try {
                    nodeValue = Integer.parseInt(inputRow[i]);
                } catch (NumberFormatException e) {
                    // Not an integer, exiting...
                    validInput = false;
                    break;
                }

                Node node = null;
                try {
                    node = new Node(nodeValue, currentRow, currentColumn);
                } catch (IllegalArgumentException e) {
                    // nodeValue is not between 1 and 9
                    validInput = false;
                    break;
                }

                board[currentRow][currentColumn] = node;
                currentColumn++;
            }

            currentRow++;
        }

        // Check that created board has no empty cells
        for (int i = 0; i < rows && validInput; i++) {
            for (int j = 0; j < columns && validInput; j++) {
                if (board[i][j] == null) {
                    // Columns are inconsistent
                    validInput = false;
                    break;
                }
            }
        }

        if (validInput) {
            zigzag = new Zigzag(board);
        }

        return zigzag;
    }

    private Zigzag getInputZigzag(Scanner fr) {
        Zigzag zigzag = null;
        boolean validInput = true;
        int rows = 0;
        int columns = 0;

        ArrayList<String[]> inputRows = new ArrayList<String[]>();

        while (fr.hasNextLine()) {
            // Get line from input and transform it into an array by spliting black spaces
            String[] currentLine = fr.nextLine().split(" ");

            // Store tokens
            inputRows.add(currentLine);
        }

        // Get number of rows
        rows = inputRows.size();

        // Rows need to be between 1 and 10
        if (rows <= 0 || rows >= 11) {
            validInput = false;
        }

        // Get number of columns
        if (validInput) {
            columns = inputRows.get(0).length;
        }

        // Columns need to be between 1 and 10
        if (columns <= 0 || columns >= 11) {
            validInput = false;
        }

        // Create board containing valid nodes
        Node board[][] = new Node[rows][columns];
        int currentRow = 0;
        for (String[] inputRow : inputRows) {
            int currentColumn = 0;
            for (int i = 0; i < inputRow.length && validInput; i++) {
                // Check if columns are out of bounds
                if (currentColumn >= columns) {
                    // Columns are inconsistent
                    validInput = false;
                    break;
                }

                int nodeValue = 0;
                try {
                    nodeValue = Integer.parseInt(inputRow[i]);
                } catch (NumberFormatException e) {
                    // Not an integer, exiting...
                    validInput = false;
                    break;
                }

                Node node = null;
                try {
                    node = new Node(nodeValue, currentRow, currentColumn);
                } catch (IllegalArgumentException e) {
                    // nodeValue is not between 1 and 9
                    validInput = false;
                    break;
                }

                board[currentRow][currentColumn] = node;
                currentColumn++;
            }

            currentRow++;
        }

        // Check that created board has no empty cells
        for (int i = 0; i < rows && validInput; i++) {
            for (int j = 0; j < columns && validInput; j++) {
                if (board[i][j] == null) {
                    // Columns are inconsistent
                    validInput = false;
                    break;
                }
            }
        }

        if (validInput) {
            zigzag = new Zigzag(board);
        } else {
            JOptionPane.showMessageDialog(frame, "El archivo introducido no es válido.", "Archivo no válido",
                    JOptionPane.ERROR_MESSAGE);
        }

        return zigzag;
    }

    private class ChangeWindowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem btn = (JMenuItem) e.getSource();
            String btnName = btn.getText();

            switch (btnName) {
                case "Inicio":
                case "Crear":
                case "Resolver":
                    FrameController.changeCurrentlyShownFrame(btnName);
                    break;
                default:
                    break;
            }

        }
    }

    private class UndoRedoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem btn = (JMenuItem) e.getSource();
            String btnName = btn.getText();

            switch (btnName) {
                case "Deshacer":
                    if (um.canUndo()) {
                        um.undo();
                    }
                    break;
                case "Rehacer":
                    if (um.canRedo()) {
                        um.redo();
                    }
                    break;
                default:
                    break;
            }

        }
    }

    private class SaveToFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle("Elige dónde guardar tu puzle Zigzag");

            if (!isEveryTextFieldCorrect()) {
                JOptionPane.showMessageDialog(frame, "Se debe introducir un número entre 1 y 9 en todas las celdas.",
                        "Entrada incorrecta", JOptionPane.ERROR_MESSAGE);
            } else if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                // Get needed components and saved file to write Zigzag grid contents
                Component[] tfComponents = zigzagGridPanel.getComponents();

                // Add .txt extension to file if it's not present
                String path = fileChooser.getSelectedFile().getAbsolutePath();

                if (!path.substring(path.lastIndexOf(".") + 1).equals("txt")) {
                    path += ".txt";
                }

                File file = new File(path);

                int confirmOverwrite = 0;
                boolean overwrite = file.exists();
                if (overwrite) {
                    // Check if users wants to overwrite the file
                    confirmOverwrite = JOptionPane.showConfirmDialog(frame,
                            "¿Desea sobrescribir el archivo seleccionado?", "Confirmar sobrescritura",
                            JOptionPane.YES_NO_OPTION);
                }

                // Write Zigzag grid contents into file
                if (overwrite && confirmOverwrite == JOptionPane.NO_OPTION) {
                    // Can't save file because user doesn't want to overwrite it
                    JOptionPane.showMessageDialog(frame, "No fue posible guardar el puzle Zigzag dado.",
                            "Sobrescritura cancelada", JOptionPane.ERROR_MESSAGE);
                } else if (!saveZigzagGrid(tfComponents, file, zigzagGridColumns)) {
                    // Couldn't write Zigzag grid contents into file
                    JOptionPane.showMessageDialog(frame, "No fue posible guardar el puzle Zigzag dado.", "Error de E/S",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Successfully saved file and Zigzag grid contents
                    JOptionPane.showMessageDialog(frame, "Puzle Zigzag guardado.", "Archivo guardado",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private boolean saveZigzagGrid(Component[] tfComponents, File file, int columns) {
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                String output = transformZigzagGridToString(tfComponents, columns);

                bw.write(output);
                bw.flush();
                bw.close();

                return true;
            } catch (IOException eIO) {
                eIO.printStackTrace();
                return false;
            }
        }
    }

    private class LoadFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle("Elige qué puzle Zigzag cargar");
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    // Load selected file
                    File file = fileChooser.getSelectedFile();

                    // Use file to generate new grid
                    Scanner fr = new Scanner(file);
                    Zigzag zigzag = getInputZigzag(fr);
                    generateZigzagGrid(zigzag);
                } catch (IOException eIO) {
                    eIO.printStackTrace();
                }
            }
        }

    }
}
