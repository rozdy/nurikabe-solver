package org.rozdy;

import org.rozdy.model.Board;
import org.rozdy.solver.Solver;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(12, 8);
        board.addRequiredIsland(2, 2, 0);
        board.addRequiredIsland(3, 6, 0);
        board.addRequiredIsland(9, 1, 3);
        board.addRequiredIsland(6, 4, 3);
        board.addRequiredIsland(5, 7, 3);
        board.addRequiredIsland(2, 2, 6);
        board.addRequiredIsland(5, 1, 7);
        board.addRequiredIsland(3, 4, 7);
        board.addRequiredIsland(2, 7, 7);
        board.addRequiredIsland(2, 2, 8);
        board.addRequiredIsland(4, 2, 11);
        board.addRequiredIsland(4, 5, 11);
        board.addRequiredIsland(3, 7, 11);
        Solver.solve(board);
        System.out.println(board);
    }
}
