class Bear extends Thread{
    HoneyPot pot;
    Bear(String name, HoneyPot pot) {
        super(name);
        this.pot = pot;
    }

    public synchronized void run() {
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        while (true) {
            synchronized (pot) {
                while (!pot.isFull()) {
                    try {
                        pot.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Exception in class \"Bear\"");
                        ex.printStackTrace();
                    }
                }
                System.out.println("Медведь проснулся");
                pot.emptyPot(); //Съел мед
                pot.notifyAll(); //Разбудил пчел
            }
        }
    }
}
