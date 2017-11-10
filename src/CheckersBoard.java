package edu.ucsb.cs56.projects.games.checkers;

//Basic classes for checkers command line version

public class CheckersBoard implements CheckersGame
{
    private char[][] pieces = new char[8][8]; //2D array of pieces
    
    private char turn = 'x'; //x alwayds starts
    private char opponent = 'o'; //always opposite of turn, starts as o
    
    private int x1; // possible x,y moves 
    private int x2;
    private int x3;
    private int x4;
    private int y1;
    private int y2;
    private int y3;
    private int y4;
    
    private int jumpI; //possible places that would be jumped to
    private int jumpJ;
    
    private boolean jumped; //true if jumped

    private int xCount = 12; //count of x pieces
    private int oCount = 12; //count of o pieces

    private char winner = ' '; //changed to either x,o, or t at end of game

    private boolean canJump = false; //true if can jump, false if not
    private boolean validMove = false;

    public CheckersBoard() {
	//initalize checkers board       
	for (int j = 0; j < 8; j++) {
	    if (j % 2 == 0) {
		pieces[0][j] = ' ';
		pieces[1][j] = 'o';
		pieces[2][j] = ' ';
		pieces[3][j] = ' ';
		pieces[4][j] = ' ';
		pieces[5][j] = 'x';
		pieces[6][j] = ' ';
		pieces[7][j] = 'x';
	    }
	    else {
		pieces[0][j] = 'o';
		pieces[1][j] = ' ';
		pieces[2][j] = 'o';
		pieces[3][j] = ' ';
		pieces[4][j] = ' ';
		pieces[5][j] = ' ';
		pieces[6][j] = 'x';
		pieces[7][j] = ' ';
		}
	}
    }
	
    public char getTurn() { return turn; }

    public char getPiece(int row, int col) { return pieces[row][col]; }

    public char getWinner() { return winner; }

    public void changeTurn() {
	if (turn == 'x') {
	    turn = 'o';
	}
	else {
	    turn = 'x';
	}
    }

    public int getXCount() { return xCount; }

    public int getOCount() { return oCount; }
    
    /**Checks if the index you are moving from is owned by the correct owner
     * @param i,j The index of the spot you are trying to move
     * @return True if you own the spot, or False if you do not own it
     */
    public boolean correctOwner(int i, int j) {
	if (turn == 'x') {
	    if(pieces[i][j] == 'x' || pieces[i][j] == 'X') {
		return true;
	    }
	    else {
		return false;
	    }
	}
	else if (turn == 'o') {
	    if (pieces[i][j] == 'o' || pieces[i][j] == 'O') {
		return true;
	    }
	    else {
		return false;
	    }
	}
	else {
	    return false;
	}
    }

    /** move method that allows the user to move
     * Will throw a CheckersIllegalMoveException if the from index is a spot that you don't own, or if you are trying to move to a spot that is not valid
     *  @param from the integer value of the index of the spot you are moving from
     *  @param to the integer value of the index of the spot you are moving to
     */
    public void setMoves(int fromI, int fromJ, int toI, int toJ) {
        if (turn == 'x') {
            x1 = fromI - 1; // up left                                                                  
            y1 = fromJ - 1;
            x2 = fromI - 1; // up right                                                                 
            y2 = fromJ + 1;
            if (pieces[fromI][fromJ] == 'X') { // if its a king
		x3 = fromI + 1; // back left                                                            
                y3 = fromJ - 1;
                x4 = fromI + 1; // back right                                                           
                y4 = fromJ + 1;
            }
        }
        else if (turn == 'o') {
            x1 = fromI + 1; // up right                                                                 
            y1 = fromJ - 1;
            x2 = fromI + 1; // up left                                                                  
            y2 = fromJ + 1;
            if (pieces[fromI][fromJ] == 'O') { // if its a king                                         
                x3 = fromI - 1; // back right                                                           
                y3 = fromJ - 1;
                x4 = fromI - 1; // back left                                                            
                y4 = fromJ + 1;
            }
        }
    }

    public void validMove(int fromI, int fromJ, int toI, int toJ) throws CheckersIllegalMoveException {
      	if (!correctOwner(fromI, fromJ)) {
	    throw new CheckersIllegalMoveException("Thats not your piece");
	    }
	else if (x1 > 7 || x2 > 7 || x3 > 7 || x4 > 7 || y1 > 7 || y2 > 7 || y3 > 7 || y4 > 7) { //check if out of bounds
	    throw new CheckersIllegalMoveException("You cant move off the board");
	}
	else if (toI == x1 && toJ == y1) {
	    validMove = true;
	}
	else if (toI == x2 && toJ == y2) {
	    validMove = true;
	}
	else if (toI == x3 && toJ == y3) {
	    validMove = true;
	}
	else if (toI == x4 && toJ == y4) {
	    validMove = true;
	}
	else if ((turn == 'o' && pieces[toI][toJ] == 'x') || (turn == 'x' && pieces[toI][toJ] == 'o')) {
	    checkJump(fromI, fromJ, toI, toJ);
	}
	else {
	    validMove = false;
	}
	// maybe all these ifs^^ can be replaced by if != ' '?
    }
    
    public void checkJump(int fromI, int fromJ, int toI, int toJ) {
	if (turn == 'x') {
	    if(toI == x1 && toJ == y1) {
		jumpI = x1 - 1;
		jumpJ = y1 - 1;
	    }
	    else if (toI == x2 && toJ == y2) {
		jumpI = x2 - 1;
		jumpJ = y2 + 1;
	    }
	    else if (toI == x3 && toJ == y3 && pieces[fromI][fromJ] == 'X') {
		jumpI = x3 + 1;
		jumpJ = y3 - 1;
	    }
	    else if (toI == x4 && toJ == y4 && pieces[fromI][fromJ] == 'X') {
		jumpI = x4 + 1;
		jumpJ = y4 + 1;
	    }
	    else {
		validMove = false;
	    }
	}
	if (turn == 'o') {
	    if(toI == x1 && toJ == y1) {
		jumpI = x1 + 1;
		jumpJ = y1 + 1;
	    }
	    else if (toI == x2 && toJ == y2) {
		jumpI = x2 + 1;
		jumpJ = y2 + 1;
	    }
	    else if (toI == x3 && toJ == y3 && pieces[fromI][fromJ] == 'O') {
		jumpI = x3 - 1;
		jumpJ = y3 - 1;
	    }
	    else if (toI == x4 && toJ == y4 && pieces[fromI][fromJ] == 'O') {
		jumpI = x4 - 1;
		jumpJ = x4 + 1;
	    }
	    else {
		validMove = false;
	    }
	}
	if (pieces[jumpI][jumpJ] == ' ') {
	    canJump = true;
	    validMove = true;
	}
    }
    
    public Boolean kingCheck(int i, int j){
	if (turn == 'x') {
	    if (i == 0 && (j == 1 || j == 3 || j == 5 || j == 7)){
		return true;
	    }
	    else {
		return false;
	    }
	}
	else {
	    if (i == 7 && (j == 0 || j == 2 || j == 4 || j == 6)) {
		return true;
	    }
	    else {
		return false;
	    }
	}
    }
    
    public void checkWinner() {
	if (xCount == 0) {
	    winner = 'x';
	}
	else if (oCount == 0) {
	    winner = 'o';
	}
    }
    
    public void move(int fromI, int fromJ, int toI, int toJ) throws CheckersIllegalMoveException {
	setMoves(fromI, fromJ, toI, toJ);
	System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
	validMove(fromI, fromJ, toI, toJ);
	if (canJump == true && validMove == true) {
	    pieces[fromI][fromJ] = ' ';
	    pieces[toI][toJ] = ' ';
	    if (kingCheck(jumpI, jumpJ)) {
		if (turn == 'x') {
		    pieces[jumpI][jumpJ] = 'X';
		    --oCount;
		}
		else {
		    pieces[jumpI][jumpJ] = 'O';
		    --xCount;
		}
	    }
	    else {
		pieces[jumpI][jumpJ] = turn;
	    }
	}
	if (canJump == false && validMove == true) {
	    pieces[fromI][fromJ] = ' ';
	    pieces[toI][toJ] = turn;
	}
	if (validMove == false) {
	    throw new CheckersIllegalMoveException("Please make a valid move");
	}
	
    }
    

    /**A toString method for the CheckersBoard, allows easier access for console based interface
     * @return result The result of the toString(), aka the gameBoard
     */
    public String toString() {
	String result;
	result = "| A | B | C | D | E | F | G | H |";

	for (int i = 0; i < 8; i++) {
	    result += "\n-+---+---+---+---+---+---+---+---+\n";
	    result += Integer.toString(i + 1) + "|";
	    for (int j = 0; j < 8; j++) {
		result += " " +  pieces[i][j] + " |";
	    }
	}
	return result;
    }

    /** A formatting function that allows multiple different types of inputs
     * Matt already completed?
     * @param input from user, either piece theyre selecting or spot they want to move to
     * @return input in proper format
     */
    /*   public String cleanCoord(String s) {
	String r = s, Spre, Ssuf;
	char Cpre, Csuf;
	// S isn't 2 chars, invalid, return entry
	if (r.length() != 2) return s;
	Cpre = r.charAt(0);
        Csuf = r.charAt(1);

            // First make sure that format is <Letter><Digit>
        if (Character.isDigit(Cpre) && Character.isLetter(Csuf)) { //	\
	    Format is <Digit><Letter>, switch(it)                                       
            char temp = Csuf;
            Csuf = Cpre;
            Cpre = temp;
	}
        Spre = String.valueOf(Cpre);
        Ssuf = String.valueOf(Csuf);
	Spre = Spre.toUpperCase();
        r = Spre + Ssuf;
        return r;
	}*/
    public int findYCoordinate(String s) {
	char coord = s.charAt(0);
	int x;
	if ( coord == 'A') {x = 0; }
	else if ( coord == 'B') { x = 1; }
	else if ( coord == 'C') { x = 2; }
	else if ( coord == 'D') { x = 3; }
	else if ( coord == 'E') { x = 4; }
	else if ( coord == 'F') { x = 5; }
	else if ( coord == 'G') { x = 6; }
	else if ( coord == 'H') { x = 7; }
	else { x = -1; }
	return x;
    }

    public int findXCoordinate(String s) {
	char coord = s.charAt(1);
	int y;
	y = Character.getNumericValue(coord) - 1;
	return y;
    }
}//end class CheckersBoard

    
/**
 | A | B | C | D | E | F | G | H |
-+---+---+---+---+---+---+---+---+
1|   | o |   | o |   | o |   | o |
-+---+---+---+---+---+---+---+---+
2| o |   | o |   | o |   | o |   |
-+---+---+---+---+---+---+---+---+
3|   | o |   | o |   | o |   | o |
-+---+---+---+---+---+---+---+---+
4|   |   |   |   |   |   |   |   |
-+---+---+---+---+---+---+---+---+
5|   |   |   |   |   |   |   |   |
-+---+---+---+---+---+---+---+---+
6| x |   | x |   | x |   | x |   |
-+---+---+---+---+---+---+---+---+
7|   | x |   | x |   | x |   | x |
-+---+---+---+---+---+---+---+---+
8| x |   | x |   | x |   | x |   |
-+---+---+---+---+---+---+---+---+

   |A|B|C|D|E|F|G|H|
-  -----------------
1  |_|x|_|x|_|x|_|x|
2  |x|_|x|_|x|_|x|_|
3  |_|x|_|x|_|x|_|x|
4  |_|_|_|_|_|_|_|_|
5  |_|_|_|_|_|_|_|_|
6  |o|_|o|_|o|_|o|_|
7  |_|o|_|o|_|o|_|o|
8  |o|_|o|_|o|_|o|_|
-  -----------------
*/
