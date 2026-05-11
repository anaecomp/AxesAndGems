package Engines;

import Sprites.Dynamic.Hero;
import Sprites.Dynamic.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    public static volatile GameEngine instance = null;
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
        switch (e.getKeyCode()) {
            case 8 -> this.mineRequest = true;
            case 32 -> this.pickupRequest = true;
            case 37 -> Hero.getInstance().setDirection(Direction.WEST);
            case 38 -> Hero.getInstance().setDirection(Direction.NORTH);
            case 39 -> Hero.getInstance().setDirection(Direction.EAST);
            case 40 -> Hero.getInstance().setDirection(Direction.SOUTH);
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void update(){
        if(pickupRequest){
            Hero.getInstance().pickupIfPossible();
            pickupRequest = false;
        }

        if(mineRequest){
            Hero.getInstance().mineIfPossible();
            mineRequest = false;
        }
    }
}
