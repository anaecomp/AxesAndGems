//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList();

    public Playground(String pathName) {
        try {
            Image imageTree = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/tree.png"));
            Image imageGrass = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/grass.png"));
            Image imageRock = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/rock.png"));
            Image imageTrap = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/trap.png"));
            Image imagePickaxe = ImageIO.read(new File("C://Users//anaec//IdeaProjects//DungeonCrawler//Resources//img/pickaxe.png"));
            int imageTreeWidth = imageTree.getWidth((ImageObserver)null);
            int imageTreeHeight = imageTree.getHeight((ImageObserver)null);
            int imageGrassWidth = imageGrass.getWidth((ImageObserver)null);
            int imageGrassHeight = imageGrass.getHeight((ImageObserver)null);
            int imageRockWidth = imageRock.getWidth((ImageObserver)null);
            int imageRockHeight = imageRock.getHeight((ImageObserver)null);
            int imagePickaxeWidth = imagePickaxe.getWidth((ImageObserver)null);
            int imagePickaxeHeight = imagePickaxe.getHeight((ImageObserver)null);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;

            for(int columnNumber = 0; line != null; line = bufferedReader.readLine()) {
                for(byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 32 -> this.environment.add(new Sprite(imageGrass, (double)(columnNumber * imageGrassWidth), (double)(lineNumber * imageGrassHeight), (double)imageGrassWidth, (double)imageGrassHeight));
                        case 80 -> this.environment.add(new PickaxeSprite(imagePickaxe, (double)(columnNumber * imagePickaxeWidth), (double)(lineNumber * imagePickaxeHeight), (double)imagePickaxeWidth, (double)imagePickaxeHeight));
                        case 82 -> this.environment.add(new RockSprite(imageRock, (double)(columnNumber * imageRockWidth), (double)(lineNumber * imageRockHeight), (double)imageRockWidth, (double)imageRockHeight));
                        case 84 -> this.environment.add(new SolidSprite(imageTree, (double)(columnNumber * imageTreeWidth), (double)(lineNumber * imageTreeHeight), (double)imageTreeWidth, (double)imageTreeHeight));
                    }

                    ++columnNumber;
                }

                columnNumber = 0;
                ++lineNumber;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList();

        for(Sprite sprite : this.environment) {
            if (sprite instanceof SolidSprite) {
                solidSpriteArrayList.add(sprite);
            }
        }

        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList();

        for(Sprite sprite : this.environment) {
            displayableArrayList.add(sprite);
        }

        return displayableArrayList;
    }

    public ArrayList<Sprite> getEnvironment() {
        return this.environment;
    }
}

