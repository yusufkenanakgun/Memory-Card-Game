package CardGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MemoryGame extends JFrame implements ActionListener {
    JButton gameButton, aboutButton, exitButton;
    protected JLabel levelLabel, triesLabel;
    Menu mainMenu;
    protected JPanel buttonPanel, statusPanel, gamePanel;
    protected int playerScore;
    private static final String SCORES_FILE = "high_scores.txt";
    public MemoryGame(int level, int attempts) {
        setTitle("Flash Cards Game");
        setSize(700, 600);
        setLocationRelativeTo(null);

        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        Dimension buttonSize = new Dimension(70, 25);

        gameButton = new JButton("Game");
        gameButton.addActionListener(this);
        gameButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(gameButton, gbc);

        aboutButton = new JButton("About");
        aboutButton.addActionListener(this);
        aboutButton.setPreferredSize(buttonSize);
        gbc.gridx = 1;
        buttonPanel.add(aboutButton, gbc);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(buttonSize);
        gbc.gridx = 2;
        buttonPanel.add(exitButton, gbc);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        statusPanel = new JPanel(new GridLayout(1, 2));
        String levLabel = "LEVEL " + level;
        levelLabel = new JLabel(levLabel, SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 36));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setBackground(new Color(0, 100, 0));
        levelLabel.setOpaque(true);
        statusPanel.add(levelLabel);

        triesLabel = new JLabel("Tries Left: " + attempts, SwingConstants.CENTER);
        triesLabel.setFont(new Font("Arial", Font.BOLD, 36));
        triesLabel.setForeground(Color.WHITE);
        triesLabel.setBackground(new Color(0, 100, 0));
        triesLabel.setOpaque(true);
        statusPanel.add(triesLabel);

        getContentPane().add(statusPanel, BorderLayout.CENTER);

        gamePanel = new JPanel();
        getContentPane().add(gamePanel, BorderLayout.SOUTH);

        playerScore = 0;
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameButton) {
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem restartItem = new JMenuItem("Restart");
            restartItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    restartGame();
                }
            });
            popupMenu.add(restartItem);

            JMenuItem highScoresItem = new JMenuItem("High Scores");
            highScoresItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showHighScores();
                }
            });
            popupMenu.add(highScoresItem);

            popupMenu.show(gameButton, 0, gameButton.getHeight());
        } else if (e.getSource() == aboutButton) {
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem aboutGameItem = new JMenuItem("About the game");
            aboutGameItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAboutGame();
                }
            });
            popupMenu.add(aboutGameItem);

            JMenuItem aboutStudentItem = new JMenuItem("About the student");
            aboutStudentItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAboutStudent();
                }
            });
            popupMenu.add(aboutStudentItem);

            popupMenu.show(aboutButton, 0, aboutButton.getHeight());
        } else if (e.getSource() == exitButton) {
            dispose();
            if (mainMenu != null) {
                mainMenu.setVisible(true);
            }
        }
    }

    protected void restartGame() {
        if (this instanceof Level1) {
            ((Level1) this).resetGame();
        } else if (this instanceof Level2) {
            ((Level2) this).resetGame();
        } else if (this instanceof Level3) {
            ((Level3) this).resetGame();
        }
    }

    private void showHighScores() {
        StringBuilder highScores = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < 10) {
                highScores.append(line).append("\n");
                count++;
            }
        } catch (IOException e) {
            highScores.append("No high scores available.");
        }
        JOptionPane.showMessageDialog(this, highScores.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void saveHighScore(String playerName, int score) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE, true))) {
            writer.println(playerName + ": " + score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAboutGame() {
        JOptionPane.showMessageDialog(this, "This is a memory game where you match pairs of cards.\n"
                + "There are three levels and 8 pairs of cards in each of them.\n"
                + "You have 18 - 15 - 12 attempts from level 1 to level 3.\n"
                + "If you lose a level, you will be dropped to a lower level.\n"
                + "You need to pass level 3 to win the game.\n"
                + "Good luck!");
    }

    private void showAboutStudent() {
        JOptionPane.showMessageDialog(this, "This game was developed by \n"
                + "YUSUF KENAN AKGÜN \n"
                + "with Java Swing \n"
                + "for 212 term project.\n"
                + "ID: 20210702010");
    }
}
