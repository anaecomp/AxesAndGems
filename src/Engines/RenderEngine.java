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

        GameState state = GameEngine.getInstance().getGameState();

        if(state == GameState.WELCOME) {
            drawWelcomeScreen(g);
            return;
        }

        if(state == GameState.MENU){
            drawMenuScreen(g);
            return;
        }

        for(int i = 0; i < this.renderList.size(); ++i) {
            ((Displayable)this.renderList.get(i)).draw(g);
        }

        Hero.getInstance().drawInventory(g, 10, 20, "HERO");

        for(Troll troll : trollList){
            troll.drawInventory(g, 200, 20, "TROLL");
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

    private void drawWelcomeScreen(Graphics g) {

        // Background
        g.setColor(new Color(30, 30, 60));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Title panel
        g.setColor(new Color(60, 40, 100));
        g.fillRoundRect(50, 40, 500, 100, 30, 30);

        // Title
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 42));
        g.drawString("AXES & GEMS", 135, 105);

        // Subtitle
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced",Font.PLAIN,18));
        g.drawString("Mine gems. Escape trolls. Cross bridge.", 70, 145);

        // Controls
        g.setColor(new Color(50, 50, 80));
        g.fillRoundRect(50, 180, 500, 220, 25, 25);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("CONTROLS", 220, 220);

        g.setFont(new Font("Monospaced", Font.PLAIN, 18));

        g.drawString("ARROWS    -  Move", 130, 270);
        g.drawString("SPACE     -  Collect Axes", 130, 300);
        g.drawString("BACKSPACE -  Mine for Gems", 130, 330);

        // Decorative icons
        // Hero.getInstance().drawPickaxe(g, 420, 285);
        // Hero.getInstance().drawGem(g, 420, 315);


        // Troll warning
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("WARNING: Trolls can steal your gems!", 110, 450);

        // Blinking start text
        if((System.currentTimeMillis() / 500) % 2 == 0) {

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 26));

            g.drawString("PRESS ENTER TO START", 120, 520);
        }
    }

    private void drawMenuScreen(Graphics g) {

        // Background
        g.setColor(new Color(25, 25, 55));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Title
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 38));
        g.drawString("CHOOSE DIFFICULTY", 60, 100);

        // Instructions
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Press 1, 2, or 3 to begin", 130, 145);

        // Easy box
        g.setColor(new Color(40, 120, 60));
        g.fillRoundRect(80, 190, 360, 70, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("1 - EASY", 140, 235);

        // Medium box
        g.setColor(new Color(150, 110, 30));
        g.fillRoundRect(80, 290, 360, 70, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("2 - MEDIUM", 140, 335);

        // Hard box
        g.setColor(new Color(130, 40, 40));
        g.fillRoundRect(80, 390, 360, 70, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("3 - HARD", 140, 435);
    }

    public void clearRenderList() {
        renderList.clear();
    }
}

