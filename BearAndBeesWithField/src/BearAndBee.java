public class BearAndBee {
    public static void main(String[] args) {
        int potSize = 20;
        int countBees = 5;
        int[][] flowers;
        HoneyPot pot = new HoneyPot(potSize);
        Bear bear = new Bear("Bear", pot);
        FlowerField flowerField = new FlowerField();
        System.out.println("Main thread is started");
        for (int i = 1; i <= countBees; i++) {
            Bee bee = new Bee("Bee " + i, pot, flowerField);
            bee.start();
        }
        bear.start();
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
        System.out.println();
        System.out.println(k);
        System.out.println("Main thread is finished");
    }
}
