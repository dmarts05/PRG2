import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Start {
    private JFrame frame;
    private JPanel titlePanel;
    private JPanel btnsPanel;
    private JPanel footerPanel;

    public Start() {
        // *** TITLE PANEL ***

        // Title label
        JLabel titleLabel = new JLabel("PUZLE ZIGZAG");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("", Font.BOLD, 60));

        // Title panel settings and components
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel);

        // *** BUTTONS PANEL ***

        // Create mode button
        JButton createBtn = new JButton("CREAR");
        createBtn.setFont(new Font("", Font.PLAIN, 30));
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameController.changeCurrentlyShownFrame("Crear");
            }
        });
        // Solve mode button
        JButton solveBtn = new JButton("RESOLVER");
        solveBtn.setFont(new Font("", Font.PLAIN, 30));
        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameController.changeCurrentlyShownFrame("Resolver");
            }
        });

        // Buttons panel settings and components
        btnsPanel = new JPanel();
        btnsPanel.setLayout(new GridLayout(2, 1));
        btnsPanel.add(createBtn);
        btnsPanel.add(solveBtn);

        // *** FOOTER PANEL ***

        // Footer label
        JLabel footerLabel = new JLabel("dmarts05 - ULE");

        // Footer panel settings and components
        footerPanel = new JPanel();
        footerPanel.add(footerLabel);

        // *** FRAME ***

        // Frame settings
        frame = new JFrame("Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);

        // Arrange panels in frame
        frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
        frame.getContentPane().add(btnsPanel, BorderLayout.CENTER);
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
    }

    public JFrame getFrame() {
        return frame;
    }
}
