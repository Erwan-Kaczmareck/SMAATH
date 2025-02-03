package com;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import comBase.ImportTable;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.File;


public class ImportCSV {
    public static void readCsvFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Erreur : Le fichier n'existe pas à l'emplacement donné !");
            return;
        }

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                .withSkipLines(1)
                .build()) {
            List<String[]> lignes = reader.readAll();

            for (String[] ligne : lignes) {
                if (ligne.length < 3) {
                    System.out.println("Ligne ignorée (données incomplètes) : " + String.join(",", ligne));
                    continue;
                }

                System.out.println("Nom: " + ligne[0] + ", Prénom: " + ligne[1] + ", Classe: " + ligne[2]);
            }
            ImportTable.importUtilisateur(lignes);
        } catch (IOException | CsvException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}
