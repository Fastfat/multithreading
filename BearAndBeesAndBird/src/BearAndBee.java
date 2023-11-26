import java.util.Set;

public class BearAndBee {
    public static void main(String[] args) {
        int potSize = 20;
        int countBees = 5;
        int[][] flowers;
        HoneyPot pot = new HoneyPot(potSize);
        Bear bear = new Bear("Bear", pot);
        Bird bird = new Bird();
        FlowerField flowerField = new FlowerField();
        System.out.println("Main thread is started");
        for (int i = 1; i <= countBees; i++) {
            Bee bee = new Bee("Bee " + i, pot, flowerField, bird);
            bee.start();
        }
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        bear.start();

        while (bear.isAlive()) {
            for (Thread thread : threads) {
                if (thread.getName().contains("Bee")) {
                    if (!thread.isAlive()) {
                        Bee bee = new Bee("New" + thread.getName(), pot, flowerField, bird);
                        System.out.println("Новая пчела вылетела из улья");
                        bee.start();
                        threads = Thread.getAllStackTraces().keySet();
                        try {
                            bee.sleep(100);
                        }
                        catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            bear.join();
        }
        catch (InterruptedException ex) {
            System.out.printf("%s has been interrupted\n", bear.getName());
            ex.printStackTrace();
        }
        flowers = flowerField.getFlowerField();
        int k = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println();
            for (int j = 0; j < 10; j++) {
                System.out.print(flowers[i][j]);
                k += flowers[i][j];
            }
        }
        threads = Thread.getAllStackTraces().keySet();
        for (Thread thread : threads) {
            if (thread.getName().contains("Bee")) {
                thread.stop(); //остаоновка потоков Пчела
            }
        }
        System.out.println();
        System.out.println(k); //вывод оставшегося количества цветов
        System.out.println("Main thread is finished");
    }
}
