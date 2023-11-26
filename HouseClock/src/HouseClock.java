public class HouseClock {
    public static void main(String[] args) {
        House house = new House();
        Clock clock = new Clock(house);
        Human human = new Human("Human1", house);
        clock.start();
        human.start();
        human = new Human("Human2", house);
        human.start();
        human = new Human("Human3", house);
        human.start();
        human = new Human("Human4", house);
        human.start();
        human = new Human("Human5", house);
        human.start();
    }
}