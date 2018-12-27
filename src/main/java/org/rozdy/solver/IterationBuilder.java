package org.rozdy.solver;

import java.util.List;
import java.util.logging.Logger;

public class IterationBuilder {

    private static final Logger LOG = Logger.getLogger(IterationBuilder.class.getName());

    private List<List<Long>> correctIterations;
    private int[] iteration;
    private long[] seeds;
    private int performanceHandler;

    public IterationBuilder(List<List<Long>> correctIterations) {
        this.correctIterations = correctIterations;
        iteration = new int[correctIterations.size()];
    }

    public IterationBuilder(List<List<Long>> correctIterations, int[] iteration) {
        this.correctIterations = correctIterations;
        this.iteration = iteration;
    }

    public long[] nextIteration(int generationState) {
        logIteration();
        if (seeds == null) {
            seeds = correctIterations.stream().mapToLong(i -> i.get(0)).toArray();
            return seeds;
        }

        boolean nextIterationIsValid = false;
        int start = generationState == 0 ? iteration.length : generationState;
        for (int i = start - 1; i >= 0; i--) {
            if (iteration[i] < correctIterations.get(i).size() - 1) {
                iteration[i]++;
                for (int j = i + 1; j < iteration.length; j++) {
                    iteration[j] = 0;
                }
                nextIterationIsValid = true;
                break;
            }
        }
        if (nextIterationIsValid) {
            for (int i = 0; i < iteration.length; i++) {
                seeds[i] = correctIterations.get(i).get(iteration[i]);
            }
            return seeds;
        } else {
            throw new RuntimeException("No iterations left");
        }
    }

    public void logIteration() {
        if (performanceHandler++ == 0) {
            StringBuilder sb = new StringBuilder("Current seeds: ");
            for (int i = 0; i < iteration.length; i++) {
                sb.append(iteration[i]).append("/").append(correctIterations.get(i).size()).append(" ");
            }
            LOG.info(sb.toString());
        }
    }

    public int getSize() {
        return correctIterations.size();
    }
}
