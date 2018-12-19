package org.rozdy.solver;

import static java.lang.String.format;

import org.rozdy.model.Board;
import org.rozdy.model.Island;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Solver {

    private static final Logger LOG = Logger.getLogger(Solver.class.getName());

    private Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public List<List<Long>> precalculate() {
        board.clear();
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
                LOG.info(format("Precalculations in progress %d/%d %d %c", i, max, max / 100, '%'));
            }
            for (int j = 0; j < maxSeeds.length; j++) {
                board.clear();
                Island island = board.getIslands().get(j);
                if (maxSeeds[j] > i && island.generateIsland(i) && correctIslands.get(j).add(island.toString())) {
                    correctIterations.get(j).add(i);
                }
            }
        }
        return correctIterations;
    }

    public List<List<Long>> recheckIterations(List<List<Long>> correctIterations) {
        for (int i = 0; i < correctIterations.size(); i++) {
            List<Long> incorrectIterations = new ArrayList<>();
            for (Long seed: correctIterations.get(i)) {
                board.clear();
                Island island = board.getIslands().get(i);
                island.generateIsland(seed);
                if (!island.checkIslandConnections() || !board.isAllRiversConnected()) {
                    incorrectIterations.add(seed);
                }
            }
            correctIterations.get(i).removeAll(incorrectIterations);
            LOG.info(format("Island %d rechecked, %d iterations removed", i, incorrectIterations.size()));
        }
        return correctIterations;
    }

    public Board solve(List<List<Long>> correctIterations) {
        board.clear();
        if (board.isComplete()) {
            return board;
        }

        IterationBuilder iterationBuilder = new IterationBuilder(correctIterations);
        int generationState = 0;
        while (!board.isComplete()) {
            long[] seeds = iterationBuilder.nextIteration(generationState);
            generationState = generateBoard(seeds);
            if (generationState == 0 && board.isComplete()) {
                return board;
            }
            board.clear();
        }
        throw new RuntimeException("Tried hard but still can't do this :(");
    }

    private int generateBoard(long[] seeds) {
        for (int j = 0; j < board.getIslands().size(); j++) {
            boolean successful = board.getIslands().get(j).generateIsland(seeds[j]);
            if (!successful) {
                return j + 1;
            }
        }
        return 0;
    }
}
