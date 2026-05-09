//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
    JFrame displayZoneFrame = new JFrame("Java Labs");
   // PhysicEngine physicEngine;
    GameEngine gameEngine;
   // Playground playground;
    public Image imageGrass;

    public Main() throws Exception {
        this.displayZoneFrame.setSize(400, 600);
        this.displayZoneFrame.setDefaultCloseOperation(3);
        Hero.getInstance(ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/heroTileSheetLowRes.png")), (double)200.0F, (double)300.0F, (double)48.0F, (double)50.0F);
        try {
            this.imageGrass = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/grass.png"));
        } catch (Exception e) {
            throw new RuntimeException("Could not load grass image", e);
        }
     //   this.physicEngine = new PhysicEngine();
     //   this.playground = new Playground("test_playground.txt");
      //  this.gameEngine = new GameEngine(hero, this.playground.getEnvironment(), this.renderEngine, this.physicEngine);
        Timer renderTimer = new Timer(50, (time) -> RenderEngine.getInstance().update());
      //  Timer gameTimer = new Timer(50, (time) -> this.gameEngine.update());
      //  Timer physicTimer = new Timer(50, (time) -> this.physicEngine.update());
        renderTimer.start();
      //  gameTimer.start();
      //  physicTimer.start();
        this.displayZoneFrame.getContentPane().add(RenderEngine.getInstance());
        this.displayZoneFrame.setVisible(true);

     //   for(Displayable d : this.playground.getSpriteList()) {
     //       this.renderEngine.addToRenderList(d);
      //  }

     //   this.physicEngine.setEnvironment(this.playground.getSolidSpriteList());
        RenderEngine.getInstance().addToRenderList(Hero.getInstance());
     //   this.physicEngine.addToMovingSpriteList(hero);
     //   this.displayZoneFrame.addKeyListener(this.gameEngine);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}

