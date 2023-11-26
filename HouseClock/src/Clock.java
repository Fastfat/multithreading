public class Clock extends Thread {
    House house;
    private final int maxClock = 24000;
    private int currentTime = 0;

    Clock(House house) {
        this.house = house;
    }

    public void run() {
        while (true) {
            while (currentTime <= maxClock) {
                house.setClockTime(currentTime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                currentTime += 1000;
            }
            currentTime = 0;
        }
    }
}
