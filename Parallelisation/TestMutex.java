package org.example.progsystem;

import java.util.ArrayList;

public class TestMutex {
    public static void main(String[] args) {

        for (int indice = 0; indice < 20; indice++) {
            CompteurSecurise.resetCompteur();
            CompteurSecurise.executable();
            System.out.println(CompteurSecurise.getCompteur()); // pas le bon nombre (attendu 10 * 111111)
        }
    }
}
