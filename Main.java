package project;

import project.exception.DataAccessException;
import project.ui.LoginWindow;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginWindow();
            } catch (DataAccessException e) {
                JOptionPane.showMessageDialog(null, "Failed to start application: " + e.getMessage());
            }
        });
    }
}