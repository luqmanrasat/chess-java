import java.awt.*;

public class DownArrowBox extends Piece
{
    DownArrowBox(String pieceColor)
    {
		super("arrowbox", pieceColor, "down", 2);

		String iconPath;
        if(pieceColor == "GREEN")
			iconPath = "image/green_downarrowbox.png";
        else
			iconPath = "image/red_downarrowbox.png";

		this.iconPath = iconPath;
    }
}
