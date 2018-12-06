package org.rozdy.solver;

import org.rozdy.model.Board;
import org.rozdy.model.Island;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Solver {

    private static int iteration;

    private Solver() {}

    public static void solve(Board board) {
        board.init();
        if (board.isComplete()) {
            return;
        }

        long[] maxSeeds = board.getIslands().stream().mapToLong(Island::getMaxSeed).toArray();
        List<List<Long>> correctIterations = getCorrectIterations(board, maxSeeds);
        System.out.println("[" + new Date() + "] Precalculations completed");
        int[] iterations = new int[maxSeeds.length];
        while (!board.isComplete()) {
            long[] seeds = nextIteration(correctIterations, iterations);
            boolean generationSuccessful = generateBoard(board, seeds);
            if (generationSuccessful && board.isComplete()) {
                System.out.println("Done, ez.");
                System.out.println(board);
                return;
            }
            board.init();
        }
        throw new RuntimeException("Tried hard but still can't do this :(");
    }

    private static List<List<Long>> getCorrectIterations(Board board, long[] maxSeeds) {
        List<List<Long>> correctIterations = new ArrayList<>(maxSeeds.length);
        for (int i = 0; i < maxSeeds.length; i++) {
            correctIterations.add(new ArrayList<>());
        }
        long max = Arrays.stream(maxSeeds).max().orElse(0);
        for (long i = 0; i < max; i++) {
            if (i % (max / 100) == 0) {
                System.out.println("[" + new Date() + "] Precalculations " + i + "/" + max);
            }
            for (int j = 0; j < maxSeeds.length; j++) {
                board.init();
                if (maxSeeds[j] > i && board.getIslands().get(j).generateIsland(i)) {
                    correctIterations.get(j).add(i);
                }
            }
        }
        return correctIterations;
    }

    private static long[] nextIteration(List<List<Long>> correctIterations, int[] iterations) {
        long[] seeds = new long[iterations.length];
        for (int i = 0; i < iterations.length; i++) {
            seeds[i] = correctIterations.get(i).get(iterations[i]);
        }
        if (iteration++ % Integer.MAX_VALUE == 0) {
            System.out.print("[" + new Date() + "] Still working. Current seeds: ");
            for (int i = 0; i < iterations.length; i++) {
                System.out.print(iterations[i] + "/" + correctIterations.get(i).size() + " ");
            }
            System.out.println();
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

    private static boolean generateBoard(Board board, long[] seeds) {
        for (int j = 0; j < board.getIslands().size(); j++) {
            boolean successful = board.getIslands().get(j).generateIsland(seeds[j]);
            if (!successful) {
                return false;
            }
        }
        return true;
    }
}
