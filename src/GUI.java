import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

enum Player {
    BLACK,
    WHITE;
}

public class GUI {

    Board board;
    Game game;
    Player player;

    JFrame frame;
    JLabel moveText;
    JPanel boardPanel;
    TileButton[] boardTiles = new TileButton[64];
    
    public GUI (Player player, Board board, Game game) {

        JPanel moveTextPanel;
        JPanel moveButtonPanel;
        JButton moveButton;

        this.board = board;
        this.game = game;
        this.player = player;

        frame = new JFrame();
        if (player == Player.BLACK)
            frame.setTitle("Othello - Black");
        else
            frame.setTitle("Othello - White");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        moveTextPanel = new JPanel();
        moveText = new JLabel();



        moveText.setSize(300,300);
        moveTextPanel.add(moveText);
        frame.getContentPane().add(moveTextPanel);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8,8));
        frame.getContentPane().add(boardPanel);

        moveButtonPanel = new JPanel();
        moveButton = new JButton("Greedy AI");
        moveButton.addActionListener(
            evnt -> 
            {
                if (game.turn == player) {
                    if (board.makeBestMove(player)) {
                        game.newTurn();
                    }
                }
            }
        );

        moveButtonPanel.add(moveButton);
        frame.getContentPane().add(moveButtonPanel);

        newTurn();

        frame.pack();
        frame.setVisible(true);
        
    }

    public void drawBoard() {

        boardPanel.removeAll();
        boardPanel.revalidate();
        boardPanel.repaint();

        State boardStates[] = this.board.getBoard();

        for (int i = 0; i < 64; i++) {

            // add tiles based on the state of each square of the board
            if (boardStates[i] == State.NULL) {
                final int currSquare = i;
                boardTiles[i] = new TileButton(State.NULL);
                boardTiles[i].addActionListener(
                    evnt ->
                    {
                        if (game.turn == player) {
                            if (board.makeMove(currSquare, player)) {
                                board.changeTile(currSquare, player);
                                game.newTurn();
                            }
                        }
                    }
                );
            }
            else if (boardStates[i] == State.BLACK)
                boardTiles[i] = new TileButton(State.BLACK);
            else if (boardStates[i] == State.WHITE)
                boardTiles[i] = new TileButton(State.WHITE);

            // draw in correct order for white
            if (player == Player.WHITE)
                boardPanel.add(boardTiles[i]);

        }

        // draw in reversed order for black
        if (player == Player.BLACK) {

            for (int i = 1; i < 65; i++) {
                boardPanel.add(boardTiles[64-i]);
            }

        }

    }

    public void endGUI () {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void newTurn() {

        if (player == Player.BLACK) {

            if (game.turn == Player.BLACK)
                moveText.setText("Black to move, your turn");
            else
                moveText.setText("White to move, not your turn"); 

        } else {

            if (game.turn == Player.WHITE)
                moveText.setText("White to move, your turn");
            else
                moveText.setText("Black to move, not your turn"); 

        }

        drawBoard();

        if (!board.canMove(game.turn)) {
            if (game.noMove()) {

                String text;
                int black = board.countBlack();
                int white = board.countWhite();

                if (black > white)
                    text = "Black wins: " + white + ":" + black;
                else if (black < white)
                    text = "White wins: " + white + ":" + black;
                else
                    text = "Draw: " + white + ":" + black;

                JOptionPane.showMessageDialog(boardPanel, text, "Click OK to restart", JOptionPane.INFORMATION_MESSAGE);
                game.restart();

            }
        }

    }
    
}
