import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BarberShop {

    public BlockingQueue<Customer> barberShopQueue = new ArrayBlockingQueue<>(5);
    public static ArrayList<Integer> customersServedPerDay = new ArrayList<>();
    public static int customersServed = 0;
    public static int customersNotServedClosedShop = 0;
    public static int customersNotServedFullQueue = 0;
    public static long sumWaitingTime = 0;
}
