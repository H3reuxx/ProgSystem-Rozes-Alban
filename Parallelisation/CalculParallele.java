
public class CalculParallele {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static long res;


    static Calcule[] threads = new Calcule[DONNEES.length];

    public static void main(String[] args) {

        long debut = System.nanoTime();

        for (int index=0; index< threads.length; index++) {
            threads[index] = new Calcule(DONNEES[index], 1000, index);
            threads[index].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
                res += threads[i].getResultat();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Durée : " + duree + " ms");

        System.out.println(res);
    }
}
