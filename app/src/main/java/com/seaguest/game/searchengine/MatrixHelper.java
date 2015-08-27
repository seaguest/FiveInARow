package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Chessman;
import com.seaguest.game.chessboard.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Evaluation strategy. <br>
 * <p/>
 * Rules:
 * <ul>
 * <li>1, five consecutive X, +100000</li>
 * <li>2, 4 consecutive X with two side open, +10000</li>
 * <li>3, 4 consecutive X with one side open, +1000</li>
 * <li>4, 3 consecutive X with two side open, +1000</li>
 * <li>5, 3 consecutive X with one side open, +100</li>
 * <li>6, 2 consecutive X with two side open, +100</li>
 * <li>7, 2 consecutive X with one side open, +10</li>
 * <li>8, 1 consecutive X with two side open, +10</li>
 * <li>9, 1 consecutive X with one side open, +1</li>
 * <p/>
 * <li>1, five consecutive O, -100</li>
 * <li>2, 4 consecutive O with two side open, -100</li>
 * <li>3, 4 consecutive O with one side open, -80</li>
 * <li>4, 3 consecutive O with two side open, -80</li>
 * <li>5, 3 consecutive O with one side open, -60</li>
 * <li>6, 2 consecutive O with two side open, -60</li>
 * <li>7, 2 consecutive O with one side open, -40</li>
 * <li>8, 1 consecutive O with two side open, -40</li>
 * <li>9, 1 consecutive O with one side open, -20</li>
 * </ul>
 *
 * @author tyhk1987
 */

public class MatrixHelper {


    public static void main(String[] args) {
        int[][] board = {
                {0, 0, 0, 1, 1, 0, 2, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };


        int[][] dias = MatrixHelper.getLines(board);
        System.out.println(Arrays.deepToString(dias));
    }

    public static int[][] getLines(int[][] board) {
        int[][] rows = getRows(board);
        int[][] columns = getColumns(board);
        int[][] rightDias = getRightDiagonals(board);
        int[][] leftDias = getLeftDiagonals(board);

        int[][] result1 = append(rows, columns);
        int[][] result2 = append(rightDias, leftDias);

        return append(result1, result2);
    }

    public static int[][] append(int[][] a, int[][] b) {
        int[][] result = new int[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static int[][] getRows(int[][] board) {
        int[][] rows = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, rows[i], 0, board.length);
        }
        return rows;
    }

    public static int[][] getColumns(int[][] board) {
        int[][] columns = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            int[] res = new int[board.length];
            for (int j = 0; j < board.length; j++) {
                res[j] = board[j][i];
            }
            columns[i] = res;
        }
        return columns;
    }

    /**
     * get all lines for the point
     *
     * @param board
     * @param position
     * @return
     */
    public static List<int[]> getLines(int[][] board, Chessman position, int[][][] analyzedFlag) {
        List<int[]> lines = new ArrayList<int[]>();

        // row, direction 0
        if(analyzedFlag[position.x][position.y][0] != 255){
            int[] row = board[position.x];
            lines.add(row);

            // flag as analyzed
            for(int i=0; i<board.length; i++){
                analyzedFlag[position.x][i][0] = 255;
            }
        }

        // column direction 1
        if(analyzedFlag[position.x][position.y][1] != 255){
            int[] column = new int[board.length];
            for (int i = 0; i < board.length; i++) {
                column[i] = board[i][position.y];

                // flag as analyzed
                analyzedFlag[i][position.y][0] = 255;
            }

            lines.add(column);
        }



        // left diagnal direction 2
        if(analyzedFlag[position.x][position.y][2] != 255) {
            if (position.x + position.y <= board.length - 1) {
                int[] leftDia = new int[position.x + position.y + 1];
                for (int i = 0; i < position.x + position.y + 1; i++) {
                    leftDia[i] = board[i][position.x + position.y - i];

                    // flag as analyzed
                    analyzedFlag[i][position.x + position.y - i][2] = 255;
                }
                lines.add(leftDia);
            } else {
                int[] leftDia = new int[2 * board.length - 1 - (position.x + position.y)];
                for (int i = 0; i < 2 * board.length - 1 - (position.x + position.y); i++) {
                    leftDia[i] = board[position.x + position.y - board.length + 1 + i][board.length - 1 - i];

                    // flag as analyzed
                    analyzedFlag[position.x + position.y - board.length + 1 + i][board.length - 1 - i][2] = 255;
                }
                lines.add(leftDia);
            }
        }

        // right diagonal direction 3
        if(analyzedFlag[position.x][position.y][3] != 255) {
            int[] rightDia = new int[board.length - Math.abs(position.y - position.x)];
            if (position.x > position.y) {
                for (int i = 0; i < board.length + position.y - position.x; i++) {
                    rightDia[i] = board[position.x - position.y + i][i];

                    // flag as analyzed
                    analyzedFlag[position.x - position.y + i][i][3] = 255;
                }
            } else {
                for (int i = 0; i < board.length + position.x - position.y; i++) {
                    rightDia[i] = board[i][position.y - position.x + i];

                    // flag as analyzed
                    analyzedFlag[i][position.y - position.x + i][3] = 255;
                }
            }
            lines.add(rightDia);
        }
        return lines;
    }


        /**
         * This Diagonal starts from the right top side to left down side. <br>
         * \ direction <br>
         * <p/>
         * the starting point should be <br>
         * {(0, squareSize-targetSize). (targetSize-1, squareSize-1)} <br>
         * then end point is <br>
         * {(squareSize-targetSize, 0). (squareSize-1, targetSize-1)}
         * <p/>
         * the total size will be 2*(squareSize-targetSize)+1
         *
         * @return
         */
    public static int[][] getRightDiagonals(int[][] board) {
        int size = board.length;
        int[][] diagonals = new int[2 * (size - Configuration.targetSize) + 1][size];

        for (int i = 0; i < (size - Configuration.targetSize + 1); i++) {
            int[] res = new int[size - i];
            for (int j = 0; j < (size - i); j++) {
                res[j] = board[j][j + i];
            }
            diagonals[i] = res;
        }

        for (int i = 0; i < (size - Configuration.targetSize); i++) {
            int[] res = new int[size - i - 1];
            for (int j = 0; j < (size - i - 1); j++) {
                res[j] = board[i + j + 1][j];
            }
            diagonals[i + size - Configuration.targetSize + 1] = res;
        }
        return diagonals;
    }

    /**
     * This Diagonal starts from the left top side to right down side. <br>
     * / direction <br>
     * the starting point should be <br>
     * {(0, targetSize-1). (targetSize-1, 0)} <br>
     * then end point is <br>
     * {(squareSize-targetSize, squareSize-1). (squareSize-1, squareSize-targetSize)}
     * <p/>
     * the total size will be 2*(squareSize-targetsize)+1
     *
     * @return
     */
    public static int[][] getLeftDiagonals(int[][] board) {
        int size = board.length;
        int[][] diagonals = new int[2 * (size - Configuration.targetSize) + 1][size];

        for (int i = 0; i < (size - Configuration.targetSize + 1); i++) {
            int[] res = new int[size - i];
            for (int j = 0; j < (size - i); j++) {
                res[j] = board[j][size - 1 - i - j];
            }
            diagonals[i] = res;
        }

        for (int i = 0; i < (size - Configuration.targetSize); i++) {
            int[] res = new int[size - i - 1];
            for (int j = 0; j < (size - i - 1); j++) {
                res[j] = board[i + 1 + j][size - 1 - j];
            }
            diagonals[i + size - Configuration.targetSize + 1] = res;
        }
        return diagonals;
    }

}
