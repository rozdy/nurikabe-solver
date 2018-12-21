package org.rozdy.model;

import static java.util.Collections.emptySet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Board {

    private int width;
    private int height;
    private List<Island> islands;
    private List<RequiredIsland> requiredIslands = new ArrayList<>();
    private Cell[][] cells;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        islands = new ArrayList<>();
        cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
        initCells();
    }

    private void initCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i != 0) {
                    cells[i][j].setUpper(cells[i - 1][j]);
                }
                if (i != height - 1) {
                    cells[i][j].setLower(cells[i + 1][j]);
                }
                if (j != 0) {
                    cells[i][j].setLeft(cells[i][j - 1]);
                }
                if (j != width - 1) {
                    cells[i][j].setRight(cells[i][j + 1]);
                }
            }
        }
    }

    public void addRequiredIsland(int size, int i, int j) {
        requiredIslands.add(new RequiredIsland(size, i, j));
    }

    public void clear() {
        islands.clear();
        Arrays.stream(cells).flatMap(Arrays::stream).forEach(c -> c.setIsland(null));
        requiredIslands.stream().map(i -> new Island(i.size, cells[i.i][i.j])).forEach(i -> islands.add(i));
    }

    public boolean isComplete() {
        return isAllIslandsFull() && isAllRiversCorrect();
    }

    private boolean isAllIslandsFull() {
        if (islands.stream().anyMatch(i -> i.getSize() != i.getCells().size())) {
            return false;
        }
        return islands.stream().allMatch(Island::checkIslandConnections);
    }

    public boolean isAllRiversCorrect() {
        Set<Cell> touched = getConnectedRivers();
        int correctRiversCount = width * height - islands.stream().mapToInt(Island::getSize).sum();
        return touched.size() == correctRiversCount && touched.stream().noneMatch(Cell::isLake);
    }

    public boolean isRiversBlocked() {
        Set<Cell> touched = getConnectedRivers();
        long allRiversCount = Arrays.stream(cells).flatMap(Arrays::stream).filter(c -> c.getIsland() == null).count();
        return touched.size() != allRiversCount;
    }

    private Set<Cell> getConnectedRivers() {
        Cell first = Stream.of(cells).flatMap(Arrays::stream).filter(c -> c.getIsland() == null).findAny().orElse(null);
        if (first == null) {
            return emptySet();
        }
        Set<Cell> touched = ConcurrentHashMap.newKeySet();
        touched.add(first);
        while (touched.stream().anyMatch(c -> touched.addAll(c.getConnectedRivers()))) {
        }
        return touched;
    }

    public List<Island> getIslands() {
        return islands;
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(cells[i][j].getIsland() == null ? 0 : 1);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private class RequiredIsland {
        private int size;
        private int i;
        private int j;

        RequiredIsland(int size, int i, int j) {
            this.size = size;
            this.i = i;
            this.j = j;
        }
    }
}
