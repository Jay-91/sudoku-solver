package com.mysudoku.sudokusolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.mysudoku.exceptions.InvalidInputFileException;
import com.mysudoku.util.ConstraintValidator;

@Component
public class SudukoSolver {

  private Logger logger = LoggerFactory.getLogger(SudukoSolver.class);

  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 9;
  private int count = 0;
  private Pattern pattern = Pattern.compile("[0-9]");
  private Matcher matcher;
  private static int[][] board;
  private static int[][] boardCopy = new int[9][9];

  /**
   * Prints the final answer
   * 
   */
  public void printBoard() {
    for (int row =
        ConstraintValidator.BOARD_START_INDEX; row < ConstraintValidator.BOARD_SIZE; row++) {
      for (int column =
          ConstraintValidator.BOARD_START_INDEX; column < ConstraintValidator.BOARD_SIZE; column++) {
        System.out.print(board[row][column] + " ");
      }
      System.out.println();
    }
  }

  /**
   * Solves the given board using recursive approach
   * 
   * @param board
   * @return status whether puzzle solved or not
   */
  public boolean solve(int[][] board) {
    for (int row =
        ConstraintValidator.BOARD_START_INDEX; row < ConstraintValidator.BOARD_SIZE; row++) {
      for (int column =
          ConstraintValidator.BOARD_START_INDEX; column < ConstraintValidator.BOARD_SIZE; column++) {
        if (board[row][column] == ConstraintValidator.NO_VALUE) {
          for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
            board[row][column] = k;
            if (isValid(board, row, column) && solve(board)) {
              return true;
            }
            board[row][column] = ConstraintValidator.NO_VALUE;
          }
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Check whether the constraints are solved by the board
   * 
   * @param board
   * @param row
   * @param column
   * @return
   */
  private boolean isValid(int[][] board, int row, int column) {
    return ConstraintValidator.rowConstraint(board, row)
        && ConstraintValidator.columnConstraint(board, column)
        && ConstraintValidator.subsectionConstraint(board, row, column);
  }

  /**
   * Creates the board from the input file if valid
   * 
   * @param filePath
   * @return
   * @throws InvalidInputFileException
   * @throws IOException
   */
  public int[][] retriveInput(String filePath) throws InvalidInputFileException, IOException {
    String[] lines = readLines(filePath);
    if (validCount(lines.length)) { // Check Valid line count
      return createBoard(lines);
    } else {
      logger.info("Input board should be 9 * 9");
      throw new InvalidInputFileException("Invalid input board");
    }
  }

  /**
   * Create the board from input lines read from the file
   * 
   * @param lines
   * @return
   * @throws InvalidInputFileException
   */
  private int[][] createBoard(String[] lines) throws InvalidInputFileException {
    board = new int[ConstraintValidator.BOARD_SIZE][ConstraintValidator.BOARD_SIZE];
    int rowCounter = 0;
    for (String line : lines) {
      int columnCounter = 0;
      String[] cells = line.split("\\s+");
      if (validCount(cells.length)) { // Check Valid cell count in each line
        for (String cell : cells) {
          if (patternMatches(cell)) {
            board[rowCounter][columnCounter] = Integer.parseInt(cell);
          } else {
            logger.info("Invalid value (Value other than 0 -9 ) in line {}", rowCounter + 1);
            throw new InvalidInputFileException("Invalid input board");
          }
          columnCounter++;
        }
        rowCounter++;
      } else {
        logger.info("Invalid count in line {}", rowCounter + 1);
        throw new InvalidInputFileException("Invalid input board");
      }
    }
    createCopy(board);
    return board;
  }

  private void createCopy(int[][] board) {
    for (int i = ConstraintValidator.BOARD_START_INDEX; i < ConstraintValidator.BOARD_SIZE; i++) {
      for (int j = ConstraintValidator.BOARD_START_INDEX; j < ConstraintValidator.BOARD_SIZE; j++) {
        boardCopy[i][j] = board[i][j];
      }
    }
  }

  /**
   * checks cell values whether the patter is [0-9]
   * 
   * @param cell
   * @return
   */
  private boolean patternMatches(String cell) {
    matcher = pattern.matcher(cell);
    return matcher.matches();
  }

  /**
   * Checks the valid number of rows and colums
   * 
   * @param count
   * @return
   */
  private boolean validCount(int count) {
    return count == ConstraintValidator.BOARD_SIZE;
  }

  /**
   * Reads the input file to create the board
   * 
   * @param filePath
   * @return
   * @throws IOException
   */
  private String[] readLines(String filePath) throws IOException {
    return Files.lines(Paths.get(filePath)).filter(line -> (line != null && line.length() > 0))
        .toArray(String[]::new);
  }

  public boolean solveReverse() {
    for (int row =
        ConstraintValidator.BOARD_START_INDEX; row < ConstraintValidator.BOARD_SIZE; row++) {
      for (int column =
          ConstraintValidator.BOARD_START_INDEX; column < ConstraintValidator.BOARD_SIZE; column++) {
        if (boardCopy[row][column] == ConstraintValidator.NO_VALUE) {
          for (int k = MAX_VALUE; k >= MIN_VALUE; k--) {
            boardCopy[row][column] = k;
            if (isValid(boardCopy, row, column) && solveReverse()) {
              return true;
            }
            boardCopy[row][column] = ConstraintValidator.NO_VALUE;
          }
          return false;
        }
      }
    }
    return true;
  }

  public void validateResult() {
    solveReverse();
    for (int i = ConstraintValidator.BOARD_START_INDEX; i < ConstraintValidator.BOARD_SIZE; i++) {
      for (int j = ConstraintValidator.BOARD_START_INDEX; j < ConstraintValidator.BOARD_SIZE; j++) {
        if (boardCopy[i][j] != board[i][j]) {
          count++;
        }
      }
    }
    if (count > 0) {
      logger.info("Input has Multiple solutions");
      for (int row =
          ConstraintValidator.BOARD_START_INDEX; row < ConstraintValidator.BOARD_SIZE; row++) {
        for (int column =
            ConstraintValidator.BOARD_START_INDEX; column < ConstraintValidator.BOARD_SIZE; column++) {
          System.out.print(boardCopy[row][column] + " ");
        }
        System.out.println();
      }

    } else {
      logger.info("Input has unique solution");
    }

  }


}
