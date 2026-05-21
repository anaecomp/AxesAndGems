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
import java.util.Collection;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RenderEngine extends JPanel implements Engine {

    private ArrayList<Displayable> renderList = new ArrayList();
    private ArrayList<Troll> trollList = new ArrayList<>();

    private boolean trollSteal = false;
    long trollStealTime = 0;


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


        //GAMESTATE IS PLAY
        for(int i = 0; i < this.renderList.size(); ++i) {
            ((Displayable)this.renderList.get(i)).draw(g);
        }

        //Hero Inventory
        Hero.getInstance().drawInventory(g, 10, 20, "HERO");

        // Objective Box
        g.setColor(new Color(40, 40, 60));
        g.fillRoundRect(10, 75, 180, 50, 15, 15);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("OBJECTIVE", 25, 95);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Collect " + GameEngine.getInstance().getRequiredGems() + " Gems", 25, 115);


        //Troll Inventory
        int x = 200;
        for(Troll troll : trollList){
            troll.drawInventory(g, x, 20, "TROLL");
            x += 200;
        }

        if(trollSteal){
            drawTrollStealScreen(g);
        }

        if(state == GameState.WIN){
            drawWinScreen(g);
            return;
        }

        if(state == GameState.GAMEOVER){
            drawGameOverScreen(g);
            return;
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

    public void showTrollStealScreen(){
        trollSteal = true;
        trollStealTime = System.currentTimeMillis();
        repaint();
    }

    private void drawWelcomeScreen(Graphics g) {

        // Background
        g.setColor(new Color(30, 30, 60));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Title panel
        g.setColor(new Color(60, 40, 100));
        g.fillRoundRect(350, 40, 500, 100, 30, 30);

        // Title
        g.setColor(new Color(253, 206, 90));
        g.setFont(new Font("Arial", Font.BOLD, 42));
        g.drawString("AXES & GEMS", 450, 100);

        // Subtitle
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced",Font.PLAIN,18));
        g.drawString("Mine gems. Escape trolls. Cross bridge.", 400, 130);

        // Controls
        g.setColor(new Color(50, 50, 80));
        g.fillRoundRect(350, 180, 500, 220, 25, 25);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("CONTROLS", 500, 220);

        g.setFont(new Font("Monospaced", Font.PLAIN, 18));

        g.drawString("ARROWS    -  Move", 460, 270);
        g.drawString("SPACE     -  Collect Axes", 460, 300);
        g.drawString("BACKSPACE -  Mine for Gems", 460, 330);


        // Troll warning
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("WARNING: Trolls can steal your gems!", 440, 450);

        // Blinking start text
        if((System.currentTimeMillis() / 500) % 2 == 0) {

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 26));

            g.drawString("PRESS ENTER TO START", 440, 520);
        }
    }

    private void drawMenuScreen(Graphics g) {

        // Background
        g.setColor(new Color(25, 25, 55));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Title
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 38));
        g.drawString("CHOOSE DIFFICULTY", 360, 100);

        // Instructions
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Press 1, 2, or 3 to begin", 410, 145);

        // Easy box
        g.setColor(new Color(40, 120, 60));
        g.fillRoundRect(375, 190, 360, 70, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("1 - EASY", 450, 235);

        // Medium box
        g.setColor(new Color(150, 110, 30));
        g.fillRoundRect(375, 290, 360, 70, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("2 - MEDIUM", 450, 335);

        // Hard box
        g.setColor(new Color(130, 40, 40));
        g.fillRoundRect(375, 390, 360, 70, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("3 - HARD", 450, 435);
    }

    private void drawWinScreen(Graphics g){

        // Transparent dark overlay over the game
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Main Panel - translucent
        g.setColor(new Color(50, 45, 75, 220));
        g.fillRoundRect(410, 160, 320, 220, 35, 35);

        // Outer Border
        g.setColor(new Color(120, 180, 255));
        g.drawRoundRect(410, 160, 320, 220, 35, 35);

        // Title
        g.setColor(new Color(255, 220, 120));
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("YOU ESCAPED", 440, 220);

        // Subtitle
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 15));
        g.drawString("The bridge has been", 470, 260);
        g.drawString("crossed successfully.", 470, 285);

        // Success text
        g.setColor(new Color(120, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("GEMS DELIVERED", 510, 310);
        g.drawString("SUCCESSFULLY", 520, 330);

        // Return prompt
        if((System.currentTimeMillis() / 500) % 2 == 0){

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            g.drawString("PRESS M FOR MENU", 480, 365);
        }
    }

    private void drawGameOverScreen(Graphics g){

        // Transparent dark overlay over the game
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Main Panel - translucent
        g.setColor(new Color(65, 45, 60, 220));
        g.fillRoundRect(410, 160, 320, 220, 35, 35);

        // Outer Border
        g.setColor(new Color(255, 120, 120));
        g.drawRoundRect(410, 160, 320, 220, 35, 35);

        // Title
        g.setColor(new Color(255, 120, 120));
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", 460, 220);

        // Subtitle
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 15));
        g.drawString("The dungeon claimed", 480, 260);
        g.drawString("your gems.", 520, 285);

        // Reason text
        g.setColor(new Color(255, 220, 120));
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("NOT ENOUGH RESOURCES", 475, 310);
        g.drawString("TO ESCAPE", 540, 330);

        // Return prompt
        if((System.currentTimeMillis() / 500) % 2 == 0){

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            g.drawString("PRESS M FOR MENU", 475, 365);
        }
    }

    private void drawTrollStealScreen(Graphics g){

        if(System.currentTimeMillis() - trollStealTime < 1000){

            g.setColor(new Color(0,0,0,180));
            g.fillRect(0,0,getWidth(),getHeight());

            g.setColor(new Color(255,0,0,120));
            g.fillRoundRect(25, 220, 340, 120, 30, 30);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("TROLL STOLE", 85, 270);
            g.drawString("YOUR GEMS!", 90, 310);

        } else {
            trollSteal = false;
        }

    }

    public void clearRenderList() {
        renderList.clear();
        trollList.clear();
    }

    public ArrayList<Displayable> getRenderList() {
        return renderList;
    }
}

