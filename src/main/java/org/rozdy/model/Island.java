package org.rozdy.model;

import static org.rozdy.model.Cell.CONNECTIONS;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Island {

    private int size;
    private Set<Cell> cells = new LinkedHashSet<>();

    public Island(int size, Cell firstCell) {
        this.size = size;
        cells.add(firstCell);
        firstCell.setIsland(this);
    }

    public boolean checkIslandConnections() {
        return cells.stream().allMatch(Cell::checkCellConnections);
    }

    public void addCell(Cell cell) {
        cells.add(cell);
        cell.setIsland(this);
    }

    public boolean generateIsland(long seed) {
        for (int i = 1; i < size; i++) {
            Cell root = cells.stream().skip(seed % i).findFirst().orElse(null);
            if (root == null) {
                return false;
            }
            seed /= i;
            Cell newCell = CONNECTIONS.get((int) (seed % 4)).apply(root);
            if (newCell == null) {
                return false;
            }
            addCell(newCell);
            seed /= 4;
        }
        return cells.size() == size;
    }

    public long getMaxSeed() {
        return ((long) Math.pow(4, size - 1)) * IntStream.rangeClosed(1, size - 1).reduce(1, (x, y) -> x * y);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }
}
