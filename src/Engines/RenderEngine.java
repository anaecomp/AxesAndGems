package Engines;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Sprites.Displayable;
import Sprites.Sprite;
import Sprites.Dynamic.Hero;
import Sprites.Dynamic.Troll;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RenderEngine extends JPanel implements Engine {

    private ArrayList<Displayable> renderList = new ArrayList();
    private ArrayList<Troll> trollList = new ArrayList<>();

    private Image imageGrass;

    public static volatile RenderEngine instance = null;

    private RenderEngine(){
        try {
            this.imageGrass = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/grass.png"));
        } catch (Exception e) {
            throw new RuntimeException("Could not load grass image", e);
        }
    }

    public static RenderEngine getInstance() {
        if (RenderEngine.instance == null) {
            synchronized (RenderEngine.class) {
                if (RenderEngine.instance == null) {
                    RenderEngine.instance = new RenderEngine();
                }
            }
        }
        return RenderEngine.instance;
    }

    public void update() {
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for(int i = 0; i < this.renderList.size(); ++i) {
            ((Displayable)this.renderList.get(i)).draw(g);
        }

        Hero.getInstance().drawInventory(g, 10, 20, "HERO");

        int y = 100;
        for(Troll troll : trollList){
            troll.drawInventory(g, 10, y, "TROLL");
            y += 80;
        }

    }

    public void addToRenderList(Displayable displayable) {
        this.renderList.add(displayable);
    }

    public void removeFromRenderList(Displayable displayable) {
        this.renderList.remove(displayable);
    }

    public void replaceWithGrass(Sprite oldSprite){
        removeFromRenderList(oldSprite);
        Sprite grass = new Sprite(imageGrass, oldSprite.getX(), oldSprite.getY(), oldSprite.getWidth(), oldSprite.getHeight());
        addToRenderList(grass);
        removeFromRenderList(Hero.getInstance());
        addToRenderList(Hero.getInstance());

        for(Troll troll : trollList){
            removeFromRenderList(troll);
            addToRenderList(troll);
        }
    }

    public void addTroll(Troll troll){
        this.trollList.add(troll);
    }
}

