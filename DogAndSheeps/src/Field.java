import java.util.HashMap;
import java.util.Map;

public class Field {
    private int[][] field;
    private int[][] fieldCopy;
    Map<String, Sheep> sheeps = new HashMap<>();
    Map<String,Dog> dogs = new HashMap<>();

    Field(int x, int y) {
        field = new int[x+6][y+6];
        fieldCopy = new int[x+6][y+6];
        for (int i = 0; i < x + 6; i++) {
            for (int j = 0; j < y + 6; j++) {
                if ((i < 3 || i >= x + 3) || (j < 3 || j >= y + 3)) {
                    field[i][j] = 1;
                    fieldCopy[i][j] = 1;
                } else {
                    field[i][j] = 0;
                    fieldCopy[i][j] = 0;
                }
            }
        }
    }

    boolean getFieldPosition(int x, int y) {
        return fieldCopy[y][x] == 0;
    }
    boolean getPictureFieldPosition(Dog dog, Sheep sheep) {
        dogs.put(dog.getName(), dog);
        int[] dogPosition = dog.getPosition();
        int[] sheepPosition = sheep.getPosition();
        field[sheepPosition[1]][sheepPosition[0]] = 4;
        setLastPosition(dogPosition[0], dogPosition[1]);
        dog.setPosition(sheepPosition[0], sheepPosition[1]);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");
        return true;
    }
    boolean getPictureFieldPosition(Sheep sheep){
        sheeps.put(sheep.getName(), sheep);
        if(!isMoveAvailable(sheep))
            return false;
        int[] position = sheep.getPosition();
        setLastPosition(position[0], position[1]);
        switch (position[2]) {
            case 1:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1] - 1][position[0] - 1] = 4;
                } else {
                    field[position[1] - 1][position[0] - 1] = 8;
                }
                sheep.setPosition(position[0]-1, position[1]-1);
                break;
            case 2:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1] - 1][position[0]] = 4;
                } else {
                    field[position[1] - 1][position[0]] = 8;
                }
                sheep.setPosition(position[0], position[1]-1);
                break;
            case 3:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1] - 1][position[0] + 1] = 4;
                } else {
                    field[position[1] - 1][position[0] + 1] = 8;
                }
                sheep.setPosition(position[0]+1, position[1]-1);
                break;
            case 4:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1]][position[0] + 1] = 4;
                } else {
                    field[position[1]][position[0] + 1] = 8;
                }
                sheep.setPosition(position[0]+1, position[1]);
                break;
            case 5:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1] + 1][position[0] + 1] = 4;
                } else {
                    field[position[1] + 1][position[0] + 1] = 8;
                }
                sheep.setPosition(position[0]+1, position[1]+1);
                break;
            case 6:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1] + 1][position[0]] = 4;
                } else {
                    field[position[1] + 1][position[0]] = 8;
                }
                sheep.setPosition(position[0], position[1]+1);
                break;
            case 7:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1] + 1][position[0] - 1] = 4;
                } else {
                    field[position[1] + 1][position[0] - 1] = 8;
                }
                sheep.setPosition(position[0]-1, position[1]+1);
                break;
            case 8:
                if (sheep.isAfraid() && !getFieldPosition(position[0], position[1])) {
                    field[position[1]][position[0] - 1] = 4;
                } else {
                    field[position[1]][position[0] - 1] = 8;
                }
                sheep.setPosition(position[0]-1, position[1]);
                break;
            case 9:
                field[position[1]][position[0]] = 8;
                sheep.setPosition(position[0], position[1]);
                break;
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                    System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");
        return true;
    }
    Map<String, Sheep> getSheeps() {
        return sheeps;
    }
    private boolean isMoveAvailable(Sheep sheep) {
        int[] location = sheep.getPosition();
        if (location[2] == 9)
            return true;
        for (Sheep i : sheeps.values()) {
            int[] location2 = i.getPosition();
            if (((location2[0] - location[0] >= -1) && (location2[0] - location[0] <= 1))
                    && ((location2[1] - location[1] >= -1) && (location2[1] - location[1] <= 1))) {
                int[] snail = translateSnail(location[2]);
                if ((location[0] + snail[0] == location2[0]) && (location[1] + snail[1] == location2[1])) {
                    return false;
                    //координаты плюс улитка
                }
            }
        }
        return true;
    }
    private int[] translateSnail(int s) {
        switch (s) {
            case 1:
                return new int[] {-1, -1};
            case 2:
                return new int[] {0, -1};
            case 3:
                return new int[] {+1, -1};
            case 4:
                return new int[] {+1, 0};
            case 5:
                return new int[] {+1, +1};
            case 6:
                return new int[] {0, +1};
            case 7:
                return new int[] {-1, +1};
            case 8:
                return new int[] {-1, 0};
            case 9:
                return new int[] {0, 0};
        }
        return new int[2];
    }
    private void setLastPosition(int x, int y) {
        if (getFieldPosition(x, y)) {
            field[y][x] = 0;
        }
        else {
            field[y][x] = 1;
        }
    }
}
