import javax.swing.*;

public class swing_console {
    public static void run(final JFrame f) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f.setTitle("SIMPL");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}
