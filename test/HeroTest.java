import Sprites.Dynamic.Hero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {

    @Test
    public void heroInstanceExists() {
        assertNotNull(Hero.getInstance(null, 100, 100, 50, 50));
    }

    @Test
    public void heroIsSingleton() {

        Hero hero1 = Hero.getInstance(null, 100, 100, 50, 50);
        Hero hero2 = Hero.getInstance(null, 120, 120, 55, 55);

        assertSame(hero1, hero2);
    }

    @Test
    public void heroHasInventory() {
        assertNotNull(Hero.getInstance(null, 100, 100, 50, 50).getInventory());
    }

    @Test
    public void heroStartsWithEmptyInventory() {
        assertEquals(0, Hero.getInstance(null, 100, 100, 50, 50).getInventory().getAxeCount());
        assertEquals(0, Hero.getInstance().getInventory().getGemCount());
    }
}