package org.rozdy;

import org.rozdy.model.Board;
import org.rozdy.solver.SeedsSerializer;
import org.rozdy.solver.Solver;

import java.util.List;

public class Demo {

    public static void main(String[] args) {
        Board board = new Board(8, 12);
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
        Solver solver = new Solver(board);
        List<List<Long>> correctIterations = solver.findCorrectIterations();
        SeedsSerializer.write(correctIterations, "correct_iterations");
        correctIterations = solver.recheckCorrectIterations(correctIterations);
        SeedsSerializer.write(correctIterations, "rechecked_iterations");
        solver.solve(correctIterations);
    }
}
