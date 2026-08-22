import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.ui.GamePanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                var frame = new JFrame();
                var panel = new GamePanel();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                panel.requestFocusInWindow();
            }
        });
    }
}
