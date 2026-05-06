import java.awt.*;

public final class Hero extends DynamicSprite{
    //Singleton Class Code Obtained from Reading #5 Design Patterns, Slide 9

    public static volatile Hero instance = null;

    private Hero(Image image, double x, double y, double width, double height){
        super(image,x,y,width,height);
    }

    public final static Hero getInstance(Image image, double x, double y, double width, double height){
        if(Hero.instance == null){
            synchronized (Hero.class) {
                if(Hero.instance == null){
                    Hero.instance = new Hero(image,x,y,width,height);
                }
            }
        }
        return Hero.instance;
    }

    public final static Hero getInstance(){
        return Hero.instance;
    }


}
