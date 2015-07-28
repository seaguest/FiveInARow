package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Point;
import com.seaguest.game.player.IPlayer;


public class MoveGenerator {
    public final static String TAG = "MoveGenerator";

    public final static int SIZE = 10;
    public int chessBoardSize;

    MoveAndScore[][] movesListAndScore;

    int moveCount;


    public MoveGenerator(int column) {
        // TODO Auto-generated constructor stub
        chessBoardSize = column;
        movesListAndScore = new MoveAndScore[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            movesListAndScore[i] = new MoveAndScore[column * column];
            for (int j = 0; j < movesListAndScore[i].length; j++) {
                movesListAndScore[i][j] = new MoveAndScore();
            }
        }
    }

    public int createPossibleMove(int[][] chessTable, int nPlay, int nSide) {
        moveCount = 0;
        for (int i = 0; i < chessTable.length; i++) {
            for (int j = 0; j < chessTable[i].length; j++) {
                if (chessTable[i][j] == IPlayer.NONE) {
                    addMove(i, j, nPlay);
                }
            }
        }
        return moveCount;
    }

    static boolean isVialMove() {
        return false;
    }

    public int addMove(int topI, int topJ, int nPlay) {

        movesListAndScore[nPlay][moveCount].position = new Point(topI, topJ);
        moveCount++;
        switch (chessBoardSize) {
            case 7:
                movesListAndScore[nPlay][moveCount].score = Evaluator.posValue7[topI][topJ];
                break;
            case 11:
                movesListAndScore[nPlay][moveCount].score = Evaluator.posValue13[topI][topJ];
                break;
            case 13:
                movesListAndScore[nPlay][moveCount].score = Evaluator.posValue13[topI][topJ];
                break;
            case 15:
                movesListAndScore[nPlay][moveCount].score = Evaluator.posValue13[topI][topJ];
                break;
            case 17:
                movesListAndScore[nPlay][moveCount].score = Evaluator.posValue13[topI][topJ];
                break;

            default:
                break;
        }

        return moveCount;
    }

}
