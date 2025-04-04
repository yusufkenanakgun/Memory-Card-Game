package CardGame;

import javax.swing.*;
import java.awt.*;

public class Card extends JButton {
    private int id;
    static int gameScore = 0;
    private boolean isFlipped;
    private boolean isMatched;
    private ImageIcon frontIcon;
    private ImageIcon backIcon;
    
    public Card(int id, String frontImagePath, ImageIcon backIcon) {
        this.id = id;
        this.isFlipped = false;
        this.isMatched = false;
        this.frontIcon = new ImageIcon(frontImagePath);
        this.backIcon = backIcon;
        this.setIcon(backIcon);
        this.setPreferredSize(new Dimension(100, 150));
    }

    public int getId() {
        return id;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public void flip() {
        if (isFlipped) {
            this.setIcon(backIcon);
        } else {
            this.setIcon(frontIcon);
        }
        isFlipped = !isFlipped;
    }
}
