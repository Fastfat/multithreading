import java.util.*;

public class Courier extends Thread {
    private String[][] deliveryArr = new String[1][1];
    private String status = "absent";
    private Company company;
    private Date currentTime;

    Courier(String name, Company company) {
        super(name);
        this.company = company;
    }

    public void run() {
        currentTime = company.getCurrentTime();
        doubleSort(company.getDeliveryArr()); //вызов метода сортировки
            for (int i = 0; i < deliveryArr.length; i++) {
                if (deliveryArr[i][1].equals("absent")) {
                    Date deliveryEndDate = new Date(currentTime.getTime());
                    deliveryEndDate.setHours(Integer.parseInt(deliveryArr[i][3].split("\\.")[2]));
                    deliveryEndDate.setMinutes(Integer.parseInt(deliveryArr[i][3].split("\\.")[3]));
                    currentTime = company.getCurrentTime();
                    if (currentTime.getTime() + Integer.parseInt(deliveryArr[i][4]) * 1000 <= deliveryEndDate
                            .getTime()) {
                        if (company.markDelivery(this, deliveryArr[i])) {
                            setStatus("inProgress");
                            try {
                                Thread.sleep(Integer.parseInt(deliveryArr[i][4]) * 1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            Date deliveryStartDate = new Date(currentTime.getTime());
                            deliveryStartDate.setHours(Integer.parseInt(deliveryArr[i][3].split("\\.")[0]));
                            deliveryStartDate.setMinutes(Integer.parseInt(deliveryArr[i][3].split("\\.")[1]));
                            if (currentTime.getTime() < deliveryStartDate.getTime()) {
                                company.markDelivery(this, deliveryArr[i]);
                                try {
                                    Thread.sleep((deliveryStartDate.getTime() - currentTime.getTime()) / 60);
                                }
                                catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            setStatus("finish");
                            company.markDelivery(this, deliveryArr[i]);
                            try {
                                Thread.sleep(Integer.parseInt(deliveryArr[i][4]) * 1000); //время пути обратно
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            setStatus("absent");
                            for (int ii = 0; ii < deliveryArr.length; ii++) {
                                for (int jj = 0; jj < deliveryArr[ii].length; jj++) {
                                    System.out.print(deliveryArr[ii][jj] + " ");
                                }
                                System.out.println();
                            }
                        }
                    }
                }
                doubleSort(company.getDeliveryArr());
            }
        System.out.println(this.getName() + " no longer sees free deliveries");
            setStatus("finish");
    }
    public String getStatus() {
        return status;
    }
    private void setStatus(String status) {
        this.status = status;
    }
    private void arrUpdate(String[][] src) {
        deliveryArr = new String[src.length-1][5];
        for (int ii = 1, jj = 0; ii < src.length; ii++, jj++) {
            deliveryArr[jj] = src[ii];
        }
    }
    private void doubleSort(String[][] sortArr) {
        arrUpdate(sortArr);
        Arrays.sort(deliveryArr, Comparator.comparingInt(arr -> Integer.parseInt(arr[4])));
        Arrays.sort(deliveryArr, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                Date timeBefore = new Date(currentTime.getTime());
                Date timeBefore2 = new Date(currentTime.getTime());
                timeBefore.setHours(Integer.parseInt(o1[3].split("\\.")[0]));
                timeBefore.setMinutes(Integer.parseInt(o1[3].split("\\.")[1]));
                timeBefore2.setHours(Integer.parseInt(o2[3].split("\\.")[0]));
                timeBefore2.setMinutes(Integer.parseInt(o2[3].split("\\.")[1]));
                return Integer.compare((int) timeBefore.getTime(), (int) timeBefore2.getTime());
            }
        });
    }
}
