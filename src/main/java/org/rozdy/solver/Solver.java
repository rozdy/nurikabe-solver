package org.rozdy.solver;

import org.rozdy.model.Board;
import org.rozdy.model.Island;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

    private Board board;
    private int iteration;

    public Solver(Board board) {
        this.board = board;
    }

    public List<List<Long>> precalculate() {
        board.init();
        List<List<Long>> correctIterations = getCorrectIterations();
        log("Precalculations completed");
        return correctIterations;
    }

    public Board solve(List<List<Long>> correctIterations) {
        board.init();
        if (board.isComplete()) {
            return board;
        }

        int[] iterations = new int[board.getIslands().size()];
        while (!board.isComplete()) {
            long[] seeds = nextIteration(correctIterations, iterations);
            boolean generationSuccessful = generateBoard(seeds);
            if (generationSuccessful && board.isComplete()) {
                return board;
            }
            board.init();
        }
        throw new RuntimeException("Tried hard but still can't do this :(");
    }

    private List<List<Long>> getCorrectIterations() {
        long[] maxSeeds = board.getIslands().stream().mapToLong(Island::getMaxSeed).toArray();
        List<List<Long>> correctIterations = new ArrayList<>(maxSeeds.length);
        List<Set<String>> correctIslands = new ArrayList<>(maxSeeds.length);
        for (int i = 0; i < maxSeeds.length; i++) {
            correctIterations.add(new ArrayList<>());
            correctIslands.add(new HashSet<>());
        }
        long max = Arrays.stream(maxSeeds).max().orElse(0);
        for (long i = 0; i < max; i++) {
            if (max / 100 != 0 && i % (max / 100) == 0) {
                log("Precalculations " + i + "/" + max);
            }
            for (int j = 0; j < maxSeeds.length; j++) {
                board.init();
                Island island = board.getIslands().get(j);
                if (maxSeeds[j] > i && island.generateIsland(i) && correctIslands.get(j).add(island.toString())) {
                    correctIterations.get(j).add(i);
                }
            }
        }
        return correctIterations;
    }

    private long[] nextIteration(List<List<Long>> correctIterations, int[] iterations) {
        long[] seeds = new long[iterations.length];
        for (int i = 0; i < iterations.length; i++) {
            seeds[i] = correctIterations.get(i).get(iterations[i]);
        }
        if (iteration++ % Integer.MAX_VALUE == 0) {
            StringBuilder sb = new StringBuilder("Still working. Current seeds: ");
            for (int i = 0; i < iterations.length; i++) {
                sb.append(iterations[i]).append("/").append(correctIterations.get(i).size()).append(" ");
            }
            log(sb.toString());
        }
        for (int i = 0; i < iterations.length; i++) {
            if (iterations[i] < correctIterations.get(i).size() - 1) {
                iterations[i]++;
                for (int j = 0; j < i; j++) {
                    iterations[j] = 0;
                }
                break;
            }
        }
        return seeds;
    }

    private boolean generateBoard(long[] seeds) {
        for (int j = 0; j < board.getIslands().size(); j++) {
            boolean successful = board.getIslands().get(j).generateIsland(seeds[j]);
            if (!successful) {
                return false;
            }
        }
        return true;
    }

    private static void log(String message) {
        System.out.printf("[%s] %s%n", new Date(), message);
    }
}
