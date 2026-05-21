import Engines.RenderEngine;
import Sprites.Displayable;
import Sprites.Dynamic.DynamicSprite;
import Sprites.Solid.SolidSprite;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RenderEngineTest {

    @Test
    public void renderEngineIsSingleton() {
        RenderEngine r1 = RenderEngine.getInstance();
        RenderEngine r2 = RenderEngine.getInstance();

        assertNotNull(r1);
        assertSame(r1, r2);
    }

    @Test
    public void renderEngineCanAddDisplayableToRenderList() {
        RenderEngine renderEngine = RenderEngine.getInstance();

        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        renderEngine.addToRenderList(sprite);

        assertTrue(renderEngine.getRenderList().contains(sprite));
    }

    @Test
    public void renderEngineCanRemoveDisplayableFromRenderList() {
        RenderEngine renderEngine = RenderEngine.getInstance();

        DynamicSprite sprite = new DynamicSprite(null, 100, 100, 50, 50);

        renderEngine.addToRenderList(sprite);
        renderEngine.getRenderList().remove(sprite);

        assertFalse(renderEngine.getRenderList().contains(sprite));
    }
}