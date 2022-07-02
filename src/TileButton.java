import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class TileButton extends JButton {

    State state;
    boolean occupied = false;
    private int borderWidth = 3;

    private String black = "#515253";
    private String white = "#FCFDFE";
    private String green = "#BAAE9E";

    private String border = "#666666";

    public TileButton (State state) {

        setMinimumSize(new Dimension(50, 50));
        setPreferredSize(new Dimension(50, 50));
        this.state = state;
        if (state != State.NULL)
            occupied = true;

    }

    protected void paintComponent(Graphics g) {

        /*g.setColor(Color.decode(black));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.decode(green));
        g.fillRect(borderWidth, borderWidth, getWidth()-borderWidth*2, getHeight()-borderWidth*2);*/

        g.setColor(Color.decode(green));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (occupied){
            /*if (state == State.WHITE)
                g.setColor(Color.decode(black));
            else if (state == State.BLACK)
                g.setColor(Color.decode(white));*/
            g.setColor(Color.decode(border));
            //g.fillOval(0, 0, getWidth(), getHeight()); // draw the border
            if (state == State.WHITE)
                g.setColor(Color.decode(white));
            else if (state == State.BLACK)
                g.setColor(Color.decode(black));
            g.fillOval(borderWidth, borderWidth, getWidth() - borderWidth*2, getHeight() - borderWidth*2); // fill the color
        }  

    }
    
}
