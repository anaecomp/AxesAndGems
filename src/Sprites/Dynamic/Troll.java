package Sprites.Dynamic;

import Engines.PhysicEngine;
import Engines.RenderEngine;
import Sprites.Solid.PickaxeSprite;
import Sprites.Sprite;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Troll extends DynamicSprite{

    private int trollCount = 0;
    private int moveDuration = 0;

    public Troll(Image image, double x, double y, double width, double height){
        super(image,x,y,width,height);
        speed = 3;
        trollCount++;
        setRandomDirection();
    }

    public int getTrollCount(){return trollCount;}

    private void setRandomDirection(){

        //Reset How Long Troll Moves in a Particular Direction
        moveDuration = 0;

        int randomDirection = (int) (Math.random() * 4);
        if(randomDirection == 0){
            setDirection(Direction.NORTH);
        }
        else if(randomDirection == 1){
            setDirection(Direction.SOUTH);
        }
        else if(randomDirection == 2){
            setDirection(Direction.EAST);
        }
        else if(randomDirection == 3){
            setDirection(Direction.WEST);
        }
    }

    public void autoMove(ArrayList<Sprite> environment){

        //Increment How Long Troll has Moved
        moveDuration++;
        //Reset Random Direction based on Move Duration
        if(moveDuration > 75 || !(this.isMovingPossible(environment))) {
            setRandomDirection();
        }

        moveIfPossible(environment);

    }

    public boolean stealIfPossible(){

        Rectangle2D.Double reachableBox = this.getInteractionBox();

        Rectangle2D.Double heroHitbox = new Rectangle2D.Double(
                Hero.getInstance().getX(),
                Hero.getInstance().getY(),
                Hero.getInstance().getWidth(),
                Hero.getInstance().getHeight()
        );

        if(reachableBox.intersects(heroHitbox)) {

            int stolenGems = Hero.getInstance().inventory.getGemCount();
            for(int i = 0; i < stolenGems; i++){
                Hero.getInstance().inventory.removeGem();
                this.inventory.addGem();
            }
            return true;
        }
        return false;
    }

}
