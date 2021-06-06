import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static Random random = new Random();
    public static BarberShop barberShop = new BarberShop();

    public static volatile boolean shopIsClosed = true;
    public static volatile boolean endOfSimulation = false;
    public static int ONEHOUR = 400;
    public static int CUSTOMERRATE = 180;
    public static int NUMBEROFDAYS = 5;

    public static void main(String[] args) throws InterruptedException {

        Thread dayNNight = new Thread(()->
        {
            for (int i =0; i<NUMBEROFDAYS; i++){
                try {
                    barberShop.customersServed = 0;
                    System.out.println("Start of the " + (i+1) +". day");
                    shopIsClosed = true;
                    TimeUnit.MILLISECONDS.sleep(9*ONEHOUR);
                    System.out.println("Barber shop opens");
                    shopIsClosed = false;
                    TimeUnit.MILLISECONDS.sleep(8*ONEHOUR);
                    System.out.println("Barber shop closes");
                    shopIsClosed = true;
                    TimeUnit.MILLISECONDS.sleep(7*ONEHOUR);
                    System.out.println("End of the " + (i+1) +". day");
                    barberShop.customersServedPerDay.add(barberShop.customersServed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            endOfSimulation = true;
            barberShop.customersServed = 0;
        });


        //barber starts
        Barber barber = new Barber();

        //customer flow starts
        Thread customersThread = new Thread(()->{
            while(!endOfSimulation){
                try {
                    Thread.sleep(random.nextInt(CUSTOMERRATE));
                } catch (InterruptedException e) {
                    return;
                }
                if(!shopIsClosed){
                        if(barberShop.barberShopQueue.remainingCapacity()!=0){
                            barberShop.barberShopQueue.add(new Customer(System.currentTimeMillis()));
                            System.out.println("I am the customer, I sit down");
                        }else{
                            System.out.println("I am the customer, queue is full");
                            barberShop.customersNotServedFullQueue++;
                        }
                }else{
                    System.out.println("I am the customer, shop is closed, I leave");
                    barberShop.customersNotServedClosedShop++;
                }
            }
        });

        //threads start
        barber.start();
        customersThread.start();
        dayNNight.start();

        dayNNight.join();
        customersThread.interrupt();
        barber.interrupt();


        for (int perDay: barberShop.customersServedPerDay){
            barberShop.customersServed += perDay;
        }

        System.out.println();
        System.out.println("Results of the simulation");
        System.out.println("1. Customers served: " + barberShop.customersServed);
        System.out.println("2. Customers unserved (shop was closed): " + barberShop.customersNotServedClosedShop);
        System.out.println("2. Customers unserved (queue was full): " + barberShop.customersNotServedFullQueue);
        System.out.println("3. Customers served per day: ");
        for (int i=0; i<NUMBEROFDAYS; ++i){
            System.out.println("\t" + (i+1) + ". day: " + barberShop.customersServedPerDay.get(i));
        }
        System.out.println("4. Average waiting time for service: " + barberShop.sumWaitingTime/barberShop.customersServed);
    }
}
