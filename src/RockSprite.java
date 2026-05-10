import java.awt.*;

public class RockSprite extends SolidSprite {

    //RockSprite class meant to distinguish rocks from other solid sprite (i.e. trees and pickaxes), no other purpose
    public RockSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }
}
