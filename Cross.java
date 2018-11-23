import java.awt.*;

public class Cross extends Piece
{
    Cross(String pieceColor)
    {
		super("cross", pieceColor, "diagonal", 7);

		String iconPath;
        if(pieceColor == "GREEN")
			iconPath = "image/green_cross.png";
        else
			iconPath = "image/red_cross.png";

		this.iconPath = iconPath;
    }
}
