import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Company {
    private File deliveryFile;
    private List<Courier> courierList = new ArrayList<>();
    private String[][] deliveryArr;
    private Date currentTime = null; //системное время
    private Clock clock;

    Company(File deliveryFile) {
        this.deliveryFile = deliveryFile;
        readFileContents();
    }

    File getDeliveryFile() {
        return deliveryFile;
    }
    void addCourier() {
        Courier courier;
        if (!courierList.isEmpty()) {
            courier = new Courier("Courier" + courierList.size() + 1, this);
        } else {
            clock = new Clock(this);
            clock.start();
            courier = new Courier("Courier1", this);
        }
        courierList.add(courier);
        courier.start();

    }
    void addFiveCouriers() {
        Courier courier;
        if (!courierList.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                courier = new Courier("Courier" + courierList.size() + 1, this);
                courierList.add(courier);
                courier.start();
            }
        } else {
            clock = new Clock(this);
            clock.start();
            for (int i = 1; i < 6; i++) {
                courier = new Courier("Courier" + i, this);
                courierList.add(courier);
                courier.start();
            }
        }
    }
    synchronized Date getCurrentTime() {
        while (currentTime == null) {
            try {
                wait();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return currentTime;
    }
    synchronized boolean markDelivery(Courier courier, String[] delivery) {
        switch (courier.getStatus()) {
            case ("absent"):
                for (int i = 0; i < deliveryArr.length; i++) {
                    if (deliveryArr[i][2].equals(delivery[2]) && deliveryArr[i][3].equals(delivery[3])) {
                        if (deliveryArr[i][1].equals("absent")) {
                            System.out.println(courier.getName() + " took delivery to " + deliveryArr[i][2]);
                            deliveryArr[i][0] = courier.getName().substring(courier.getName().length()-1);
                            deliveryArr[i][1] = "inProgress";
                            return true;
                        }
                        return false;
                    }
                }
                return false;
            case ("inProgress"):
                System.out.println(courier.getName() + " is waiting for the addressee");
                return true;
            case ("finish"):
                for (int i = 0; i < deliveryArr.length; i++) {
                    if (deliveryArr[i][2].equals(delivery[2]) && deliveryArr[i][3].equals(delivery[3])) {
                        System.out.println(courier.getName() + " brought delivery to " + deliveryArr[i][2]);
                        deliveryArr[i][1] = "finish";
                        return true;
                    }
                }
        }
        return false;
    }
    private void readFileContents()
    {
        String row;
        int count = 0;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(deliveryFile));
            while ((csvReader.readLine()) != null) {
                count++;
            }
            deliveryArr = new String[count][5];
            csvReader = new BufferedReader(new FileReader(deliveryFile));
            int i = 0;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                for (int j = 0; j < 5; j++) {
                    deliveryArr[i][j] = data[j];
                }
                i++;
            }
            csvReader.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    synchronized String[][] getDeliveryArr() {
        while (deliveryArr[0] == null) {
            try {
                wait();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return deliveryArr;
    }
    synchronized void setTime(Date currentTime) {
        this.currentTime = currentTime;
        System.out.println("hh.mm.ss " + currentTime);
        notifyAll();
    }
}