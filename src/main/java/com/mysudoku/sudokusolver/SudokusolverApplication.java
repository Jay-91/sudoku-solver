package com.mysudoku.sudokusolver;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.mysudoku.exceptions.InvalidInputFileException;

@SpringBootApplication
public class SudokusolverApplication {

  private static Logger logger = LoggerFactory.getLogger(SudokusolverApplication.class);
  private static int[][] board;

  public static void main(String[] args) throws InvalidInputFileException, IOException {
    ApplicationContext context = SpringApplication.run(SudokusolverApplication.class, args);
    SudukoSolver solver = context.getBean(SudukoSolver.class);
    logger.info("=========Start of Solver=========");
    board = solver.retriveInput(args[0]);
    solver.solve(board);
    solver.printBoard(board);
    logger.info("==========End of solver=========");
  }
}
