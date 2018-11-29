package com.mysudoku.util;

import java.util.stream.IntStream;

public class ConstraintValidator {

  public static final int BOARD_SIZE = 9;
  public static final int SUBSECTION_SIZE = 3;
  public static final int BOARD_START_INDEX = 0;
  public static final int NO_VALUE = 0;

  /**
   * Checks subsection constraint 3*3 
   * @param board
   * @param row
   * @param column
   * @return
   */
  public static boolean subsectionConstraint(int[][] board, int row, int column) {
    boolean[] constraint = new boolean[BOARD_SIZE];
    int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
    int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

    int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
    int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

    for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
      for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
        if (!checkConstraint(board, r, constraint, c))
          return false;
      }
    }
    return true;
  }

  /**
   * Checks the column constraint
   * @param board
   * @param column
   * @return
   */
  public static boolean columnConstraint(int[][] board, int column) {
    boolean[] constraint = new boolean[BOARD_SIZE];
    return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
        .allMatch(row -> checkConstraint(board, row, constraint, column));
  }

  /**
   * Checks the row constraint
   * @param board
   * @param row
   * @return
   */
  public static boolean rowConstraint(int[][] board, int row) {
    boolean[] constraint = new boolean[BOARD_SIZE];
    return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
        .allMatch(column -> checkConstraint(board, row, constraint, column));
  }

  public static boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
    if (board[row][column] != NO_VALUE) {
      if (!constraint[board[row][column] - 1]) {
        constraint[board[row][column] - 1] = true;
      } else {
        return false;
      }
      
    }
    return true;
  }
}
