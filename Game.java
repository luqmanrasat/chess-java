import java.awt.*;
import java.io.* ;

public class Game implements Event,Transform , Test{
	protected String p1 = "RED";
	protected String p2 = "GREEN";
	protected boolean isPieceSelected = false;
	protected int[] selectedPiece = new int[2];
	protected String curPlayer;
	protected int turnCount;
	protected Piece[][] piecesArray = new Piece[8][5];
	protected ObjectInputStream restore ;
    protected ObjectOutputStream save ;


	// Function generates help text
	public String helpTxt(){
		String arrowTxt = "- The Arrow Box can only move forward, 1 or 2 steps. If it reaches the end of the board, it turns around and starts heading back the other way.";
		String starTxt = "\n- The Star can move 1 or 2 steps in any direction.";
		String crossTxt = "\n- The Cross can only move diagonally, but can go any distance.";
		String heartTxt = "\n- The Heart can move only 1 step in any direction. The game ends when the Heart is captured by the other side.";
		String fourTurnTxt = "\n- After 4 turns, a Star will turn into a Cross, and a Cross will turn into a Star. ";
		String helpTxt = arrowTxt + starTxt + crossTxt + heartTxt + fourTurnTxt;
		return helpTxt;
	}

	// Funtion generates win text
    public String winTxt(){
        String winTxt = curPlayer + " wins!";
        return winTxt;
    }

	// Function add piece in piecesArray
	public void addPiece(int row, int col, Piece newPiece){	piecesArray[row][col] = newPiece;}

	// Funtion remove piece from piecesArray
	public void removePiece(int row, int col){	piecesArray[row][col] = null;}

	@Override
	public void newGame(){
		turnCount = 0;
		curPlayer = p1;
		// Spawn GREEN pieces
		addPiece(0, 0, new Star("GREEN"));
		addPiece(0, 1, new Cross("GREEN"));
		addPiece(0, 2, new Heart("GREEN"));
		addPiece(0, 3, new Cross("GREEN"));
		addPiece(0, 4, new Star("GREEN"));
		addPiece(1, 1, new DownArrowBox("GREEN"));
		addPiece(1, 2, new DownArrowBox("GREEN"));
		addPiece(1, 3, new DownArrowBox("GREEN"));
		// Spawn RED pieces
		addPiece(7, 0, new Star("RED"));
		addPiece(7, 1, new Cross("RED"));
		addPiece(7, 2, new Heart("RED"));
		addPiece(7, 3, new Cross("RED"));
		addPiece(7, 4, new Star("RED"));
		addPiece(6, 1, new UpArrowBox("RED"));
		addPiece(6, 2, new UpArrowBox("RED"));
		addPiece(6, 3, new UpArrowBox("RED"));
		// Set empty as null
		for(int i=1; i<7; i++){
			if(i == 1 || i == 6){
				piecesArray[i][0] = null;
				piecesArray[i][4] = null;
			}
			else
				for(int j=0;j<5; j++){	piecesArray[i][j] = null;}
		}
	}

	@Override
	public void loadGame()
	{  try{
        FileInputStream saveFile = new FileInputStream("save.sav") ;
        restore = new ObjectInputStream(saveFile) ;
        piecesArray = (Piece[][]) restore.readObject() ;
        turnCount = (int) restore.readObject() ;
        curPlayer = (String) restore.readObject() ;
        restore.close() ;}

        catch(Exception exc)
        { exc.printStackTrace();}

	}

	@Override
	public void saveGame(String filename)
	{
    try{FileOutputStream saveFile = new FileOutputStream(filename);
     save = new ObjectOutputStream(saveFile) ;
     save.writeObject(piecesArray) ;
     save.writeObject(turnCount) ;
     save.writeObject(curPlayer) ;
     save.close() ;
	}
	catch(Exception exc)
	{ exc.printStackTrace() ;}
	}

	@Override
	public void selectPiece(int row, int col){
		Piece piece = piecesArray[row][col];
		if(piece != null && piecesArray[row][col].pieceColor == curPlayer)
			isPieceSelected = true;
		selectedPiece[0] = row;
		selectedPiece[1] = col;
	}

	@Override
	public void movePiece(int curRow, int curCol, int newRow, int newCol){
		addPiece(newRow, newCol, piecesArray[curRow][curCol]);
		removePiece(curRow, curCol);
		isPieceSelected = false;
		turnCount++;
		if(curPlayer == p1)
			curPlayer = p2;
		else
			curPlayer = p1;
	}

	@Override
    public boolean winTest(int row, int col){
		if(piecesArray[row][col].type == "heart"){
			return true;
		}
		else
			return false;
    }

	@Override
    public boolean fourTurnTest(){
        if (turnCount % 4 == 0)
            return true;
        else
			return false;
    }

    @Override
    public boolean arrowBoxReachEndTest(int row){
        if(row==0 || row==7)
			return true;
        else
			return false;
    }

    @Override
    public boolean upTest(int curRow, int curCol, int newRow ,int newCol){
        boolean inRange = false;
        int x = newRow - curRow;
        if(x >= -2 && x < 0)
			inRange = true;

		if (inRange == true && curCol == newCol)
			return true;
        else
			return false;
    }

    @Override
    public boolean downTest(int curRow, int curCol, int newRow ,int newCol){
        boolean inRange = false;
        int x = newRow - curRow;
        if(x <= 2 && x > 0)
			inRange = true;

		if (inRange == true && curCol == newCol)
			return true;
        else
			return false;
    }

    @Override
    public boolean diagonalTest(int curRow, int curCol, int newRow ,int newCol){
            int x = Math.abs(newRow - curRow);
            int y = Math.abs(newCol - curCol);

            if (x == y)
				return true;
            else
				return false;
       }

    @Override
    public boolean anyTest(int curRow, int curCol, int newRow ,int newCol, int maxStep){
        boolean inRangeRow = false;
        boolean inRangeCol = false;

        int x = Math.abs(newRow - curRow);
        int y = Math.abs(newCol - curCol);

        if ( x >= 0 && x <= maxStep)
            inRangeRow = true;
        if ( y >= 0 && y <= maxStep)
            inRangeCol = true;

		if(inRangeRow == false || inRangeCol == false)
			return false;
        else
			return true;

    }

    @Override
     public void flipArrowBox(int row,int col, String player)
    {
         if(piecesArray[row][col].direction == "up")
           {
                removePiece(row,col) ;
            addPiece(row,col, new DownArrowBox(player)) ;
           }

        else if(piecesArray[row][col].direction == "down")
            {removePiece(row,col) ;
            addPiece (row,col, new UpArrowBox(player)) ;}

    }

    @Override
    public void StarToCross()
    {

            for(int i=0; i<8; i++){
			for(int j=0; j<5; j++){
                if(piecesArray[i][j] != null && piecesArray[i][j].type == "star" )
                   {
                       String color =piecesArray[i][j].pieceColor ;
                    removePiece(i,j) ;
                    addPiece(i,j, new Cross(color)) ;
                   }
                else if(piecesArray[i][j] != null && piecesArray[i][j].type == "cross" )
                    {
                        String color =piecesArray[i][j].pieceColor ;
                        removePiece(i,j) ;
                    addPiece(i,j,new Star(color)) ;
                    }
			}
            }

    }



}
