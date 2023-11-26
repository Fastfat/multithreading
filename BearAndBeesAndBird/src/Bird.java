import java.util.Random;

public class Bird {
    Random random = new Random();
    int x,y;

    boolean isBeeAlive(int x, int y) {
        this.x = random.nextInt(10);
        this.y = random.nextInt(10);
        return !(this.x == x && this.y == y);
    }
}
