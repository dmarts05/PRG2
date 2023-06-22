import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // App windows
                    Start startWindow = new Start();
                    Create createWindow = new Create();
                    Resolve resolveWindow = new Resolve();

                    // Setup frame controller
                    FrameController.frames.add(startWindow.getFrame());
                    FrameController.frames.add(createWindow.getFrame());
                    FrameController.frames.add(resolveWindow.getFrame());

                    // Show start window
                    FrameController.changeCurrentlyShownFrame("Inicio");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}