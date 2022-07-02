enum State {
    BLACK,
    WHITE,
    NULL
}

public class Board {

    private State board[];
    private final int[] dirNums = {-8, -7, 1, 9, 8, 7, -1, -9}; // stores the numbers associated with moving in a specific direction

    public Board () {

        board = new State[64];
        for (int i = 0; i < 64; i++) {
            board[i] = State.NULL;
        }
        
        int white[] = {27, 36};
        int black[] = {28, 35};

        for (int i = 0; i < white.length; i++)
            board[white[i]] = State.WHITE;

        for (int i = 0; i < black.length; i++)
            board[black[i]] = State.BLACK;  

    }

    public void changeTile (int square, Player player) {

        if (player == Player.WHITE) {
            board[square] = State.WHITE;
        } else {
            board[square] = State.BLACK;
        }

    }

    public Boolean canMove (Player player) {

        State searchState;
        State state;
        boolean move = false;
        int[] moveDirs = new int[8];

        if (player == Player.BLACK) {
            searchState = State.WHITE;
            state = State.BLACK;
        } else {
            searchState = State.BLACK;
            state = State.WHITE; 
        }

        for (int i = 0; i <= 63; i++) {

            if (board[i] == State.NULL) {

                    moveDirs = checkForMoves(i, moveDirs, state, searchState, false);
                    for (int j = 0; j < 8; j++) {
                        if (moveDirs[j] > 0)
                            move = true;
                    }

            }

        }

        return move;

    }

    public Boolean makeMove (int square, Player player) {

        State searchState;
        State state;

        if (player == Player.BLACK) {
            searchState = State.WHITE;
            state = State.BLACK;
        } else {
            searchState = State.BLACK;
            state = State.WHITE; 
        }

        int[] moveDirs = new int[8]; // stores whether a move an be made in direction, from north to north-west
        moveDirs = checkForMoves(square, moveDirs, state, searchState, true); // checking for moves in each direction

        for (int i = 0; i < 8; i++) { // return true if a move is made
            if (moveDirs[i] != 0)
                return true;
        }

        return false;

    }

    public Boolean makeBestMove (Player player) {

        State searchState;
        State state;
        int bestSquare = 0;
        int bestScore = 0;

        if (player == Player.BLACK) {
            searchState = State.WHITE;
            state = State.BLACK;
        } else {
            searchState = State.BLACK;
            state = State.WHITE; 
        }

        int[] moveDirs = new int[8]; // stores whether a move an be made in direction, from north to north-west

        for (int i = 0; i <= 63; i++) { // go through each tile in the board

            int total = 0; // stores a score for each tile

            if (getSquare(i) == State.NULL) { // only null squares can be played

                moveDirs = checkForMoves(i, moveDirs, state, searchState, false);
                for (int j = 0; j < 8; j++) {
                    total += moveDirs[j];
                }

                if (total > bestScore) {
                    bestScore = total;
                    bestSquare = i;
                }

            }

        }

        checkForMoves(bestSquare, moveDirs, state, searchState, true);
        changeTile(bestSquare, player);

        return true;

    }

    private int[] checkForMoves(int square, int[] moveDirs, State state, State searchState, boolean fill) {

        for (int i = 0; i < 8; i++) { // checking for moves in each direction
            int newSquare = square + dirNums[i];
            moveDirs[i] = pieceCount(square, newSquare, searchState, state, fill);
        }

        return moveDirs;

    }

    private int pieceCount(int square, int newSquare, State searchState, State state, Boolean fill) {

        int mult = newSquare - square;
        int count = 0;

        if (!isOutOfBoard(square, newSquare)) {
            if (!isNull(newSquare) && getSquare(newSquare) == searchState) {

                while (getSquare(newSquare + (mult * count)) == searchState && !isOutOfBoard(newSquare + (mult * count), newSquare + (mult * (count + 1)))) {
                    count = count + 1;
                }

                if (getSquare(newSquare + (mult * count)) == state) {

                    if (fill) {
                        for (int i = 0; i < count; i++) {
                            board[newSquare + mult * i] = state;
                        }
                    }
                    
                } else {
                    count = 0;
                }

            }
        }

        return count;

    }

    private Boolean isOutOfBoard (int currSquare, int newSquare) {

        if (newSquare > 63 || newSquare < 0) // is below or above the board
            return true;
        else if (currSquare % 8 == 0 && newSquare == currSquare - 1) // is to the left of the board
            return true;
        else if ((currSquare + 1) % 8 == 0 && newSquare == currSquare + 1) // is to the right of the board
            return true;
        else if ((currSquare + 1) % 8 == 0 && newSquare == currSquare - 7) // is to top right of initial square, right of board
            return true;
        else if (currSquare % 8 == 0 && newSquare == currSquare + 7) // is to bottom left of initial square, left of board
            return true;
        else if (currSquare % 8 == 0 && newSquare == currSquare - 9) // is to top left of initial square, left of board
            return true;
        else if (((currSquare + 1) % 8 == 0 && newSquare == currSquare + 9)) // is to bottom right of initial square, right of board
            return true;
        else
            return false;

    }

    public int countWhite () {
        
        int total = 0;

        for (int i = 0; i <= 63; i++)
            if (getSquare(i) == State.WHITE)
                total += 1;

        return total;

    }

    public int countBlack () {
        
        int total = 0;

        for (int i = 0; i <= 63; i++)
            if (getSquare(i) == State.BLACK)
                total += 1;

        return total;

    }

    private Boolean isNull (int square) {
        if (getSquare(square) == State.NULL)
            return true;
        else 
            return false;
    }

    private State getSquare (int square) {
        if (square < 0 || square > 63)
            return State.NULL;
        else 
            return board[square];
    }

    public State[] getBoard () {
        return board;
    }
    
}
