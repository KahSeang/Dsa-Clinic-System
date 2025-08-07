package util;

import adt.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileIO {
    public static List<String> readLines(String filename) throws IOException {
        List<String> lines = new List<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    public static void writeLines(String filename, List<String> lines) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < lines.size(); i++) {
            bw.write(lines.get(i));
            bw.newLine();
        }
        bw.close();
    }
} 