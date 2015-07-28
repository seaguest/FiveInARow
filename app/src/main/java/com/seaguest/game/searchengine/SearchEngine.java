package com.seaguest.game.searchengine;


import com.seaguest.game.chessboard.Point;
import com.seaguest.game.player.IPlayer;

import java.util.Arrays;
import java.util.List;

public class SearchEngine {
    int computerChessmanColor = IPlayer.WHITE;

    public int[][] board;
    public MoveAndScore bestMoveAndScore;
    HistoryHeuristic historyHeuristic;

    MoveGenerator moveGenerator;

    List<Point> availablePoints;

    Eveluation eveluation;

    public SearchEngine(int[][] board) {
        this.board = board;

        moveGenerator = new MoveGenerator(board.length);

        historyHeuristic = new HistoryHeuristic(board.length);

        eveluation = new Eveluation(board.length);

    }

    public void setBoard(int[][] board) {
        this.board = Arrays.copyOf(board, board.length);
    }

    public int[][] getBoard() {
        return board;
    }

    public void searchBestMove() {
        historyHeuristic.resetHistoryHeristic();

        //       alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        //alphaBetaV1(Config.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, IPlayer.X);
        alphaBeta(Config.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int alphaBetaMinimax(int alpha, int beta, int depth) {
        int chessColor;

        chessColor = getCurrentPlayerChessmanColor();

        int score = isGameOver(chessColor, depth);
        if (score != 0) {
            return score;
        }

        int count = moveGenerator.createPossibleMove(board, depth, chessColor);

        for (int j = 0; j < count; j++) {
            moveGenerator.movesListAndScore[depth][j].score +=
                    historyHeuristic.getHistoryHeristicScore(moveGenerator.movesListAndScore[depth][j]);
        }

        //historyHeuristic.sortStoneMove(moveGenerator.movesListAndScore[depth], true);
        MoveAndScore[] moves = moveGenerator.movesListAndScore[depth];
        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));

        int bestMove = -1;

        for (int i = 0; i < count; ++i) {
            makeMove(moveGenerator.movesListAndScore[depth][i], chessColor);
            score = -alphaBetaMinimax(-beta, -alpha, depth - 1);
            unMakeMove(moveGenerator.movesListAndScore[depth][i]);

            if (score > alpha) {
                alpha = score;
                if (depth == 0) {
                    bestMoveAndScore = moveGenerator.movesListAndScore[depth][i];
                    bestMove = i;
                }
            }
            if (alpha >= beta) {
                bestMove = i;
                break;
            }

        }
        if (bestMove != -1) {
            historyHeuristic.saveHistoryScore(moveGenerator.movesListAndScore[depth][bestMove], depth);
        }

        return alpha;
    }

    public int alphaBeta(int depth, int alpha, int beta) {
        int chessColor;
        int count, i;

        chessColor = getCurrentPlayerChessmanColor();
        int score = isSearchFinished(chessColor, depth);
        if (score != 0) {
            return score;
        }

        //获得所有合法走法
        count = moveGenerator.createPossibleMove(board, depth, chessColor);

        //获得所有走法的历史得分
        for (int j = 0; j < count; j++) {
            moveGenerator.movesListAndScore[depth][j].score +=
                    historyHeuristic.getHistoryHeristicScore(moveGenerator.movesListAndScore[depth][j]);
        }
        //对count走法按历史得分排序 降序
        //historyHeuristic.sortStoneMove(moveGenerator.movesListAndScore[depth], true);
        MoveAndScore[] moves = moveGenerator.movesListAndScore[depth];
        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));

        //记录最佳走法
        int bestMove = -1;

        //搜索每一种走法的评分
        for (int j = 0; j < count; j++) {
            //走一步
            makeMove(moveGenerator.movesListAndScore[depth][j], chessColor);
            //走这步的评分
            score = -alphaBeta(depth - 1, -beta, -alpha);
            //撤销走这一步
            unMakeMove(moveGenerator.movesListAndScore[depth][j]);

            if (score > alpha) {
                alpha = score;
                if (depth == Config.depth) {
                    bestMoveAndScore = moveGenerator.movesListAndScore[depth][j];
                    bestMove = j;
                }
            }

            //剪枝
            if (alpha >= beta) {
                bestMove = j;
                break;
            }
        }

        //将最佳走法保存到历史记录表
        if (bestMove != -1) {
            historyHeuristic.saveHistoryScore(moveGenerator.movesListAndScore[depth][bestMove], depth);
        }

        return alpha;
    }


    public int alphaBetaV1(int alpha, int beta, int depth, int player) {
        int chessColor;

        chessColor = getCurrentPlayerChessmanColor();
        int score = isSearchFinished(chessColor, depth);
        if (score != 0) {
            return score;
        }

        int count = moveGenerator.createPossibleMove(board, depth, chessColor);

 /*       for (int j = 0; j < count; j++) {
            moveGenerator.movesListAndScore[depth][j].score +=
                    historyHeuristic.getHistoryHeristicScore(moveGenerator.movesListAndScore[depth][j]);
        }*/

        //historyHeuristic.sortStoneMove(moveGenerator.movesListAndScore[depth], true);
/*
        MoveAndScore[] moves = moveGenerator.movesListAndScore[depth];
        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));
*/

        int currentBeta = Integer.MAX_VALUE;

        int bestMove = -1;
        int bestScore = 0;

        if (player == IPlayer.X) {
            for (int i = 0; i < count; ++i) {
                MoveAndScore nextMove = moveGenerator.movesListAndScore[depth][i];
                makeMove(nextMove, chessColor);
                bestScore =  Math.max(alpha, alphaBetaV1(alpha, beta, depth - 1, IPlayer.O));
                nextMove.score += bestScore;
                unMakeMove(nextMove);

                if (bestScore >= beta) {
                    bestMove = i;
                    break;
                }
            }
        }else {
            for (int i = 0; i < count; ++i) {
                MoveAndScore nextMove = moveGenerator.movesListAndScore[depth][i];
                makeMove(nextMove, chessColor);
                bestScore =  Math.min(beta, alphaBetaV1(alpha, beta, depth - 1, IPlayer.X));
                nextMove.score += bestScore;
                unMakeMove(nextMove);

                if (alpha >= bestScore) {
                    bestMove = i;
                    break;
                }
            }
        }

        if (bestMove != -1) {
//            historyHeuristic.saveHistoryScore(moveGenerator.movesListAndScore[depth][bestMove], depth);
        }
        return bestScore;
    }


    public MoveAndScore getBestMove(){
        MoveAndScore[] moves = moveGenerator.movesListAndScore[Config.depth];
        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));
        return moves[0];
    }

    public int alphaBetavv(int dept, int alpha, int beta) {
        int score, chessColor;
        int count, i;

        i = isGameOverA(board, dept);
        if (i != 0) {
            return i;
        }

        chessColor = getCurrentPlayerChessmanColor();
        if (dept <= 0) {
            return eveluation.Eveluate(board, chessColor);
        }

        count = moveGenerator.createPossibleMove(board, dept, chessColor);


        for (int j = 0; j < count; j++) {
            moveGenerator.movesListAndScore[dept][j].score +=
                    historyHeuristic.getHistoryHeristicScore(moveGenerator.movesListAndScore[dept][j]);
        }

        //historyHeuristic.sortStoneMove(moveGenerator.movesListAndScore[dept], true);
        MoveAndScore[] moves = moveGenerator.movesListAndScore[dept];
        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));


        int bestMove = -1;


        for (int j = 0; j < count; j++) {

            makeMove(moveGenerator.movesListAndScore[dept][j], chessColor);

            score = -alphaBetavv(dept - 1, -beta, -alpha);

            unMakeMove(moveGenerator.movesListAndScore[dept][j]);

            if (score > alpha) {
                alpha = score;
                if (dept == Config.depth) {
                    bestMoveAndScore = moveGenerator.movesListAndScore[dept][j];
                    bestMove = j;
                }
            }


            if (alpha >= beta) {
                bestMove = j;
                break;
            }
        }

        if (bestMove != -1) {
            historyHeuristic.saveHistoryScore(moveGenerator.movesListAndScore[dept][bestMove], dept);
        }

        return alpha;
    }

    public int getCurrentPlayerChessmanColor() {
        int chessColor;
        if(getNumberOfChessman()% 2 ==0){
            chessColor = IPlayer.BLACK;
        }else{
            chessColor = IPlayer.WHITE;
        }
        return chessColor;
    }

    public int getNumberOfChessman(){
        int sum =0;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j] == IPlayer.NONE){
                    sum++;
                }            }

        }
        return sum;
    }

    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player; // player = 1 for X, 2 for O
    }

    public void makeMove(MoveAndScore move, int type) {
        board[move.position.x][move.position.y] = type;
    }

    public void unMakeMove(MoveAndScore move) {
        board[move.position.x][move.position.y] = IPlayer.NONE;
    }

    public int isGameOver1(int[][] chessTable, int nDepth) {
        int score, chessColor;

        chessColor = getCurrentPlayerChessmanColor();

        score = eveluation.Eveluate(chessTable, chessColor);

        if (Math.abs(score) > 8000) {
            return score;
        }

        return 0;
    }


    public int isGameOver(int player, int depth) {
        Evaluator evaluator = new Evaluator(board);
        int score = evaluator.evaluate(player, false);

        if (depth <= 0) { //finish the search
            return score;
        }

        if (Math.abs(score) > 100000) {
            return score;
        }

        return 0;
    }

    // incase, level reaches 0, or game over
    public int isSearchFinished(int playerColor, int depth) {
        Evaluator evaluator = new Evaluator(board);
        boolean isX = false;
        if(playerColor == IPlayer.WHITE){        // back is always first
            isX = true;
        }
        int score = evaluator.evaluate(playerColor, isX);

        if (depth <= 0) { //finish the search
            return score;
        }

        if (Math.abs(score) > 100000) {
            return score;
        }

        return 0;
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
            System.out.println("Position:[" + moves[i].position.x + "," + moves[i].position.y + "]-Score:" + moves[i].score);
        }

    }

    public int isGameOverA(int[][] chessTable, int nDepth) {
        int score, chessColor;

        chessColor = getCurrentPlayerChessmanColor();
        Evaluator evaluator = new Evaluator(board);

        score = evaluator.evaluate(chessColor, false);

        if (Math.abs(score) > 8000) {
            return score;
        }

        return 0;
    }

}
