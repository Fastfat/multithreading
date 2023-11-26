import java.util.HashMap;

public class DogAndSheeps {
    public static void main(String[] args) throws InterruptedException {
        Field field = new Field(10, 10);
        HashMap<String, Sheep> sheepMap = new HashMap<>();
        Sheep sheep = new Sheep("Sheep1", field, 5, 5);
        sheep.start();
        sheepMap.put(sheep.getName(), sheep);
        sheep = new Sheep("Sheep2", field, 6, 6);
        sheep.start();
        sheepMap.put(sheep.getName(), sheep);
        Dog dog = new Dog("Dog1", sheepMap, field);
        dog.start();
        dog.join();
    }
}