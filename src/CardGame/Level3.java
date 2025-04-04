package CardGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;

public class Level3 extends GameLevel {
    private ArrayList<Card> cards;
    private Set<Integer> matchingPairIndices;

    public Level3() {
        super(3, 12);
        cards = new ArrayList<>();
        matchingPairIndices = new HashSet<>();
        createAndAddCards();
    }

    @Override
    protected void createAndAddCards() {
        String frontImagePath = "ProjectAssets/Level3-GamingComputerAssets/";
        ImageIcon backIcon = new ImageIcon(frontImagePath + "no_image.png");
        for (int i = 1; i <= 8; i++) {
            String imagePath = frontImagePath + i + ".png";
            cards.add(new Card(i, imagePath, backIcon));
            cards.add(new Card(i, imagePath, backIcon));
        }

        shuffleCards();

        gamePanel.setLayout(new GridLayout(4, 4, 3, 3));
        gamePanel.removeAll();
        for (Card card : cards) {
            card.addActionListener(e -> cardSelected(card));
            gamePanel.add(card);
        }

        getContentPane().add(gamePanel, BorderLayout.SOUTH);
    }

    private Timer shuffleTimer;

    private void shuffleCards() {
        for (Card card : cards) {
            card.setEnabled(false);
        }

        if (shuffleTimer != null && shuffleTimer.isRunning()) {
            shuffleTimer.stop();
        }
        shuffleTimer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.shuffle(cards);
                gamePanel.removeAll();
                for (Card card : cards) {
                    card.addActionListener(evt -> cardSelected(card));
                    gamePanel.add(card);
                }
                getContentPane().revalidate();
                getContentPane().repaint();
                for (Card card : cards) {
                    card.setEnabled(true);
                }
            }
        });
        shuffleTimer.setRepeats(false);
        shuffleTimer.start();
    }


    @Override
    protected void cardSelected(Card selectedCard) {
        super.cardSelected(selectedCard);
        if (firstSelectedCard != null && secondSelectedCard != null && !isMatching()) {
            shuffleCards();
        }
    }

    private boolean isMatching() {
        return firstSelectedCard.getId() == secondSelectedCard.getId();
    }

    public void resetGame() {
        gamePanel.removeAll();
        remainingAttempts = 12;
        matchedPairs = 0;
        this.playerScore = 0;
        firstSelectedCard = null;
        secondSelectedCard = null;
        levelLabel.setText("LEVEL 3");
        triesLabel.setText("Tries Left: " + remainingAttempts);
        cards.clear();
        createAndAddCards();
        revalidate();
        repaint();
    }
}
