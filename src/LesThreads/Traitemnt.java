package LesThreads;

public class Traitemnt extends Thread {
    private String nom;

    public Traitemnt(String nom) {
        this.nom = nom;
    }


    public void run() {
        int i = 0;
        while(i<10){
            System.out.println("Traitement : "+i+" de thread :"+nom);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
