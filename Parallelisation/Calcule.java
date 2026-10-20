package org.example.progsystem;

public class Calcule extends Thread {

    private int valeur, multiplicateur, index;
    private long resultat;

    @Override
    public void run() {
        // Calcul intensif simulé par des opérations mathématiques
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }
    }

    public Calcule(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
        resultat = 0;
    }

    public long getResultat() {
        return resultat;
    }
}
