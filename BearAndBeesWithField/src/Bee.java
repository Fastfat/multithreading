import java.util.Random;
class Bee extends Thread {
    HoneyPot pot;
    FlowerField flowerField;
    Random random = new Random();
    int x,y;

    Bee(String name, HoneyPot pot, FlowerField flowerField) {
        super(name);
        this.pot = pot;
        this.flowerField = flowerField;
    }

    public void run() {
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        while (true) {
            synchronized (flowerField) {
                do {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                    try {
                        Thread.sleep(50); //Время полета за медом
                    } catch (InterruptedException ex) {
                        System.out.println("Exception in class \"Bee\"");
                        ex.printStackTrace();
                    }
                } while (!flowerField.setFlowerField(x, y));
            }
            synchronized (pot) {
                if(!pot.isFull()) {
                    pot.addPortions();
                    System.out.printf("%s добавила \n", Thread.currentThread().getName());
                    if (pot.isFull()) //Пчела не впадает в ожидание. Если горшок полон, то она просто улетает
                        pot.notifyAll(); //Разбудила медведя
                    /* В java нет возможности разбудить конкретный тред. Пчела не может
                    разбудить только одного медведя, поэтому она будит всех. */
                }
            }
        }
    }
}
