public class FlowerField {
    private int[][] flowerField;

    FlowerField() {
        flowerField = new int[10][10]; //задается размер цветочного поля
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                flowerField[i][j] = 1; //неопыленный цветок
            }
        }
    }

    int[][] getFlowerField() {
        return flowerField;
    }
    boolean setFlowerField(int x, int y) {
        if (flowerField[x][y] == 1) {
            flowerField[x][y] = 0; //цветок опыляется
            return true;
        } else {
            return false;
        }
    }
}
