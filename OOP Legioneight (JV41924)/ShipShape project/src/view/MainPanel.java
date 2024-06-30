package view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("<html><hr>Welcome To ShipShape Maritime Maintenance <br><hr> Management Application<hr></html>", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        setBackground(Color.ORANGE);

        add(welcomeLabel, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}
