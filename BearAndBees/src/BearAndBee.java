public class BearAndBee {
    public static void main(String[] args) {
        int potSize = 20;
        int countBees = 5;
        HoneyPot pot = new HoneyPot(potSize);
        Bear bear = new Bear("Bear", pot);
        System.out.println("Main thread is started");
        for (int i = 1; i <= countBees; i++) {
            Bee bee = new Bee("Bee " + i, pot);
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
        System.out.println("Main thread is finished");
    }
}
