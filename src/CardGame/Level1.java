package CardGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Level1 extends GameLevel {
    private ArrayList<Card> cards;

    public Level1() {
        super(1, 18);
        cards = new ArrayList<>();
        createAndAddCards();
    }


    @Override
    protected void createAndAddCards() {
        String frontImagePath = "ProjectAssets/Level1-InternetAssets/";
        ImageIcon backIcon = new ImageIcon(frontImagePath + "no_image.png");
        for (int i = 1; i <= 8; i++) {
            String imagePath = frontImagePath + i + ".png";
            cards.add(new Card(i, imagePath, backIcon));
            cards.add(new Card(i, imagePath, backIcon));
        }

        Thread shuffleThread = new Thread(() -> {
            Collections.shuffle(cards);
            SwingUtilities.invokeLater(() -> {
                gamePanel.setLayout(new GridLayout(4, 4, 3, 3)); 
                gamePanel.removeAll();
                for (Card card : cards) {
                    card.addActionListener(e -> cardSelected(card));
                    gamePanel.add(card);
                }
                getContentPane().add(gamePanel, BorderLayout.SOUTH);
                revalidate();
                repaint();
            });
        });
        shuffleThread.start();
    }


    public void resetGame() {
        gamePanel.removeAll();
        remainingAttempts = 18;
        matchedPairs = 0;
        this.playerScore = 0;
        firstSelectedCard = null;
        secondSelectedCard = null;
        levelLabel.setText("LEVEL 1");
        triesLabel.setText("Tries Left: " + remainingAttempts);
        cards.clear();
        createAndAddCards();
        revalidate();
        repaint();
    }
}
