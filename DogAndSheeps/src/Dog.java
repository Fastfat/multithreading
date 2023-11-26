import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dog extends Thread {
    private int x;
    private int y;
    List<Sheep> sheepsUpdateList = new ArrayList<>();
    Map<String, Sheep> sheeps;
    Field field;

    Dog(String name, HashMap<String, Sheep> sheeps, Field field) {
        super(name);
        x = 0;
        y = 0;
        this.sheeps = sheeps;
        this.field = field;
    }

    public void run() {
        int[] position;
        while (true) {
            for (Sheep sheep : sheeps.values()) {
                position = sheep.getPosition();
                if (!field.getFieldPosition(position[0], position[1])) {
                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    position = sheep.getPosition();
                    if (sheep.isAlive()) {
                        if (!field.getFieldPosition(position[0], position[1])) {
                            sheep.getAfraid(bark());
                            field.getPictureFieldPosition(this, sheep);//двигаться к центру забора
                            if (((position[0] > 12 || position[0] < 3)
                                    && (position[1] < 13 && position[1] > 2)) || (position[1] < 3 || position[1] > 12)) {
                                sheep.changeDirection(setSnail(sheep));
                                try {
                                    Thread.sleep(500);
                                }
                                catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                sheep.getAfraid(bark());
                            } else {
                                sheep.changeDirection(setSnail(sheep));
                                while (!(((position[0] > 12 || position[0] < 3)
                                        && (position[1] < 13 && position[1] > 2)) || (position[1] < 3
                                        || position[1] > 12))) {
                                    position = sheep.getPosition();
                                    setPosition(position[0], position[1]);
                                }
                                sheep.getAfraid(bark());
                            }
                            sheepsUpdateList.add(new Sheep("Sheep" + (sheeps.size() + 1), field, 7, 7));
                        }
                    }
                }
            }
            for (Sheep sheep : sheepsUpdateList) {
                sheep.start();
                this.addSheep(sheep);
            }
            sheepsUpdateList.clear();
        }
    }
    public void addSheep(Sheep sheep) {
        sheeps.put(sheep.getName(), sheep);
    }
    int[] getPosition() {
        return new int[] {x, y};
    }
    void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    int setSnail(Sheep sheep) { //координаты центра 7,7
        int[] position = sheep.getPosition();
        int x = position[0];
        int y = position[1];
        if (x > 7) {
            if (y > 7) {
                return 1;
            }
            if (y == 7) {
                return 8;
            }
            return 7;
        }
        if (x < 7) {
            if (y > 7) {
                return 3;
            }
            if (y == 7) {
                return 4;
            }
            return 5;
        }
        if (x == 7) {
            if (y > 7) {
                return 2;
            }
            return 6;
        }
        return 9;
    }
    private boolean bark() {
        return true;
    }
}
