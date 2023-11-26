import java.util.Random;

public class Sheep extends Thread {
    private int x;
    private int y;
    private int snail;
    private boolean frightened;
    Field field;

    Sheep(String name, Field field, int x, int y) {
        super(name);
        this.x = x;
        this.y = y;
        snail = 9;
        frightened = false;
        this.field = field;
    }

    public void run() {
        try {
            while (true) {
                while (!isAfraid()) {
                    synchronized (field) {
                        while (!grazeOnField()) {
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                while (isAfraid()) {
                    synchronized (field) {
                        afraidOnField();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                for (int i = 0; i < 2; i++) {
                    synchronized (field) {
                        afraidOnField();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("\nОвца сбежала\n");
            System.out.println("Овец было всего: " + field.getSheeps().size());
            System.exit(0);
        }
    }

    void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int[] getPosition() {
        return new int[] {x, y, snail};
    }

    boolean grazeOnField() {
        changeDirection();
        return field.getPictureFieldPosition(this);
    }
    boolean afraidOnField() {
        return field.getPictureFieldPosition(this);
    }
    void getAfraid(boolean noice) {
        if (frightened) {
            frightened = false;
        } else {
            frightened = noice;
        }
    }
    boolean isAfraid() {
        return frightened;
    }
    void changeDirection(int snail) {
        this.snail = snail;
    }

    private void changeDirection() {
        Random rn = new Random();
        int maximum = 9;
        int minimum = 1;
        snail = rn.nextInt(maximum - minimum + 1) + minimum;
    }
}
