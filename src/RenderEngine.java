//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class RenderEngine extends JPanel implements Engine {

    private ArrayList<Displayable> renderList = new ArrayList();
    public static volatile RenderEngine instance = null;

    private RenderEngine(){}

    public static RenderEngine getInstance() {
        if (RenderEngine.instance == null) {
            synchronized (RenderEngine.class) {
                if (RenderEngine.instance == null) {
                    RenderEngine.instance = new RenderEngine();
                }
            }
        }
        return RenderEngine.instance;
    }

    public void update() {
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for(int i = 0; i < this.renderList.size(); ++i) {
            ((Displayable)this.renderList.get(i)).draw(g);
        }

    }

    public void addToRenderList(Displayable displayable) {
        this.renderList.add(displayable);
    }

    public void removeFromRenderList(Displayable displayable) {
        this.renderList.remove(displayable);
    }
}

