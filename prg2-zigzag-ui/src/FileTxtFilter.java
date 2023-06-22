import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTxtFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
    }

    @Override
    public String getDescription() {
        return "Text files (*.txt)";
    }
}
