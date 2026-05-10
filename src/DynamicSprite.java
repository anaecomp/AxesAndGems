import org.w3c.dom.css.Rect;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{

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
            case NORTH -> {super.setY(super.getY()-speed);
                break;}
            case SOUTH -> {super.setY(super.getY()+speed);
                break;}
            case WEST -> {super.setX(super.getX()-speed);
                break;}
            case EAST -> {super.setX(super.getX()+speed);
                break;}
        }
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){

        //Create X,Y Position to Test Possible Movement
        double x_test = super.getX();
        double y_test = super.getY();

        switch(direction){
            case NORTH -> {y_test = y_test - speed;}
            case SOUTH -> {y_test = y_test + speed;}
            case WEST -> {x_test = x_test - speed;}
            case EAST -> {x_test = x_test + speed;}
        }

        //Determine the hit box for hypothetical new position
        Rectangle2D.Double hitBox = new Rectangle2D.Double(x_test,y_test,super.getWidth(),super.getHeight());

        //Iterate through entire environment
        for(Sprite my_sprite : environment){

            //Skip over the DynamicSprite itself
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

    private Rectangle2D.Double getInteractionBox() {
        double reach = (double)100.0F;
        double x = super.getX();
        double y = super.getY();
        double w = super.getWidth();
        double h = super.getHeight();
        Rectangle2D.Double boxInFront = new Rectangle2D.Double();
        switch (this.direction) {
            case NORTH -> boxInFront = new Rectangle2D.Double(x, y - reach, w, reach);
            case SOUTH -> boxInFront = new Rectangle2D.Double(x, y + h, w, reach);
            case WEST -> boxInFront = new Rectangle2D.Double(x - reach, y, reach, h);
            case EAST -> boxInFront = new Rectangle2D.Double(x + w, y, reach, h);
        }
        return boxInFront;
    }

    protected boolean pickupIfPossible(){
        Rectangle2D.Double reachableBox = this.getInteractionBox();

        for(Sprite my_sprite : PhysicEngine.getInstance().getEnvironment()) {
            if (my_sprite != this) {
                Rectangle2D.Double hitbox = new Rectangle2D.Double(my_sprite.getX(), my_sprite.getY(), my_sprite.getWidth(), my_sprite.getHeight());
                if (reachableBox.intersects(hitbox) && my_sprite instanceof PickaxeSprite) {
                    //Eliminate Pickaxe from the Environment List
                    PhysicEngine.getInstance().getEnvironment().remove(my_sprite);
                    //Add the Pickaxe to the Sprite's Inventory
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

    protected boolean mineIfPossible(){
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
                        //Add the Gem to the Sprite's Inventory
                        this.inventory.addGem();
                        //Remove a Pickaxe from the Sprite's Inventory
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



}
