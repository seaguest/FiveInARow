package com.seaguest.game.minimax;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class MiniMaxBoard {

    com.seaguest.game.minimax.Evaluator evaluator;

    public int size;

    List<Point> availablePoints;
    char[][] board;

    List<com.seaguest.game.minimax.PointsAndScores> rootsChildrenScore = new ArrayList<com.seaguest.game.minimax.PointsAndScores>();

    public MiniMaxBoard() {
    }

    public void setBoard(char[][] board) {
        this.board = board;
        this.size = board.length;
        evaluator = new com.seaguest.game.minimax.Evaluator(this.size);
    }

    public char[][] getBoard() {
        return board;
    }

    public int getSize() {
        return this.size;
    }

    public int alphaBetaMinimax(int alpha, int beta, int depth, char player) {

        if (beta <= alpha) {
//	    System.out.println("Pruning at depth = " + depth);
            if (player == Config.X) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        if (depth == Config.depth || isGameOver()) {
            return evaluator.evaluate(this);
        }

        List<Point> pointsAvailable = getAvailableStates();

        if (pointsAvailable.isEmpty()) {
            return 0;
        }

        if (depth == 0) {
            rootsChildrenScore.clear();
        }

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);

            int currentScore = 0;

            if (player == Config.X) {
                placeAMove(point, Config.X);
                currentScore = alphaBetaMinimax(alpha, beta, depth + 1, Config.O);
                maxValue = Math.max(maxValue, currentScore);

                // Set alpha
                alpha = Math.max(currentScore, alpha);

                if (depth == 0) {
                    rootsChildrenScore.add(new com.seaguest.game.minimax.PointsAndScores(currentScore, point));
                }
            } else if (player == Config.O) {
                placeAMove(point, Config.O);
                currentScore = alphaBetaMinimax(alpha, beta, depth + 1, Config.X);
                minValue = Math.min(minValue, currentScore);

                // Set beta
                beta = Math.min(currentScore, beta);
            }
            // reset board
            board[point.x][point.y] = Config.EMPTY;

            // If a pruning has been done, don't evaluate the rest of the sibling states
            if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) {
                break;
            }
        }
        return player == Config.X ? maxValue : minValue;
    }

    public boolean isGameOver() {
        int xwin = isXWon();
        // Game is over is someone has won, or board is full (draw)
        return (xwin == 1 || xwin == -1 || getAvailableStates().isEmpty());
    }

    public int isXWon() {
        return evaluator.isXWin(this);
    }

    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<Point>();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board[i][j] == Config.EMPTY) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    public void placeAMove(Point point, char player) {
        board[point.x][point.y] = player; // player = 1 for X, 2 for O
    }

    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScore.size(); ++i) {
            if (MAX < rootsChildrenScore.get(i).score) {
                MAX = rootsChildrenScore.get(i).score;
                best = i;
            }
        }

/*
        System.out.println("best:"+ best + "--Size:" + rootsChildrenScore.size());
        if(best == -1){
            displayBoard();
        }
*/

        return rootsChildrenScore.get(best).point;
    }



    public void displayBoard() {
        System.out.println("------------------borad----------------");
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println("------------------borad----------------");
    }

}
