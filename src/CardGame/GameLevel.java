package CardGame;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public abstract class GameLevel extends MemoryGame {
    protected int remainingAttempts;
    protected int currentLevel;
    protected Card firstSelectedCard, secondSelectedCard;
    protected int matchedPairs;

    public GameLevel(int level, int attempts) {
        super(level, attempts);
        this.currentLevel = level;
        this.remainingAttempts = attempts;
        this.matchedPairs = 0;
        setTitle("Memory Game - Level " + level);
        setSize(600, 725);
        setLocationRelativeTo(null);
    }

    protected abstract void createAndAddCards();

    protected void cardSelected(Card selectedCard) {
        if (selectedCard.isFlipped() || selectedCard.isMatched()) {
            return;
        }

        selectedCard.flip();

        if (firstSelectedCard == null) {
            firstSelectedCard = selectedCard;
        } else {
            secondSelectedCard = selectedCard;
            

            if (firstSelectedCard.getId() == secondSelectedCard.getId()) {
                firstSelectedCard.setMatched(true);
                secondSelectedCard.setMatched(true);
                matchedPairs++;
                updateScore(true);
                firstSelectedCard = null;
                secondSelectedCard = null;

                if (matchedPairs == 8) {
                    proceedToNextLevel();
                }
            } else {
                updateScore(false);
                remainingAttempts--;
                triesLabel.setText("Tries Left: " + remainingAttempts);
                javax.swing.Timer timer = new Timer(400, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        firstSelectedCard.flip();
                        secondSelectedCard.flip();
                        firstSelectedCard = null;
                        secondSelectedCard = null;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

            if (remainingAttempts == 0 && matchedPairs != 8) {
                fallBackToPreviousLevel();
            }
        }
    }

    private void updateScore(boolean isMatch) {
        int matchPoints, penaltyPoints;
        switch (currentLevel) {
            case 1:
                matchPoints = 5;
                penaltyPoints = 1;
                break;
            case 2:
                matchPoints = 4;
                penaltyPoints = 2;
                break;
            case 3:
                matchPoints = 3;
                penaltyPoints = 3;
                break;
            default:
                return;
        }

        if (isMatch) {
            playerScore += matchPoints;
            
        } else {
            playerScore -= penaltyPoints;
        }
    }

    protected void proceedToNextLevel() {
        if (currentLevel < 3) {
            dispose();
            if (currentLevel == 1) {
                new Level2().setVisible(true);
                Card.gameScore += this.playerScore;
            } else if (currentLevel == 2) {
                new Level3().setVisible(true);
                Card.gameScore += this.playerScore;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! You've completed all levels!");
            Card.gameScore += this.playerScore;
            showFinalScore();
        }
    }

    protected void fallBackToPreviousLevel() {
        dispose();
        new Level1().setVisible(true);
        Card.gameScore += this.playerScore;
    }
    
    protected void showFinalScore() {
        String playerName = getPlayerName();
        JOptionPane.showMessageDialog(this, "Congratulations, " + playerName + "! Your final score is: " + Card.gameScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        saveHighScore(playerName, Card.gameScore);
        dispose();
    }

    private String getPlayerName() {
        return JOptionPane.showInputDialog(this, "Enter your name:");
    }

}
 
