package org.rozdy.model;

import static java.util.Collections.emptySet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cell {

    public static final List<Function<Cell, Cell>> CONNECTIONS = Arrays
            .asList(Cell::getUpper, Cell::getLeft, Cell::getLower, Cell::getRight);

    private Cell upper;
    private Cell left;
    private Cell lower;
    private Cell right;
    private Island island;
    private String name;

    public boolean checkCellConnections() {
        if (island == null) {
            return true;
        }
        return CONNECTIONS.stream().allMatch(f -> f.apply(this) == null || f.apply(this).getIsland() == null
                || f.apply(this).getIsland() == island);
    }

    public Set<Cell> getConnectedRivers() {
        if (island != null) {
            return emptySet();
        }
        return CONNECTIONS.stream()
                .map(f -> f.apply(this))
                .filter(c -> c != null && c.island == null)
                .collect(Collectors.toSet());
    }

    public boolean isLake() {
        Cell cell = this;
        for (Function<Cell, Cell> f: CONNECTIONS) {
            if (cell == null || cell.island != null) {
                return false;
            }
            cell = f.apply(cell);
        }
        return true;
    }

    public Cell getUpper() {
        return upper;
    }

    public void setUpper(Cell upper) {
        this.upper = upper;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public Cell getLower() {
        return lower;
    }

    public void setLower(Cell lower) {
        this.lower = lower;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell right) {
        this.right = right;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    @Override
    public String toString() {
        if (name != null) {
            return name;
        }
        int i = 0;
        Cell cell = this;
        while (cell.getUpper() != null) {
            i++;
            cell = cell.getUpper();
        }
        int j = 0;
        while (cell.getLeft() != null) {
            j++;
            cell = cell.getLeft();
        }
        name = i + " " + j + " ";
        return name;
    }
}
