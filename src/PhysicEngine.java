import java.util.ArrayList;

public class PhysicEngine implements Engine {
    public static volatile PhysicEngine instance = null;
    private ArrayList<DynamicSprite> movingSpriteList = new ArrayList();
    private ArrayList<Sprite> environment = new ArrayList();

    private PhysicEngine(){};

    public static PhysicEngine getInstance() {
        if (PhysicEngine.instance == null) {
            synchronized (PhysicEngine.class) {
                if (PhysicEngine.instance == null) {
                    PhysicEngine.instance = new PhysicEngine();
                }
            }
        }
        return PhysicEngine.instance;
    }

    public void addToMovingSpriteList(DynamicSprite dynamicSprite) {
        this.movingSpriteList.add(dynamicSprite);
    }

    public void addToEnvironmentList(Sprite sprite) {
        this.environment.add(sprite);
    }

    public void removeFromEnvironmentList(Sprite sprite) {
        this.environment.remove(sprite);
    }

    void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    public ArrayList<Sprite> getEnvironment() {
        return this.environment;
    }

    public ArrayList<DynamicSprite> getMovingSpriteList() {
        return this.movingSpriteList;
    }

    public void update() {
        for(DynamicSprite my_sprite : this.movingSpriteList) {
            my_sprite.moveIfPossible(this.environment);
        }

    }
}
