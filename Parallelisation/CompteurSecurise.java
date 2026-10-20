package org.example.progsystem;

public class CompteurSecurise {
    private static int compteurGlobal = 0;  // Variable partagée MODIFIABLE

    private static final Object verrou = new Object();

    static class Incrementeur implements Runnable {
        private final String nom;
        private final int nombreIncrements;

        public Incrementeur(String nom, int nombreIncrements) {
            this.nom = nom;
            this.nombreIncrements = nombreIncrements;
        }

        @Override
        public void run() {
            for (int i = 0; i < nombreIncrements; i++) {
                /* C'est ce qu'on appelle une race condition ;
                 * Les erreurs de cohérence mémoire surviennent lorsque différents threads ont des vues incohérentes
                 * de données qui devraient être identiques.
                 */
                synchronized(verrou) {
                    // TODO: Placer ici l'opération critique
                    compteurGlobal++;
                }
            }
        }
    }

    public static void executable() {

        Thread[] threads = new Thread[9];
        for (int index=0; index < 9; index++) {
            threads[index] = new Thread(new Incrementeur("chais pas", 1111111));
            threads[index].start();
        }
        for (int index=0; index < 9; index++) {
            try {
                threads[index].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int getCompteur() {
        synchronized(verrou) {
            return compteurGlobal;
        }
    }

    public static void resetCompteur() {
        synchronized(verrou) {
            compteurGlobal = 0;;
        }
    }
}
