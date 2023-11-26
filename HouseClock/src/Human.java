import java.util.Random;

public class Human extends Thread {
    private int time;
    private House house;
    Human(String nane, House house) {
        super(nane);
        this.house = house;
    }

    public void run() {
        synchronized (house) {
            while (true) {
                System.out.println(this.getName() + " is start to work");
                time = timeGeneration(17, 21) * 1000;
                System.out.println(this.getName() + " is working");
                house.setAlarmTime(this);
                System.out.println(this.getName() + " is finish to work");
                time = timeGeneration(6, 10) * 1000;
                System.out.println(this.getName() + " is sleeping");
                house.setAlarmTime(this);
                System.out.println(this.getName() + " is woke up");
            }
        }
    }
    int getTime() {
        return time;
    }
    private int timeGeneration(int min, int max) { //метод генерации числа в заданном диапазоне
        Random rn = new Random();
        return rn.nextInt(max - min + 1) + min;
    }
}
