import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Barber extends Thread{

    private Random cutTimeGenerator = new Random();
    private boolean cutsFacial = false;

    Barber(boolean cutsFacial){
        this.cutsFacial = cutsFacial;
    }

    @Override
    public void run() {
        while(!Main.endOfSimulation){
            if(!Main.barberShop.barberShopQueue.isEmpty()) {
                Customer actual = Main.barberShop.barberShopQueue.peek();
                if(actual!=null && actual.getFacialCut() && this.cutsFacial && Main.barberShop.barberShopQueue.remove(actual)) {
                    Main.barberShop.sumWaitingTime += System.currentTimeMillis() - actual.getStartWaiting();
                    try {
                        this.hairCut();
                        this.facialCut();
                        Main.barberShop.customersServed++;
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                if(actual!=null && !actual.getFacialCut() && Main.barberShop.barberShopQueue.remove(actual)){
                    Main.barberShop.sumWaitingTime += System.currentTimeMillis() - actual.getStartWaiting();
                    try {
                        this.hairCut();
                        Main.barberShop.customersServed++;
                    } catch (InterruptedException e) {
                        return;
                    }
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
        }
    }
    void facialCut() throws InterruptedException{
        synchronized (Barber.class){
            System.out.println("Cutting facial...");
            int cutTime = cutTimeGenerator.nextInt(180)+20;
            TimeUnit.MILLISECONDS.sleep(cutTime);
            System.out.println("Facial cut done!");
        }
    }
}
