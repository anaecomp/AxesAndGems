public class Inventory {
    int axeCount;
    int gemCount;

    Inventory(int axeCount, int gemCount) {
        this.axeCount = axeCount;
        this.gemCount = gemCount;
    }

    void addAxe(){axeCount++;}
    void addGem(){gemCount++;}

    void removeAxe(){axeCount--;}
    void removeGem(){gemCount--;}

    public int getAxeCount() {return axeCount;}
    public int getGemCount() {return gemCount;}

    public boolean hasAxe() {return axeCount > 0;}
    public boolean hasGem() {return gemCount > 0;}
}
