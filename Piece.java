import java.awt.*;

public abstract	class Piece{
	protected String type;
	protected String pieceColor;
	protected String direction;
	protected int maxStep;
	protected String iconPath; 
	
	Piece(String type, String pieceColor, String direction, int maxStep){
		this.type = type;
		this.pieceColor = pieceColor;
		this.direction = direction;
		this.maxStep = maxStep;
	}
}