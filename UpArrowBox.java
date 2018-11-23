import java.awt.*;

public class UpArrowBox extends Piece
{
    UpArrowBox(String pieceColor)
    {
		super("arrowbox", pieceColor, "up", 2);

		String iconPath;
        if(pieceColor == "GREEN")
			iconPath = "image/green_uparrowbox.png";
        else
			iconPath = "image/red_uparrowbox.png";
		
		this.iconPath = iconPath;
    }
}
