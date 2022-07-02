
public class Game {

    public Player turn;
    private GUI black = null, white = null;
    private boolean skippedTurn;

    public Game () {

        restart();

    }

    public void restart() {

        if (white != null && black != null) {
            white.endGUI();
            black.endGUI();
        }

        turn = Player.WHITE;
        skippedTurn = false;

        Board board = new Board();
        black = new GUI(Player.BLACK, board, this);
        white = new GUI(Player.WHITE, board, this);

    }

    public void newTurn(boolean skipped) {

        skippedTurn = skipped;
        
        if (turn == Player.WHITE) {
            turn = Player.BLACK;
        } else {
            turn = Player.WHITE;
        }

        black.newTurn();
        white.newTurn();

    }

    public void newTurn() {

        newTurn(false);

    }

    public boolean noMove() {

        if (!skippedTurn) {
            skippedTurn = true;
            newTurn(true);
            return false;
        } else {
            black.drawBoard();
            white.drawBoard();
            return true;
        }

    }

}