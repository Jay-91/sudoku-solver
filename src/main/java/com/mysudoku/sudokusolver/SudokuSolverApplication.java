package com.mysudoku.sudokusolver;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.mysudoku.exceptions.InvalidInputFileException;

@SpringBootApplication
public class SudokuSolverApplication {

  private static Logger logger = LoggerFactory.getLogger(SudokuSolverApplication.class);

  public static void main(String[] args) throws InvalidInputFileException, IOException {
    ApplicationContext context = SpringApplication.run(SudokuSolverApplication.class, args);
    SudukoSolver solver = context.getBean(SudukoSolver.class);
    logger.info("=========Start of Solver=========");
    solver.solve(solver.retriveInput(args[0]));
    solver.printBoard();
    solver.validateResult();
    logger.info("==========End of solver=========");
  }
}
