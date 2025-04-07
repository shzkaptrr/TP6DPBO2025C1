import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JFrame {
    public StartMenu() {
        setTitle("Welcome to Flappy Bird");
        setSize(400, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel dengan background image
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = new ImageIcon(getClass().getResource("assets/bg_awal_3.jpg")).getImage();
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Tombol retro
        JButton startButton = new JButton("PRESS TO START");
        startButton.setFont(new Font("Courier New", Font.BOLD, 20)); // mirip font pixel
        startButton.setBounds(100, 180, 200, 50);
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(0, 255, 144, 178)); // #00FF9C
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        startButton.setFocusPainted(false);

        // Action tombol
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                JFrame frame = new JFrame("Flappy Bird");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(360, 640);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                FlappyBird flappyBird = new FlappyBird();
                frame.add(flappyBird);
                frame.pack();
                flappyBird.requestFocus();
                frame.setVisible(true);
            }
        });

        panel.add(startButton);
        add(panel);
    }
}
