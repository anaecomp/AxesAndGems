//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Engines.GameEngine;
import Engines.PhysicEngine;
import Engines.RenderEngine;
import Sprites.Displayable;
import Sprites.Dynamic.Hero;
import Sprites.Dynamic.Troll;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

    JFrame displayZoneFrame = new JFrame("Java Labs");
    public Playground playground;
    public Image imageGrass;

    public Main() throws Exception {

        //Configure GUI Window and Game Environment
        this.displayZoneFrame.setSize(400, 600);
        this.displayZoneFrame.setDefaultCloseOperation(3);
        this.playground = new Playground("src/test.txt");
        Hero.getInstance(ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/heroTileSheetLowRes.png")), (double)200.0F, (double)300.0F, (double)48.0F, (double)50.0F);

        //Configure Enemies
        Troll troll = new Troll(ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/trollTileSheetLowRes.png")), (double)200.0F, (double)300.0F, (double)48.0F, (double)50.0F);
        RenderEngine.getInstance().addTroll(troll);


        try {
            this.imageGrass = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/grass.png"));
        } catch (Exception e) {
            throw new RuntimeException("Could not load grass image", e);
        }

        //Configure Engines.Engine Timers
        Timer renderTimer = new Timer(50, (time) -> RenderEngine.getInstance().update());
        Timer gameTimer = new Timer(50, (time) -> GameEngine.getInstance().update());
        Timer physicTimer = new Timer(50, (time) -> PhysicEngine.getInstance().update());
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();


        for(Displayable d : this.playground.getSpriteList()) {
            RenderEngine.getInstance().addToRenderList(d);
        }

        PhysicEngine.getInstance().setEnvironment(this.playground.getSolidSpriteList());
        RenderEngine.getInstance().addToRenderList(Hero.getInstance());
        PhysicEngine.getInstance().addToMovingSpriteList(Hero.getInstance());
        RenderEngine.getInstance().addToRenderList(troll);
        PhysicEngine.getInstance().addToMovingSpriteList(troll);

        this.displayZoneFrame.add(RenderEngine.getInstance());
        this.displayZoneFrame.setVisible(true);
        this.displayZoneFrame.addKeyListener(GameEngine.getInstance());
        this.displayZoneFrame.addKeyListener(GameEngine.getInstance());
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}

