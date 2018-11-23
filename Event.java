import java.awt.*;
import java.io.* ;


public interface Event{
	// Function spawns pieces at first positions and game starts
	public void newGame();

	// Function spawns pieces at saved positions and game starts
	public void loadGame();

	// Function saves game state
	public void saveGame(String filename);

	// Function changes isPieceSelected boolean if player owns the selected piece
	public void selectPiece(int row, int col);

	// Function move piece to a new position if the move is valid
	public void movePiece(int curRow, int curCol, int newRow, int newCol);
}
