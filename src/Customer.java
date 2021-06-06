import java.util.Random;

public class Customer{

    private Random random = new Random();
    private long startWaiting;
    private boolean facialCut = false;

    Customer(long startWaiting){
        this.startWaiting = startWaiting;
        if(random.nextInt(2)==0){
            this.facialCut = true;
        }

    }

    public long getStartWaiting() {
        return startWaiting;
    }

    public boolean getFacialCut() {
        return this.facialCut;
    }
}
