import Engines.Difficulty;
import Engines.GameEngine;
import Engines.GameState;
import Engines.PhysicEngine;
import Sprites.Dynamic.Direction;
import Sprites.Dynamic.DynamicSprite;
import Sprites.Dynamic.Hero;
import Sprites.Solid.PickaxeSprite;
import Sprites.Solid.RockSprite;
import Sprites.Solid.WizardSprite;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;


public class GameEngineTest {

    @Test
    public void gameEngineIsSingleton() {
        GameEngine g1 = GameEngine.getInstance();
        GameEngine g2 = GameEngine.getInstance();

        assertNotNull(g1);
        assertSame(g1, g2);
    }

    @Test
    public void gameEngineStoresRequiredGems() {
        assertEquals(1, GameEngine.getInstance().getRequiredGems());
    }

    @Test
    public void gameStartsOnWelcomeScreen() {
        GameEngine.resetInstance();
        assertEquals(GameState.WELCOME, GameEngine.getInstance().getGameState());
    }

    @Test
    public void gameCanSwitchToMenuState() {
        GameEngine.getInstance().setGameState(GameState.MENU);

        assertEquals(GameState.MENU, GameEngine.getInstance().getGameState());
    }

    @Test
    public void gameCanSwitchToPlayingState() {
        GameEngine.getInstance().setGameState(GameState.PLAY);

        assertEquals(GameState.PLAY, GameEngine.getInstance().getGameState());
    }

    @Test
    public void gameCanSwitchToGameOverState() {
        GameEngine.getInstance().setGameState(GameState.GAMEOVER);

        assertEquals(GameState.GAMEOVER, GameEngine.getInstance().getGameState());
    }

    @Test
    public void gameCanSwitchToWinState() {
        GameEngine.getInstance().setGameState(GameState.WIN);

        assertEquals(GameState.WIN, GameEngine.getInstance().getGameState());
    }

    private void pickupAxeAndMineOneGem(DynamicSprite sprite) {
        sprite.setDirection(Direction.SOUTH);

        PickaxeSprite axe = new PickaxeSprite(null, 100, 175, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(axe);

        assertTrue(sprite.pickupIfPossible());

        RockSprite rock = new RockSprite(null, 100, 175, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(rock);

        assertTrue(sprite.mineIfPossible());
    }

}
