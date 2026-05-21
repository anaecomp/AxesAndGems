import Sprites.Dynamic.*;
import Sprites.Sprite;
import Engines.PhysicEngine;
import Sprites.Solid.RockSprite;
import Sprites.Solid.PickaxeSprite;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TrollTest {

    @Test
    public void trollCountStartsAtOne() {
        Troll troll = new Troll(null, 100, 100, 50, 50);

        assertEquals(1, troll.getTrollCount());
    }

    @Test
    public void trollStartsWithSpeedOfTwo() {
        Troll troll = new Troll(null, 100, 100, 50, 50);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(troll);

        double oldX = troll.getX();
        double oldY = troll.getY();

        troll.autoMove(environment);

        double distanceMoved = Math.abs(troll.getX() - oldX) + Math.abs(troll.getY() - oldY);

        assertEquals(2, distanceMoved);
    }

    @Test
    public void trollAutoMoveChangesPosition() {
        Troll troll = new Troll(null, 100, 100, 50, 50);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(troll);

        double oldX = troll.getX();
        double oldY = troll.getY();

        troll.autoMove(environment);

        boolean moved = troll.getX() != oldX || troll.getY() != oldY;

        assertTrue(moved);
    }

    @Test
    public void trollDoesNotStealWhenHeroHasNoGems() {
        Hero.resetInstance();
        Hero hero = Hero.getInstance(null, 100, 100, 50, 50);
        Troll troll = new Troll(null, 100, 160, 50, 50);

        boolean result = troll.stealIfPossible();

        assertFalse(result);
        assertEquals(0, troll.getInventory().getGemCount());
    }

    @Test
    public void trollStealsHeroGemAfterHeroPicksUpAxeAndMinesRock() {
        Hero.resetInstance();
        Hero hero = Hero.getInstance(null, 100, 100, 50, 50);
        Troll troll = new Troll(null, 100, 160, 50, 50);
        troll.setDirection(Direction.NORTH);

        PhysicEngine.getInstance().getEnvironment().clear();

        PickaxeSprite axe = new PickaxeSprite(null, 100, 175, 50, 50);
        RockSprite rock = new RockSprite(null, 100, 175, 50, 50);

        PhysicEngine.getInstance().getEnvironment().add(hero);
        PhysicEngine.getInstance().getEnvironment().add(axe);

        hero.setDirection(Direction.SOUTH);

        boolean pickedUp = hero.pickupIfPossible();
        assertTrue(pickedUp);

        PhysicEngine.getInstance().getEnvironment().add(rock);

        boolean mined = hero.mineIfPossible();
        assertTrue(mined);

        assertEquals(1, hero.getInventory().getGemCount());

        boolean stolen = troll.stealIfPossible();

        assertTrue(stolen);
        assertEquals(0, hero.getInventory().getGemCount());
        assertEquals(1, troll.getInventory().getGemCount());
    }


}
