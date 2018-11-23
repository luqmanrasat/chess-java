import java.awt.*;

public class Star extends Piece
{
    Star(String pieceColor)
    {
		super("star", pieceColor, "any", 2);

		String iconPath;
        if(pieceColor == "GREEN")
			iconPath = "image/green_star.png";
        else
			iconPath = "image/red_star.png";
		
		this.iconPath = iconPath;
    }
}
