import java.util.HashMap;

public class House {
    private int currentTime = 0;
    private HashMap<Human, Boolean> alarm = new HashMap<>();

    synchronized void setAlarmTime(Human human) {
        alarm.put(human, human.getTime() == currentTime);
        while (currentTime != human.getTime() || !alarm.get(human)) {
            if (currentTime - human.getTime() >= 0 && currentTime - human.getTime() <= 2) {
                alarm.put(human, true);
                continue; //выходим из ожидания
            }
            System.out.println(human.getTime());
            try {
                wait();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("The alarm clock went off on " + human.getName());
        alarm.put(human, false);
    }
    synchronized void setClockTime(int currentTime) { //метод для работы часов от класса Clock
        this.currentTime = currentTime;
        System.out.println("dd " + this.currentTime);
        notifyAll();
    }
}
