package Engines;

import Sprites.Dynamic.DynamicSprite;
import Sprites.Dynamic.Hero;
import Sprites.Dynamic.Direction;
import Sprites.Dynamic.Troll;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    public static volatile GameEngine instance = null;
    private GameState gameState = GameState.WELCOME;
    private boolean levelSelected = false;
    private Difficulty difficulty;

    private int requiredGems = 0;
    private boolean pickupRequest = false;
    private boolean mineRequest = false;

    private GameEngine(){};

    public static GameEngine getInstance() {
        if (GameEngine.instance == null) {
            synchronized (GameEngine.class) {
                if (GameEngine.instance == null) {
                    GameEngine.instance = new GameEngine();
                }
            }
        }
        return GameEngine.instance;
    }


    public void keyPressed(KeyEvent e) {

        //Wait to Exit the Welcome State and Present Menu
        if(this.gameState == GameState.WELCOME && e.getKeyCode() == KeyEvent.VK_ENTER){
            gameState = GameState.MENU;
        }

        //Wait for User to Select a Level of Difficulty
        if(this.gameState == GameState.MENU){
            switch(e.getKeyCode()){
                case KeyEvent.VK_1 -> {
                        difficulty = Difficulty.EASY;
                        levelSelected = true;
                }
                case KeyEvent.VK_2 -> {
                    difficulty = Difficulty.MEDIUM;
                    levelSelected = true;
                }
                case KeyEvent.VK_3 -> {
                    difficulty = Difficulty.HARD;
                    levelSelected = true;
                }
            }
        }

        //User Manipulation of Hero during Game Play
        if(this.gameState == GameState.PLAY){
            switch (e.getKeyCode()) {
                case 8 -> this.mineRequest = true;
                case 32 -> this.pickupRequest = true;
                case 37 -> Hero.getInstance().setDirection(Direction.WEST);
                case 38 -> Hero.getInstance().setDirection(Direction.NORTH);
                case 39 -> Hero.getInstance().setDirection(Direction.EAST);
                case 40 -> Hero.getInstance().setDirection(Direction.SOUTH);
            }
        }

        //Return to Menu Selection
        if(this.gameState == GameState.WIN || this.gameState == GameState.GAMEOVER){
            if(e.getKeyCode() == KeyEvent.VK_M){

                resetGame();
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public int getRequiredGems() {
        return requiredGems;
    }

    public void update(){

        if(this.gameState == GameState.PLAY){
            if(pickupRequest){
                Hero.getInstance().pickupIfPossible();
                pickupRequest = false;
            }

            if(mineRequest){
                Hero.getInstance().mineIfPossible();
                mineRequest = false;
            }

            if(Hero.getInstance().crossIfPossible()){
                gameState = GameState.WIN;
            }

            for(DynamicSprite mySprite : PhysicEngine.getInstance().getMovingSpriteList()) {

                if(mySprite.crossIfPossible() && mySprite instanceof Troll) {
                    gameState = GameState.GAMEOVER;
                }
            }



        }


    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getLevelFile() {

        if(difficulty == Difficulty.EASY) {
            return "src/level1.txt";
        }
        else if(difficulty == Difficulty.MEDIUM) {
            return "src/level2.txt";
        }
        else {
            return "src/level3.txt";
        }
    }

    public boolean isLevelSelected() {
        return levelSelected;
    }

    public void resetGame(){

        //Reset Game Variables
        difficulty = null;
        levelSelected = false;
        requiredGems = 0;

        pickupRequest = false;
        mineRequest = false;

        //Clear Render and Physics Lists
        RenderEngine.getInstance().clearRenderList();
        PhysicEngine.getInstance().clearMovingSpriteList();

        //Reset Hero Singleton
        Hero.resetInstance();

        //Return to Menu
        gameState = GameState.MENU;
    }

    public void setLevelSelected(boolean b) {
        levelSelected = b;
    }
}
