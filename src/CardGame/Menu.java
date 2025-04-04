package CardGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {
    private JButton startGameButton, selectLevelButton, instructionsButton, exitButton;

    public Menu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Memory Card Game");
        setSize(709, 425);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("ProjectAssets/background.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
                
                g.setColor(new Color(0, 200, 255));
                g.setFont(new Font("Arial", Font.ITALIC, 36));
                String text = "Memory Card Game";
                int x = 185;
                int y = 60;
                g.drawString(text, x, y);
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        final Dimension buttonSize = new Dimension(160, 30);

        startGameButton = new JButton("Start game");
        startGameButton.addActionListener(this);
        startGameButton.setPreferredSize(buttonSize);
        buttonPanel.add(startGameButton, gbc);

        selectLevelButton = new JButton("Select Level");
        selectLevelButton.addActionListener(this);
        selectLevelButton.setPreferredSize(buttonSize);
        buttonPanel.add(selectLevelButton, gbc);

        instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(this);
        instructionsButton.setPreferredSize(buttonSize);
        buttonPanel.add(instructionsButton, gbc);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(buttonSize);
        buttonPanel.add(exitButton, gbc);

        getContentPane().add(buttonPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            new Level1().setVisible(true);
            
        }  else if (e.getSource() == selectLevelButton) {
            String[] levels = {"Easy", "Normal", "Hard"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Select the level you want to play:",
                    "Select Level",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    levels,
                    levels[0]);

            if (choice == 0) {
                new Level1().setVisible(true);
            } else if (choice == 1) {
            	new Level2().setVisible(true);
            } else if (choice == 2) {
            	new Level3().setVisible(true);
            }
        }  else if (e.getSource() == instructionsButton) {
            JOptionPane.showMessageDialog(this,
                    "Instructions:\n" +
                    "There are 3 levels in the game. It gets gradually harder!\n" +
                    "Match all pairs of cards to win!",
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu());
    }
}
