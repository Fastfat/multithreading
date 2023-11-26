class HoneyPot {
    private int honeyPortions; //текущее количество меда в горшке
    private int potSize; //размер горшка с медом

    HoneyPot(int potSize) {
        this.potSize = potSize;
        honeyPortions = 0;
    }
    boolean isFull() {
        return (honeyPortions == potSize);
    }
    void addPortions() {
        honeyPortions++;
        System.out.println("Мед" + honeyPortions);
    }
    void emptyPot() {
        honeyPortions = 0;
        System.out.println("Мед съел медведь");
    }
}
