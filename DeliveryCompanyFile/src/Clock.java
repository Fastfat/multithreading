import java.util.Date;

public class Clock extends Thread {
    Date currentTime;
    Company company;

    Clock(Company company) {
        currentTime = new Date();
        this.company = company;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            currentTime.setTime(currentTime.getTime() + 60000);
            company.setTime(currentTime);
        }
    }
}
