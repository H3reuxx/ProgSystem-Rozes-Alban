package org.example.progsystem;

import java.util.ArrayList;

public class TestCompteur {

    public static void main(String[] args) {
        ArrayList<Integer> listeTests = new ArrayList<Integer>();
        for (int indice = 0; indice < 20; indice++) {
            CompteurDangereux.resetCompteur();
            CompteurDangereux.executable();
            System.out.println(CompteurDangereux.getCompteur()); // pas le bon nombre (attendu 10 * 111111)
            listeTests.add(CompteurDangereux.getCompteur());
        }

        int valeurMax = 0;
        for (int num : listeTests) {
            if (num > valeurMax) {
                valeurMax = num;
            }
        }

        System.out.println("Voici la valeur max obtenue parmis les incréments : " + valeurMax);
    }
}
