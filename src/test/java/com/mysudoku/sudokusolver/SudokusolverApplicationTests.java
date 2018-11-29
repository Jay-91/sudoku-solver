package com.mysudoku.sudokusolver;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mysudoku.util.ConstraintValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SudokusolverApplicationTests {


  private SudukoSolver solver = mock(SudukoSolver.class);
  private static ConstraintValidator validator = mock(ConstraintValidator.class);

  private static int[][] board;

  private static final int BOARD_SIZE = 9;

  @Before
  public void setupBoard() {
    board = null;
  }

  @Test
  public void testSudoSolver() {
    givenBoard();
    solver.solve(board);
    thenValidateResults();
  }

  private void givenBoard() {
    board = new int[][] {{8, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 3, 6, 0, 0, 0, 0, 0},
        {0, 7, 0, 0, 9, 0, 2, 0, 0}, {0, 5, 0, 0, 0, 7, 0, 0, 0}, {0, 0, 0, 0, 4, 5, 7, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 3, 0}, {0, 0, 1, 0, 0, 0, 0, 6, 8}, {0, 0, 8, 5, 0, 0, 0, 1, 0},
        {0, 9, 0, 0, 0, 0, 4, 0, 0}};

  }

  private void thenValidateResults() {
    assertTrue(validator.rowConstraint(board, BOARD_SIZE - 1));
    assertTrue(validator.columnConstraint(board, BOARD_SIZE - 1));
    assertTrue(validator.subsectionConstraint(board, BOARD_SIZE - 1, BOARD_SIZE - 1));
  }

}
