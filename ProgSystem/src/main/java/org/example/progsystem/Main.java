package org.example.progsystem;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String enTete = "P3\n100 100\n255\n";

        try {
            FileWriter fw = new FileWriter("firstPPM_withFileWriter.ppm");
            fw.write(enTete); // écriture du texte directement
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}