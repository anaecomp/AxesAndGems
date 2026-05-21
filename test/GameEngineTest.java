import Engines.Difficulty;
import Engines.GameEngine;
import Engines.GameState;
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
}
