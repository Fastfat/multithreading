class Bee extends Thread{
    HoneyPot pot;
    Bee(String name, HoneyPot pot) {
        super(name);
        this.pot = pot;
    }

    public void run() {
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        while (true) {
            try {
                Thread.sleep(50); //Время полета за медом
            } catch (InterruptedException ex) {
                System.out.println("Exception in class \"Bee\"");
                ex.printStackTrace();
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
