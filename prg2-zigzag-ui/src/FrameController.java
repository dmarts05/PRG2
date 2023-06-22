import java.util.ArrayList;

import javax.swing.JFrame;

public class FrameController {
    public static ArrayList<JFrame> frames = new ArrayList<JFrame>();

    public static void changeCurrentlyShownFrame(String newCurrentFrameTitle) {
        boolean found = false;
        for (JFrame frame : frames) {
            if (frame.getTitle().equals(newCurrentFrameTitle)) {
                frame.setVisible(true);
                found = true;
            } else {
                frame.setVisible(false);
            }
        }

        // Check if at least one frame is being displayed, otherwise exit app
        if (!found) {
            exitApp();
        }
    }

    public static void exitFrame(String closedFrame) {
        JFrame foundFrame = null;

        for (JFrame frame : frames) {
            if (frame.getTitle().equals(closedFrame)) {
                foundFrame = frame;
                break;

            }
        }

        if (foundFrame != null) {
            foundFrame.dispose();
            frames.remove(foundFrame);
        }
    }

    public static void exitApp() {
        for (JFrame frame : frames) {
            frame.dispose();
        }
    }
}
