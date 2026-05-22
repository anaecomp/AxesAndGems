import Engines.PhysicEngine;
import Sprites.Dynamic.DynamicSprite;
import Sprites.Solid.PickaxeSprite;
import Sprites.Solid.RockSprite;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhysicEngineTest {
    @Test
    public void physicEngineIsSingleton() {
        PhysicEngine p1 = PhysicEngine.getInstance();
        PhysicEngine p2 = PhysicEngine.getInstance();

        assertNotNull(p1);
        assertSame(p1, p2);
    }

    @Test
    public void physicEngineStoresSpritesInEnvironment() {
        PhysicEngine.getInstance().getEnvironment().clear();

        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        PhysicEngine.getInstance().getEnvironment().add(sprite);

        assertTrue(PhysicEngine.getInstance().getEnvironment().contains(sprite));
    }

    @Test
    public void physicEngineCanClearEnvironment() {
        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(sprite);
        PhysicEngine.getInstance().getEnvironment().clear();
        assertTrue(PhysicEngine.getInstance().getEnvironment().isEmpty());

    }

    @Test
    public void physicEngineCanCount(){
        PhysicEngine.getInstance().getEnvironment().clear();
        RockSprite rockSprite = new RockSprite(null, 100, 100, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(rockSprite);
        PickaxeSprite pickaxeSprite = new PickaxeSprite(null, 100, 100, 50, 50);
        PhysicEngine.getInstance().getEnvironment().add(pickaxeSprite);

        assertEquals(1,PhysicEngine.getInstance().countRemainingRocks());
        assertEquals(1,PhysicEngine.getInstance().countRemainingPickaxes());
    }
}
