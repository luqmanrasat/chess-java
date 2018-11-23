import java.awt.*;

public interface Test{
	
	public boolean winTest(int row, int col);
	
	public boolean fourTurnTest();
	
	public boolean arrowBoxReachEndTest(int row);
	
	public boolean upTest(int curRow, int curCol, int newRow ,int newCol);
	
	public boolean downTest(int curRow, int curCol, int newRow ,int newCol);
	
	public boolean diagonalTest(int curRow, int curCol, int newRow ,int newCol);
	
	public boolean anyTest(int curRow, int curCol, int newRow ,int newCol, int maxStep);
}
	