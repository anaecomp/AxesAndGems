package Sprites.Solid;
import java.awt.*;

public class PickaxeSprite extends SolidSprite {
    //Sprites.Solid.PickaxeSprite class meant to distinguish pickaxes from other solid sprite (i.e. trees and rocks) so they can be picked up, no other purpose
    public PickaxeSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }
}
