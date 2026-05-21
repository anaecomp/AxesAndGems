import Sprites.Displayable;
import Sprites.Sprite;
import Sprites.Solid.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlaygroundTest {

    @Test
    public void playgroundLoadsEnvironmentFromFile() {
        Playground playground = new Playground("test_playground_junit.txt");

        assertNotNull(playground.getEnvironment());
        assertFalse(playground.getEnvironment().isEmpty());
    }

    @Test
    public void playgroundCreatesCorrectNumberOfSprites() {
        Playground playground = new Playground("test_playground_junit.txt");

        assertEquals(6, playground.getEnvironment().size());
    }

    @Test
    public void playgroundCreatesPickaxeSprite() {
        Playground playground = new Playground("test_playground_junit.txt");

        boolean hasPickaxe = false;

        for (Sprite sprite : playground.getEnvironment()) {
            if (sprite instanceof PickaxeSprite) {
                hasPickaxe = true;
            }
        }

        assertTrue(hasPickaxe);
    }

    @Test
    public void playgroundCreatesRockSprite() {
        Playground playground = new Playground("test_playground_junit.txt");

        boolean hasRock = false;

        for (Sprite sprite : playground.getEnvironment()) {
            if (sprite instanceof RockSprite) {
                hasRock = true;
            }
        }

        assertTrue(hasRock);
    }

    @Test
    public void playgroundCreatesWizardSprite() {
        Playground playground = new Playground("test_playground_junit.txt");

        boolean hasWizard = false;

        for (Sprite sprite : playground.getEnvironment()) {
            if (sprite instanceof WizardSprite) {
                hasWizard = true;
            }
        }

        assertTrue(hasWizard);
    }

    @Test
    public void playgroundCreatesBridgeSprite() {
        Playground playground = new Playground("test_playground_junit.txt");

        boolean hasBridge = false;

        for (Sprite sprite : playground.getEnvironment()) {
            if (sprite instanceof BridgeSprite) {
                hasBridge = true;
            }
        }

        assertTrue(hasBridge);
    }

    @Test
    public void solidSpriteListOnlyContainsSolidSprites() {
        Playground playground = new Playground("test_playground_junit.txt");

        ArrayList<Sprite> solidSprites = playground.getSolidSpriteList();

        assertFalse(solidSprites.isEmpty());

        for (Sprite sprite : solidSprites) {
            assertTrue(sprite instanceof SolidSprite);
        }
    }

    @Test
    public void spriteListReturnsAllSpritesAsDisplayables() {
        Playground playground = new Playground("test_playground_junit.txt");

        ArrayList<Displayable> spriteList = playground.getSpriteList();

        assertEquals(playground.getEnvironment().size(), spriteList.size());
    }
}