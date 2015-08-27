package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Chessman;
import com.seaguest.game.chessboard.Point;
import com.seaguest.game.player.IPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SearchResult {
    public boolean isFinished;
    public int score;

    public SearchResult(boolean isFinished, int score) {
        this.isFinished = isFinished;
        this.score = score;
    }
}

public class SearchEngine {

    public static int MAX = 200000000;

    public int[][] board;

    public MoveAndScore bestMoveAndScore;

    HistoryHeuristic historyHeuristic;

    MoveGenerator moveGenerator;

    IPlayer ai;
    int maxDepth = 1;

    public MoveAndScore lastMove;


    List<Chessman>  chessmanRecords = new ArrayList<Chessman>();

    public SearchEngine(IPlayer player, int maxDepth) {
        this.ai = player;
        this.maxDepth = maxDepth;
    }


    public void setBoard(int[][] board) {
        this.board = Arrays.copyOf(board, board.length);
        moveGenerator = new MoveGenerator(board.length);
        historyHeuristic = new HistoryHeuristic(board.length);
    }

    public int[][] getBoard() {
        return board;
    }

    public void setMaxDepth(int maxDepth){
        this.maxDepth = maxDepth;
    }

    public void searchBestMove() {
        long t1 = System.currentTimeMillis();
        historyHeuristic.resetHistoryHeristic();
        int score = alphaBeta(0, -MAX, MAX);
        long t2 = System.currentTimeMillis();

        System.out.println("Searching Time:" + (t2 - t1));
        System.out.println("Score:" + score);

    }

    public int alphaBeta(int depth, int alpha, int beta) {
        int chessColor;
        int count, score;

        chessColor = getCurrentPlayerChessmanColor();
        SearchResult result = isSearchFinished(chessColor, depth);
        if (result.isFinished) {
            // displayBoard();
            // System.out.println("Final state : " + result.score + " -----------");
            return result.score;
        }

        count = moveGenerator.createPossibleMove(board, depth, chessColor);

        for (int j = 0; j < count; j++) {
            moveGenerator.movesListAndScore[depth][j].score += historyHeuristic
                    .getHistoryHeristicScore(moveGenerator.movesListAndScore[depth][j]);
        }

        // historyHeuristic.sortStoneMove(moveGenerator.movesListAndScore[depth], true);
        MoveAndScore[] moves = moveGenerator.movesListAndScore[depth];
        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));

        int bestMove = -1;

        for (int j = 0; j < count; j++) {
            MoveAndScore move = moveGenerator.movesListAndScore[depth][j];
            Chessman chessmanToMove = new Chessman(move.position.x, move.position.y, chessColor);

/*             if(depth==0 && !(chessmanToMove.x==2 &&chessmanToMove.y==8)){
                continue;
            }
           if(depth==1 && !(chessmanToMove.x==5 &&chessmanToMove.y==7)){
                continue;
            }
            if(depth==2 && !(chessmanToMove.x==9 &&chessmanToMove.y==0)){
                continue;
            }
            if(depth==3 && !(chessmanToMove.x==9 &&chessmanToMove.y==1)){
                continue;
            }*/

            chessmanRecords.add(chessmanToMove);
            makeMove(chessmanToMove);
            score = -alphaBeta(depth + 1, -beta, -alpha);
            unMakeMove(chessmanToMove);
            chessmanRecords.remove(chessmanToMove);

            if (score > alpha) {
                alpha = score;
                if (depth == 0) {
                    bestMoveAndScore = moveGenerator.movesListAndScore[depth][j];
                    bestMove = j;
                }
            }

            if (alpha >= beta) {
                bestMove = j;
                break;
            }
        }

        if (bestMove != -1) {
            historyHeuristic.saveHistoryScore(moveGenerator.movesListAndScore[depth][bestMove], depth);
        }
        return alpha;
    }

    public MoveAndScore getBestMove() {
        return bestMoveAndScore;
        // MoveAndScore[] moves = moveGenerator.movesListAndScore[0];
        // QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));
        // return moves[0];
    }

    public int getCurrentPlayerChessmanColor() {
        int chessColor;
        if (getNumberOfChessman() % 2 == 0) {
            chessColor = IPlayer.BLACK;
        } else {
            chessColor = IPlayer.WHITE;
        }
        return chessColor;
    }

    public int getNumberOfChessman() {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != IPlayer.NONE) {
                    sum++;
                }
            }

        }
        return sum;
    }

    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player; // player = 1 for X, 2 for O
    }

    public void makeMove(Chessman chessmanToMove) {
        board[chessmanToMove.x][chessmanToMove.y] = chessmanToMove.color;
    }

    public void unMakeMove(Chessman chessmanToUnMove) {
        board[chessmanToUnMove.x][chessmanToUnMove.y] = IPlayer.NONE;
    }

    // incase, level reaches 0, or game over
    public SearchResult isSearchFinished(int playerColor, int depth) {
        SearchResult result = new SearchResult(false, 0);
        Evaluator evaluator = new Evaluator(board);
        boolean isGameOver = evaluator.isGameOver(ai);

        boolean isAIturn = (playerColor == ai.getChessmanColor()) ? true : false; // means if X's
        // turn
        if (isGameOver) {
            result.isFinished = true;
            if (ai.getWinstate() == IPlayer.WIN) { // AI is always X
                if (!isAIturn) { // score for other player
                    result.score = -999999;
                } else {
                    result.score = 999999;
                }
            } else if (ai.getWinstate() == IPlayer.LOST) {
                if (!isAIturn) {
                    result.score = 999999;
                } else {
                    result.score = -999999;
                }
                result.score = -999999;
            } else {
                result.score = 0;
            }
        }

        if (depth >= maxDepth) { // finish the search
            result.isFinished = true;
//            result.score = evaluator.evaluate(ai);
            result.score = evaluator.evaluate(ai, chessmanRecords);
        }
        return result;
    }

    public void displayBoard() {
        System.out.println("------------------borad----------------");
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------borad----------------");
    }

    public void dislayHistoryHeuristic() {
        System.out.println("------------------historyHeuristic----------------");
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------historyHeuristic----------------");
    }

    public void displayMoves(MoveAndScore[] moves) {
        System.out.println("------------------displayMoves----------------");
        for (int i = 0; i < moves.length; i++) {
            System.out.println("Position:[" + moves[i].position.x + "," + moves[i].position.y + "]-Score:"
                    + moves[i].score);
        }

    }

}
