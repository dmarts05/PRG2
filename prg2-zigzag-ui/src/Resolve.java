import java.awt.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Resolve {
    private Zigzag zigzag;
    private ArrayList<Node[][]> solutions;

    private JFrame frame;
    private JPanel optionsPanel;
    private JPanel zigzagGridPanel;
    private int zigzagGridRows;
    private int zigzagGridColumns;
    private JPanel footerPanel;

    private JFileChooser fileChooser;

    private ArrayList<JCell> undoBtnsList = new ArrayList<JCell>();
    private ArrayList<JCell> redoBtnsList = new ArrayList<JCell>();

    private ArrayList<JLabel> undoLabelsList = new ArrayList<JLabel>();
    private ArrayList<JLabel> redoLabelsList = new ArrayList<JLabel>();

    private UndoRedoManager urm = new UndoRedoManager();

    public Resolve() {
        // *** OPTIONS PANEL ***

        // File chooser (save Zigzag grid)
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Elige tu puzle Zigzag");
        fileChooser.setFileFilter(new FileTxtFilter());

        // Load Zigzag grid file button
        LoadFileListener lfListener = new LoadFileListener();
        JButton loadFileBtn = new JButton("CARGAR ARCHIVO");
        loadFileBtn.addActionListener(lfListener);

        // Reset Zigzag grid button
        JButton resetBtn = new JButton("RESETEAR");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (urm.canUndo()) {
                    urm.undo();
                }

                undoBtnsList.clear();
                undoLabelsList.clear();
                redoBtnsList.clear();
                redoLabelsList.clear();
            }
        });

        // Show solutions button
        JButton solutionsBtn = new JButton("SOLUCIONES");
        solutionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolutionsScrollDialog dialog = new SolutionsScrollDialog();
                dialog.setVisible(true);
            }
        });

        // Options Panel settings and components
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        optionsPanel.add(loadFileBtn);
        optionsPanel.add(resetBtn);
        optionsPanel.add(solutionsBtn);

        // *** ZIGZAG GRID PANEL ***

        zigzagGridPanel = new JPanel();

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
                if (urm.canUndo()) {
                    undoBtn.setEnabled(true);
                } else {
                    undoBtn.setEnabled(false);
                }

                if (urm.canRedo()) {
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
        frame = new JFrame("Resolver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);

        // Arrange panels in frame
        frame.getContentPane().add(optionsPanel, BorderLayout.NORTH);
        frame.getContentPane().add(zigzagGridPanel, BorderLayout.CENTER);
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
    }

    public Resolve(Zigzag zigzag) {
        this();
        this.zigzag = zigzag;
        generateZigzagGrid();
    }

    public JFrame getFrame() {
        return frame;
    }

    private void generateZigzagGrid() {
        // Get Zigzag solutions
        SolveZigzagWorker worker = new SolveZigzagWorker();
        worker.execute();
        try {
            solutions = worker.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Remove previous grid components
        for (Component c : zigzagGridPanel.getComponents()) {
            zigzagGridPanel.remove(c);
        }

        // Reset undo/redo lists
        undoBtnsList.clear();
        redoBtnsList.clear();
        undoLabelsList.clear();
        redoLabelsList.clear();

        // Get Zigzag's board and its rows and columns
        Node[][] board = zigzag.getBoard();

        zigzagGridRows = board.length * 2 - 1;
        zigzagGridColumns = board[0].length * 2 - 1;

        // Set new layout and create new components
        zigzagGridPanel.setLayout(new GridLayout(zigzagGridRows, zigzagGridColumns, 10, 10));
        int nodeRow = 0;
        int nodeColumn = 0;
        for (int i = 0; i < zigzagGridRows; i++) {
            for (int j = 0; j < zigzagGridColumns; j++) {
                // Odd rows are filled only with labels and even rows with buttons and labels
                if (i % 2 != 0) {
                    // Create label (where arrows will be drawn)
                    JLabel label = new JLabel("");
                    if (Math.max(board.length, board[0].length) >= 8) {
                        label.setFont(new Font("", Font.PLAIN, 15));
                    } else {
                        label.setFont(new Font("", Font.PLAIN, 40));
                    }
                    label.setHorizontalAlignment(JLabel.CENTER);

                    // Add label to grid
                    zigzagGridPanel.add(label);
                } else {
                    // Create button
                    Node node = board[nodeRow][nodeColumn];
                    JCell cell = new JCell(Integer.toString(node.getValue()), nodeRow, nodeColumn, i, j);
                    if (Math.max(board.length, board[0].length) >= 8) {
                        cell.setFont(new Font("", Font.PLAIN, 15));
                    } else {
                        cell.setFont(new Font("", Font.PLAIN, 40));
                    }

                    CellListener cListener = new CellListener();
                    cell.addActionListener(cListener);

                    // Add button to grid
                    zigzagGridPanel.add(cell);

                    // There is no need to create labels for last column
                    if (j != zigzagGridColumns - 1) {
                        // Create label (where arrows will be drawn)
                        JLabel label = new JLabel("");
                        if (Math.max(board.length, board[0].length) >= 8) {
                            label.setFont(new Font("", Font.PLAIN, 15));
                        } else {
                            label.setFont(new Font("", Font.PLAIN, 40));
                        }
                        label.setHorizontalAlignment(JLabel.CENTER);

                        // Add label to grid
                        zigzagGridPanel.add(label);

                        // We add two components if it's an even row, so j += 2 per iteration
                        j++;
                    }

                    nodeColumn++;
                }
            }
            nodeColumn = 0;

            // Update node row only when it will be used
            if (i % 2 == 0) {
                nodeRow++;
            }
        }

        // Refresh panel
        zigzagGridPanel.revalidate();
        zigzagGridPanel.repaint();
    }

    private boolean isEveryRequiredCellDisabled() {
        Node[][] board = zigzag.getBoard();

        for (Component component : zigzagGridPanel.getComponents()) {
            // Skip label checking
            if (component instanceof JLabel) {
                continue;
            }

            JCell cell = (JCell) component;
            Node node = board[cell.nodeRow][cell.nodeColumn];

            // Check if current cell is enabled and it's not the last one
            if (cell.isEnabled() && !node.equals(board[board.length - 1][board[0].length - 1])) {
                // This solution lacks disabled cells
                return false;
            }
        }

        return true;
    }

    private String transformLinkType(int linkType) {
        switch (linkType) {
            case 1:
            case 9:
                return "\\";
            case 2:
            case 8:
                return "|";
            case 3:
            case 7:
                return "/";
            case 4:
            case 6:
                return "-";

            default:
                return "";
        }
    }

    private Component[][] getZigzagGridPanelComponentsMatrix() {
        Component[][] components = new Component[zigzagGridRows][zigzagGridColumns];

        int currentRow = 0;
        int currentColumn = 0;
        for (Component component : zigzagGridPanel.getComponents()) {
            components[currentRow][currentColumn] = component;

            currentColumn++;
            if (currentColumn >= zigzagGridColumns) {
                currentColumn = 0;
                currentRow++;
            }
        }

        return components;
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

    private class LoadFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    // Load selected file
                    File file = fileChooser.getSelectedFile();

                    // Transform file input into a Zigzag object
                    Scanner fr = new Scanner(file);
                    zigzag = getInputZigzag(fr);

                    // Generate new Zigzag Grid
                    generateZigzagGrid();

                } catch (IOException eIO) {
                    eIO.printStackTrace();
                }
            }
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
    }

    private class SolveZigzagWorker extends SwingWorker<ArrayList<Node[][]>, Void> {
        @Override
        protected ArrayList<Node[][]> doInBackground() throws Exception {
            Zigzag aux = new Zigzag(zigzag.getCopyOfBoard());
            return aux.solveZigzag();
        }
    }

    private class JCell extends JButton {
        public int nodeRow;
        public int nodeColumn;
        public int cellRow;
        public int cellColumn;

        public JCell(String name, int nodeRow, int nodeColumn, int cellRow, int cellColumn) {
            super(name);
            this.nodeRow = nodeRow;
            this.nodeColumn = nodeColumn;
            this.cellRow = cellRow;
            this.cellColumn = cellColumn;
        }
    }

    private class CellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Note: Each button can only be pressed once
            Node[][] board = zigzag.getBoard();
            JCell cell = (JCell) e.getSource();
            Node currentNode = board[cell.nodeRow][cell.nodeColumn];

            if (undoBtnsList.isEmpty() && !currentNode.equals(board[0][0])) {
                // First button has not been pressed yet
                JOptionPane.showMessageDialog(frame,
                        "Movimiento no permitido, debes presionar primero el botón de la esquina superior izquierda.",
                        "Movimiento no permitido", JOptionPane.ERROR_MESSAGE);
            } else if (currentNode.equals(board[0][0])) {
                // First button pressed
                cell.setEnabled(false);

                // Clear redo lists
                redoBtnsList.clear();
                redoLabelsList.clear();

                // Add button to undo list
                undoBtnsList.add(cell);
            } else if (currentNode.equals(board[board.length - 1][board[0].length - 1])) {
                // Last button pressed
                JCell previousBtn = undoBtnsList.get(undoBtnsList.size() - 1);
                Node previousNode = board[previousBtn.nodeRow][previousBtn.nodeColumn];

                if (isEveryRequiredCellDisabled() && zigzag.trySetLink(previousNode, currentNode)) {
                    // User got a solution
                    cell.setEnabled(false);

                    // Clear redo lists
                    redoBtnsList.clear();
                    redoLabelsList.clear();

                    // Add button to undo list
                    undoBtnsList.add(cell);

                    // * Create GUI link (show link type) *
                    Node node = board[cell.nodeRow][cell.nodeColumn];
                    // Get required character
                    String linkType = transformLinkType(node.getLinkType());
                    // Get target label
                    JLabel label = getLabelByLinkType(cell, node.getLinkType());
                    // Put link in label
                    label.setText(linkType);

                    // Add label to undo list
                    undoLabelsList.add(label);

                    JOptionPane.showMessageDialog(frame, "¡Puzle completado!", "Enhorabuena",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // This is not a valid solution
                    JOptionPane.showMessageDialog(frame,
                            "Movimiento no permitido, este debería ser el último botón que presiones.",
                            "Movimiento no permitido", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Set link
                JCell previousBtn = undoBtnsList.get(undoBtnsList.size() - 1);
                Node previousNode = board[previousBtn.nodeRow][previousBtn.nodeColumn];

                if (!zigzag.trySetLink(previousNode, currentNode)) {
                    // Link failed
                    JOptionPane.showMessageDialog(frame, "Movimiento no permitido, inténtalo de nuevo.",
                            "Movimiento no permitido", JOptionPane.ERROR_MESSAGE);
                } else {
                    cell.setEnabled(false);

                    // Clear redo lists
                    redoBtnsList.clear();
                    redoLabelsList.clear();

                    // Add button to undo list
                    undoBtnsList.add(cell);

                    // * Create GUI link (show link type) *
                    Node node = board[cell.nodeRow][cell.nodeColumn];
                    // Get required character
                    String linkType = transformLinkType(node.getLinkType());
                    // Get target label
                    JLabel label = getLabelByLinkType(cell, node.getLinkType());
                    // Put link in label
                    label.setText(linkType);

                    // Add label to undo list
                    undoLabelsList.add(label);
                }
            }
        }

        private JLabel getLabelByLinkType(JCell cell, int linkType) {
            Component[][] components = getZigzagGridPanelComponentsMatrix();
            JLabel label = null;

            switch (linkType) {
                // Upper row
                case 1:
                    label = (JLabel) components[cell.cellRow - 1][cell.cellColumn - 1];
                    break;
                case 2:
                    label = (JLabel) components[cell.cellRow - 1][cell.cellColumn];
                    break;
                case 3:
                    label = (JLabel) components[cell.cellRow - 1][cell.cellColumn + 1];
                    break;
                // Current row
                case 4:
                    label = (JLabel) components[cell.cellRow][cell.cellColumn - 1];
                    break;
                case 6:
                    label = (JLabel) components[cell.cellRow][cell.cellColumn + 1];
                    break;
                // Lower row
                case 7:
                    label = (JLabel) components[cell.cellRow + 1][cell.cellColumn - 1];
                    break;
                case 8:
                    label = (JLabel) components[cell.cellRow + 1][cell.cellColumn];
                    break;
                case 9:
                    label = (JLabel) components[cell.cellRow + 1][cell.cellColumn + 1];
                    break;
                default:
                    break;
            }

            return label;
        }
    }

    private class UndoRedoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem btn = (JMenuItem) e.getSource();
            String btnName = btn.getText();

            switch (btnName) {
                case "Deshacer":
                    if (urm.canUndo()) {
                        urm.undo();
                    }
                    break;
                case "Rehacer":
                    if (urm.canRedo()) {
                        urm.redo();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private class UndoRedoManager {
        public boolean canUndo() {
            return undoBtnsList.size() == 1 || (!undoBtnsList.isEmpty() && !undoLabelsList.isEmpty());
        }

        public boolean canRedo() {
            return undoBtnsList.size() == 0 && !redoBtnsList.isEmpty()
                    || (!redoBtnsList.isEmpty() && !redoLabelsList.isEmpty());
        }

        public void undo() {
            if (canUndo() && undoBtnsList.size() == 1) {
                // Release first button
                JCell lastCell = undoBtnsList.remove(undoBtnsList.size() - 1);
                redoBtnsList.add(lastCell);
                lastCell.setEnabled(true);
            } else if (canUndo()) {
                // Get last cell in undo list and previous (to last cell) cell in undo list
                JCell lastCell = undoBtnsList.remove(undoBtnsList.size() - 1);
                JCell previousCell = undoBtnsList.get(undoBtnsList.size() - 1);
                // Move last cell to redo list
                redoBtnsList.add(lastCell);
                // Enable last cell
                lastCell.setEnabled(true);

                // Remove link between nodes
                Node[][] board = zigzag.getBoard();
                Node lastNode = board[lastCell.nodeRow][lastCell.nodeColumn];
                Node previousNode = board[previousCell.nodeRow][previousCell.nodeColumn];
                zigzag.unlinkNodes(lastNode, previousNode);

                // Get last label in undo list
                JLabel lastLabel = undoLabelsList.remove(undoLabelsList.size() - 1);
                redoLabelsList.add(lastLabel);
                // Remove link type from last label
                lastLabel.setText("");
            }
        }

        public void redo() {
            if (canRedo() && undoBtnsList.size() == 0) {
                // Press first button
                JCell lastCell = redoBtnsList.remove(redoBtnsList.size() - 1);
                undoBtnsList.add(lastCell);
                lastCell.setEnabled(false);
            } else if (canRedo()) {
                // Get last cell in redo list and last cell in undo list
                JCell lastCell = redoBtnsList.remove(redoBtnsList.size() - 1);
                JCell previousCell = undoBtnsList.get(undoBtnsList.size() - 1);
                // Restore last cell back into undo list
                undoBtnsList.add(lastCell);
                // Disable last cell
                lastCell.setEnabled(false);

                // Restore link between nodes
                Node[][] board = zigzag.getBoard();
                Node lastNode = board[lastCell.nodeRow][lastCell.nodeColumn];
                Node previousNode = board[previousCell.nodeRow][previousCell.nodeColumn];
                zigzag.trySetLink(previousNode, lastNode);

                // Get last label in redo list
                JLabel lastLabel = redoLabelsList.remove(redoLabelsList.size() - 1);
                // Restore last label back into undo list
                undoLabelsList.add(lastLabel);

                // Restore link type from last label
                String linkType = transformLinkType(lastNode.getLinkType());
                lastLabel.setText(linkType);

                // Check if puzzle has been solved after redoing (needs to check last button)
                if (isEveryRequiredCellDisabled()
                        && !zigzagGridPanel.getComponent(zigzagGridPanel.getComponentCount() - 1).isEnabled()) {
                    JOptionPane.showMessageDialog(frame, "¡Puzle completado!", "Enhorabuena",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private class SolutionsScrollDialog extends JDialog {
        private JScrollPane scrollPane = new JScrollPane();
        private JPanel innerPane = new JPanel();

        public SolutionsScrollDialog() {
            // SolutionsScrollDialog base config
            this.setSize(400, 400);
            this.setLocationRelativeTo(null);
            this.getContentPane().setLayout(new BorderLayout());
            this.getContentPane().add(scrollPane, BorderLayout.CENTER);
            innerPane.setLayout(new GridLayout(1, 1));
            scrollPane.setViewportView(innerPane);

            // Close button
            JButton closeBtn = new JButton("CERRAR");
            closeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SolutionsScrollDialog.this.dispose();
                    ;
                }
            });
            this.getContentPane().add(closeBtn, BorderLayout.SOUTH);

            String solutionsText = transformSolutionsToString();
            // Text area that contains solutions
            JTextArea solutionsTextArea = new JTextArea(solutionsText);
            solutionsTextArea.setFont(new Font("monospace", Font.PLAIN, 12));
            solutionsTextArea.setEditable(false);
            solutionsTextArea.setColumns(30);
            solutionsTextArea.setRows(10);
            solutionsTextArea.setLineWrap(true);
            solutionsTextArea.setWrapStyleWord(true);
            solutionsTextArea.setSize(solutionsTextArea.getPreferredSize().width,
                    solutionsTextArea.getPreferredSize().height);
            solutionsTextArea.setMargin(new Insets(10, 10, 10, 10));

            innerPane.add(solutionsTextArea);
        }

        private String transformSolutionsToString() {
            if (solutions == null || solutions.size() == 0) {
                return "*** SOLUCIONES ***\n\nNo hay soluciones.";
            }

            StringBuffer output = new StringBuffer();
            output.append("*** SOLUCIONES ***\n");

            int counter = 1;
            for (Node[][] board : solutions) {
                String[][] solutionMatrix = new String[board.length + board.length - 1][board[0].length
                        + board[0].length - 1];

                int solutionRow = 0;
                int solutionColumn = 0;

                for (int i = 0; i < solutionMatrix.length; i++) {
                    for (int j = 0; j < solutionMatrix[0].length; j++) {
                        solutionMatrix[i][j] = " ";
                    }
                }

                StringBuffer solution = new StringBuffer();

                // Board is looped through normally while solutionMatrix jumps from node to node
                for (int i = 0; i < board.length; i++, solutionRow += 2) {
                    for (int j = 0; j < board[0].length; j++, solutionColumn += 2) {
                        Node currentNode = board[i][j];
                        int currentNodeLinkType = currentNode.getLinkType();

                        // Place node
                        solutionMatrix[solutionRow][solutionColumn] = " " + String.valueOf(currentNode.getValue())
                                + " ";

                        // Put link in its correct row and column
                        switch (currentNodeLinkType) {
                            // Upper row
                            case 1:
                                solutionMatrix[solutionRow - 1][solutionColumn - 1] = "  "
                                        + transformLinkType(currentNodeLinkType) + " ";
                                break;
                            case 2:
                                solutionMatrix[solutionRow - 1][solutionColumn] = "  "
                                        + transformLinkType(currentNodeLinkType) + " ";
                                break;
                            case 3:
                                solutionMatrix[solutionRow - 1][solutionColumn + 1] = "  "
                                        + transformLinkType(currentNodeLinkType) + " ";
                                break;
                            // Current row
                            case 4:
                                solutionMatrix[solutionRow][solutionColumn - 1] = transformLinkType(
                                        currentNodeLinkType);
                                break;
                            case 6:
                                solutionMatrix[solutionRow][solutionColumn + 1] = transformLinkType(
                                        currentNodeLinkType);
                                break;
                            // Lower row
                            case 7:
                                solutionMatrix[solutionRow + 1][solutionColumn - 1] = "  "
                                        + transformLinkType(currentNodeLinkType) + " ";
                                break;
                            case 8:
                                solutionMatrix[solutionRow + 1][solutionColumn] = "  "
                                        + transformLinkType(currentNodeLinkType) + " ";
                                break;
                            case 9:
                                solutionMatrix[solutionRow + 1][solutionColumn + 1] = "  "
                                        + transformLinkType(currentNodeLinkType) + " ";
                                break;
                            default:
                                break;
                        }
                    }

                    solutionColumn = 0;
                }

                // Transform matrix into a String
                for (int i = 0; i < solutionMatrix.length; i++) {
                    for (int j = 0; j < solutionMatrix[0].length; j++) {
                        solution.append(solutionMatrix[i][j]);
                    }

                    solution.append("\n");
                }

                output.append("\n").append("Solución " + counter + ":\n").append(solution.toString());
                counter++;
            }

            return output.toString();
        }
    }
}
