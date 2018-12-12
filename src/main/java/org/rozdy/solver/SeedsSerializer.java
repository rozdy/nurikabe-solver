package org.rozdy.solver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SeedsSerializer {

    private SeedsSerializer() {}

    public static void write(List<List<Long>> correctIterations, String filename) {
        try (Writer wr = new FileWriter(filename)) {
            wr.write("Island count: ");
            wr.write(Integer.toString(correctIterations.size()));
            wr.write(System.lineSeparator());
            wr.write("Seeds counts: ");
            for (List<Long> iterations : correctIterations) {
                wr.write(Integer.toString(correctIterations.size()));
                wr.write(" ");
            }
            wr.write(System.lineSeparator());
            wr.write("Seeds:");
            wr.write(System.lineSeparator());
            for (List<Long> iterations : correctIterations) {
                for (Long iteration : iterations) {
                    wr.write(iteration.toString());
                    wr.write(" ");
                }
                wr.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Uh.");
        }
    }

    public static List<List<Long>> read(String filename) {
        List<List<Long>> correctIterations = new ArrayList<>();
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            while (!"Seeds:".equals(scanner.nextLine()));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<Long> iterations = Arrays.stream(line.split(" "))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                correctIterations.add(iterations);
            }
            return correctIterations;
        } catch (IOException e) {
            System.err.println("Uh.");
            return Collections.emptyList();
        }
    }
}
