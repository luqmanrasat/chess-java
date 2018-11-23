import java.awt.*;

public class Heart extends Piece
{
    Heart(String pieceColor)
    {
		super("heart", pieceColor, "any", 1);

		String iconPath;
        if(pieceColor == "GREEN")
			iconPath = "image/green_heart.png";
        else
			iconPath = "image/red_heart.png";

		this.iconPath = iconPath;
    }
}
