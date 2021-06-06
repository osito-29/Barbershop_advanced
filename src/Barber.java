import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Barber extends Thread{

    private Random cutTimeGenerator = new Random();

    @Override
    public void run() {
        while(!Main.endOfSimulation){
            if(!Main.barberShop.barberShopQueue.isEmpty()) {
                Customer actual = Main.barberShop.barberShopQueue.poll();
                Main.barberShop.sumWaitingTime += System.currentTimeMillis() - actual.getStartWaiting();
                try {
                    this.hairCut();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    void hairCut() throws InterruptedException {
        synchronized (Barber.class) {
            System.out.println("Cutting hair...");
            int cutTime = cutTimeGenerator.nextInt(180)+20;
            TimeUnit.MILLISECONDS.sleep(cutTime);
            System.out.println("Haircut done!");
            Main.barberShop.customersServed++;
        }
    }
}
