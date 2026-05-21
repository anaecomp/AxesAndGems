import Engines.GameEngine;
import Engines.PhysicEngine;
import Sprites.Dynamic.DynamicSprite;
import Sprites.Dynamic.Direction;
import Sprites.Solid.PickaxeSprite;
import Sprites.Solid.RockSprite;
import Sprites.Solid.WizardSprite;
import Sprites.Sprite;
import Sprites.Solid.SolidSprite;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicSpriteTest {

    @Test
    public void dynamicSpriteHasInventory() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        assertNotNull(sprite.getInventory());
    }

    @Test
    public void dynamicSpriteStartsWithEmptyInventory() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        assertEquals(0, sprite.getInventory().getAxeCount());
        assertEquals(0, sprite.getInventory().getGemCount());
    }

    @Test
    public void dynamicSpriteMovesEastWhenNothingBlocksIt() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.EAST);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(sprite);

        sprite.moveIfPossible(environment);

        assertEquals(105, sprite.getX());
        assertEquals(100, sprite.getY());
    }

    @Test
    public void dynamicSpriteMovesWestWhenNothingBlocksIt() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.WEST);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(sprite);

        sprite.moveIfPossible(environment);

        assertEquals(95, sprite.getX());
        assertEquals(100, sprite.getY());
    }

    @Test
    public void dynamicSpriteMovesNorthWhenNothingBlocksIt() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.NORTH);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(sprite);

        sprite.moveIfPossible(environment);

        assertEquals(100, sprite.getX());
        assertEquals(95, sprite.getY());
    }

    @Test
    public void dynamicSpriteMovesSouthWhenNothingBlocksIt() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.SOUTH);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(sprite);

        sprite.moveIfPossible(environment);

        assertEquals(100, sprite.getX());
        assertEquals(105, sprite.getY());
    }

    @Test
    public void dynamicSpriteCannotMoveIntoSolidSprite() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.EAST);

        SolidSprite wall = new SolidSprite(null, 105, 100, 50, 50);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(sprite);
        environment.add(wall);

        sprite.moveIfPossible(environment);

        assertEquals(100, sprite.getX());
        assertEquals(100, sprite.getY());
    }

    @Test
    public void dynamicSpriteCanMoveWhenSolidSpriteIsNotBlocking() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.EAST);

        SolidSprite wall = new SolidSprite(null, 300, 300, 50, 50);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(sprite);
        environment.add(wall);

        sprite.moveIfPossible(environment);

        assertEquals(105, sprite.getX());
        assertEquals(100, sprite.getY());
    }

    @Test
    public void dynamicSpriteCannotMineWithoutAxe() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.SOUTH);

        RockSprite rock = new RockSprite(null, 100, 175, 50, 50);

        PhysicEngine.getInstance().getEnvironment().clear();
        PhysicEngine.getInstance().getEnvironment().add(sprite);
        PhysicEngine.getInstance().getEnvironment().add(rock);

        boolean mined = sprite.mineIfPossible();

        assertFalse(mined);
        assertEquals(0, sprite.getInventory().getGemCount());
    }

    @Test
    public void dynamicSpriteCannotPickupAxeWhenNotReachable() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.SOUTH);

        PickaxeSprite axe = new PickaxeSprite(null, 300, 300, 50, 50);

        PhysicEngine.getInstance().getEnvironment().clear();
        PhysicEngine.getInstance().getEnvironment().add(sprite);
        PhysicEngine.getInstance().getEnvironment().add(axe);

        boolean pickedUp = sprite.pickupIfPossible();

        assertFalse(pickedUp);
        assertEquals(0, sprite.getInventory().getAxeCount());
    }

    @Test
    public void dynamicSpriteCannotMineWhenRockIsNotReachable() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        sprite.setDirection(Direction.SOUTH);

        PhysicEngine.getInstance().getEnvironment().clear();

        PickaxeSprite axe = new PickaxeSprite(null, 100, 175, 50, 50);

        PhysicEngine.getInstance().getEnvironment().add(sprite);
        PhysicEngine.getInstance().getEnvironment().add(axe);

        boolean pickedUp = sprite.pickupIfPossible();

        assertTrue(pickedUp);
        assertEquals(1, sprite.getInventory().getAxeCount());

        RockSprite farAwayRock = new RockSprite(null, 300, 300, 50, 50);

        PhysicEngine.getInstance().getEnvironment().add(farAwayRock);

        boolean mined = sprite.mineIfPossible();

        assertFalse(mined);
        assertEquals(0, sprite.getInventory().getGemCount());
        assertEquals(1, sprite.getInventory().getAxeCount());
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

    @Test
    public void dynamicSpriteCanCrossWhenWizardReachableAndEnoughGems() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        PhysicEngine.getInstance().getEnvironment().clear();
        PhysicEngine.getInstance().getEnvironment().add(sprite);

        int requiredGems = GameEngine.getInstance().getRequiredGems();

        for (int i = 0; i < requiredGems; i++) {
            pickupAxeAndMineOneGem(sprite);
        }

        WizardSprite wizard = new WizardSprite(null, 100, 175, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(wizard);

        sprite.setDirection(Direction.SOUTH);

        boolean crossed = sprite.crossIfPossible();

        assertTrue(crossed);
    }

    @Test
    public void dynamicSpriteCannotCrossWhenWizardReachableButNotEnoughGems() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        PhysicEngine.getInstance().getEnvironment().clear();
        PhysicEngine.getInstance().getEnvironment().add(sprite);

        WizardSprite wizard = new WizardSprite(null, 100, 175, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(wizard);

        sprite.setDirection(Direction.SOUTH);

        boolean crossed = sprite.crossIfPossible();

        assertFalse(crossed);
    }

    @Test
    public void dynamicSpriteCannotCrossWhenWizardIsNotReachableEvenWithEnoughGems() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        PhysicEngine.getInstance().getEnvironment().clear();
        PhysicEngine.getInstance().getEnvironment().add(sprite);

        int requiredGems = GameEngine.getInstance().getRequiredGems();

        for (int i = 0; i < requiredGems; i++) {
            pickupAxeAndMineOneGem(sprite);
        }

        WizardSprite wizard = new WizardSprite(null, 300, 300, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(wizard);

        sprite.setDirection(Direction.SOUTH);

        boolean crossed = sprite.crossIfPossible();

        assertFalse(crossed);
    }


}
