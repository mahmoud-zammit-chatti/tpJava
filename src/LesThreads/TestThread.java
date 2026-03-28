package LesThreads;

public class TestThread {

    public static void main() {
        System.out.println("debut programme principal");
        Traitemnt ta=new Traitemnt("A");
        Traitemnt tb=new Traitemnt("B");

        ta.start();
        try {
            ta.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        tb.start();
        System.out.println("fin programme principal");

    }

}
