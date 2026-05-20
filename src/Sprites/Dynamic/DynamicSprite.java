package Sprites.Dynamic;
import Engines.GameEngine;
import Engines.RenderEngine;
import Engines.PhysicEngine;
import Sprites.Solid.WizardSprite;
import Sprites.Sprite;
import Sprites.Solid.PickaxeSprite;
import Sprites.Solid.RockSprite;
import Sprites.Solid.SolidSprite;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {

    boolean isWalking;
    double speed;
    final int spriteSheetNumberOfColumn = 10;
    int timeBetweenFrame;
    Direction direction;
    Inventory inventory;

    public DynamicSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);

        //Default Values
        this.isWalking = true;
        this.speed = 5;
        this.timeBetweenFrame = 200;
        this.direction = Direction.SOUTH;
        this.inventory = new Inventory(0,0);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void draw(Graphics g){

        int index = (int)(System.currentTimeMillis()/timeBetweenFrame) % spriteSheetNumberOfColumn;
        int attitude = direction.getFrameLineNumber();

        //Source Calculations
        int sx1 = index*(int)super.getWidth();
        int sy1 = attitude*(int)super.getHeight();
        int sx2 = (index+1)*(int)super.getWidth();
        int sy2 = (attitude+1)*(int)super.getHeight();

        //Destination Calculations
        int dx1 = (int)super.getX();
        int dy1 = (int)super.getY();
        int dx2 = (int)super.getX() + (int)super.getWidth();
        int dy2 = (int) super.getY() + (int)super.getHeight();

        g.drawImage(super.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }

    private void move(){
        switch(direction){
            case Direction.NORTH -> {super.setY(super.getY()-speed);
                break;}
            case Direction.SOUTH -> {super.setY(super.getY()+speed);
                break;}
            case Direction.WEST -> {super.setX(super.getX()-speed);
                break;}
            case Direction.EAST -> {super.setX(super.getX()+speed);
                break;}
        }
    }

    boolean isMovingPossible(ArrayList<Sprite> environment){

        //Create X,Y Position to Test Possible Movement
        double x_test = super.getX();
        double y_test = super.getY();

        switch(direction){
            case Direction.NORTH -> {y_test = y_test - speed;}
            case Direction.SOUTH -> {y_test = y_test + speed;}
            case Direction.WEST -> {x_test = x_test - speed;}
            case Direction.EAST -> {x_test = x_test + speed;}
        }

        //Determine the hit box for hypothetical new position
        Rectangle2D.Double hitBox = new Rectangle2D.Double(x_test,y_test,super.getWidth(),super.getHeight());

        //Iterate through entire environment
        for(Sprite my_sprite : environment){

            //Skip over the Sprites.Dynamic.DynamicSprite itself
            if(my_sprite == this){continue;}

            //Only SolidSprites are needed to be checked
            if(!(my_sprite instanceof SolidSprite)){continue;}

            //Check hit box for the given sprite
            Rectangle2D.Double my_sprite_hitbox = new Rectangle2D.Double(my_sprite.getX(),my_sprite.getY(),my_sprite.getWidth(),my_sprite.getHeight());

            //Check if given sprite would interfere with potential new position
            if(hitBox.intersects(my_sprite_hitbox)){
                return false;
            }

        }


        //If no intersections occur, movement can occur
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if(isMovingPossible(environment)){move();}
    }

    Rectangle2D.Double getInteractionBox() {
        double reach = (double)100.0F;
        double x = super.getX();
        double y = super.getY();
        double w = super.getWidth();
        double h = super.getHeight();
        Rectangle2D.Double boxInFront = new Rectangle2D.Double();
        switch (this.direction) {
            case Direction.NORTH -> boxInFront = new Rectangle2D.Double(x, y - reach, w, reach);
            case Direction.SOUTH -> boxInFront = new Rectangle2D.Double(x, y + h, w, reach);
            case Direction.WEST -> boxInFront = new Rectangle2D.Double(x - reach, y, reach, h);
            case Direction.EAST -> boxInFront = new Rectangle2D.Double(x + w, y, reach, h);
        }
        return boxInFront;
    }

    public boolean pickupIfPossible(){
        Rectangle2D.Double reachableBox = this.getInteractionBox();

        for(Sprite my_sprite : PhysicEngine.getInstance().getEnvironment()) {
            if (my_sprite != this) {
                Rectangle2D.Double hitbox = new Rectangle2D.Double(my_sprite.getX(), my_sprite.getY(), my_sprite.getWidth(), my_sprite.getHeight());
                if (reachableBox.intersects(hitbox) && my_sprite instanceof PickaxeSprite) {
                    //Eliminate Pickaxe from the Environment List
                    PhysicEngine.getInstance().getEnvironment().remove(my_sprite);
                    //Add the Pickaxe to the Sprites.Sprite's Sprites.Dynamic.Inventory
                    this.inventory.addAxe();
                    //Replace the Pickaxe with Grass in the GUI Window
                    RenderEngine.getInstance().replaceWithGrass(my_sprite);
                    //Indicate Pickup Occurred
                    return true;
                }
            }
        }
        return false;
    }

    public boolean mineIfPossible(){
        if (!this.inventory.hasAxe()) {
            return false;
        }

            Rectangle2D.Double reachableBox = this.getInteractionBox();

            for(Sprite my_sprite : PhysicEngine.getInstance().getEnvironment()) {
                if (my_sprite != this) {
                    Rectangle2D.Double hitbox = new Rectangle2D.Double(my_sprite.getX(), my_sprite.getY(), my_sprite.getWidth(), my_sprite.getHeight());
                    if (reachableBox.intersects(hitbox) && my_sprite instanceof RockSprite) {
                        //Eliminate Rock from the Environment List
                        PhysicEngine.getInstance().getEnvironment().remove(my_sprite);
                        //Add the Gem to the Sprites.Sprite's Sprites.Dynamic.Inventory
                        this.inventory.addGem();
                        //Remove a Pickaxe from the Sprites.Sprite's Sprites.Dynamic.Inventory
                        this.inventory.removeAxe();
                        //Replace the Pickaxe with Grass in the GUI Window
                        RenderEngine.getInstance().replaceWithGrass(my_sprite);
                        //Indicate Pickup Occurred
                        return true;
                    }
                }
            }

            return false;

    }

    public boolean crossIfPossible(){
        Rectangle2D.Double reachableBox = this.getInteractionBox();

        for(Sprite my_sprite : PhysicEngine.getInstance().getEnvironment()) {
            if (my_sprite != this) {
                Rectangle2D.Double hitbox = new Rectangle2D.Double(my_sprite.getX(), my_sprite.getY(), my_sprite.getWidth(), my_sprite.getHeight());
                if (reachableBox.intersects(hitbox) && my_sprite instanceof WizardSprite) {
                    if(this.inventory.getGemCount() >= GameEngine.getInstance().getRequiredGems()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void drawGem(Graphics g, int x, int y){

        g.setColor(Color.CYAN);

        int[] xPoints = {x+10, x+20, x+10, x};
        int[] yPoints = {y, y+10, y+20, y+10};

        g.fillPolygon(xPoints, yPoints, 4);
    }

    public void drawPickaxe(Graphics g, int x, int y){

        // Handle
        g.setColor(new Color(139,69,19));
        g.fillRect(x+8, y+5, 4, 16);

        // Metal Head
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, 20, 4);
        g.fillRect(x+2, y+3, 4, 4);
        g.fillRect(x+14, y+3, 4, 4);
    }

    public void drawInventory(Graphics g, int startX, int startY, String name){

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(startX, startY, 180, 75, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(name, startX + 10, startY + 20);

        // Gem icon + count
        drawGem(g, startX + 15, startY + 30);
        g.setColor(Color.WHITE);
        g.drawString("x " + inventory.getGemCount(), startX + 45, startY + 48);

        // Pickaxe icon + count
        drawPickaxe(g, startX + 95, startY + 30);
        g.setColor(Color.WHITE);
        g.drawString("x " + inventory.getAxeCount(), startX + 125, startY + 48);
    }

}
