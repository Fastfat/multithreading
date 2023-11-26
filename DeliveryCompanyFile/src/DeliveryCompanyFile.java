import java.io.File;

public class DeliveryCompanyFile {
    public static void main(String[] args) {
        Company company = new Company(new File("resources/deliveryFile.csv"));
        //company.addCourier();
        company.addFiveCouriers();
    }
}