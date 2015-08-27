package com.seaguest.game.chessboard;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.seaguest.game.player.IPlayer;
import com.seaguest.game.player.Player;
import com.seaguest.game.searchengine.Evaluator;

public class ChessBoard {
    public Object context;

    // the chessboard
    int[][] chessboard;
    // the list which contains all chessman on board
    public BoardRecorder boardrecorder;

    public int chessBoardSize = 13;

    int width;
    int height;
    // board boundary
    int startx1, endx1;
    int starty1, endy1;
    int sideLength;
    Point[][] boardPoints;
    // five stars
    Point[] fiveStars = new Point[5];

    boolean isGmaeOver = false;

    // computer player is white
    IPlayer playerA;
    IPlayer playerB;


    public ChessBoard(Object context, int width, int height ) {
        this.context = context;
        this.width = width;
        this.height = height;

        // init boards points
        boardPoints = new Point[chessBoardSize][chessBoardSize];
        chessboard = new int[chessBoardSize][chessBoardSize];
        boardrecorder = new BoardRecorder();
    }

    public void resetChessBoard() {
        chessboard = new int[chessBoardSize][chessBoardSize];
        boardPoints = new Point[chessBoardSize][chessBoardSize];
        boardrecorder.clear();
        isGmaeOver = false;
        initBoundBoundary(chessBoardSize);
    }

    public void undoLastStep(Player player) {
        if (!boardrecorder.empty() && player.setting.allowundo) {
            Chessman lastOther = boardrecorder.pop();
            chessboard[lastOther.x][lastOther.y] = IPlayer.NONE;
            if (lastOther.color != player.getChessmanColor()) {
                Chessman lastMine = boardrecorder.pop();
                chessboard[lastMine.x][lastMine.y] = IPlayer.NONE;
            }

            // set the status as not game over after undo.
            if (isGmaeOver) {
                isGmaeOver = false;
            }
        }
    }


    // set board boundary
    public void initBoundBoundary(int size) {
        chessBoardSize = size;
        int modx = width % chessBoardSize;
        sideLength = width / chessBoardSize;
        startx1 = (modx + sideLength) / 2;
        endx1 = width - startx1;
        int mody = (height - width) / 2;
        starty1 = startx1;
        endy1 = height - starty1;

        // find five starts
        fiveStars[0] = new Point(3, 3);
        fiveStars[1] = new Point(3, chessBoardSize - 4);
        fiveStars[2] = new Point((chessBoardSize - 1) / 2, (chessBoardSize - 1) / 2);
        fiveStars[3] = new Point(chessBoardSize - 4, 3);
        fiveStars[4] = new Point(chessBoardSize - 4, chessBoardSize - 4);

        initBoardPoints();
    }


    // init board points with coordiantes
    public void initBoardPoints() {

        // init all points
        for (int i = 0; i < chessBoardSize; i++) {
            boardPoints[i] = new Point[chessBoardSize];
            for (int j = 0; j < chessBoardSize; j++) {
                int x, y;
                x = startx1 + sideLength * j;
                y = starty1 + sideLength * i;
                boardPoints[i][j] = new Point(x, y);
            }
        }

    }


    /**
     * check if the position is taken, or it is right turn.
     *
     * @param chessman
     */
    public void placeChessman(Chessman chessman) {
        // record the on board chessmans
        if (boardrecorder.isPlaceAvailable(chessman)) {
            if (chessman.color == IPlayer.BLACK) {
                if (boardrecorder.blackCount == boardrecorder.whiteckCount) {
                    boardrecorder.blackCount++;
                    boardrecorder.push(chessman);
                    chessboard[chessman.x][chessman.y] = chessman.color;

                    // TODO
                    if(context instanceof ChessBoardView){
                        ((ChessBoardView)context).resourceManager.mediaPlayerPush.start();
                    }


                    checkIsGameOver();
                }
            } else if (chessman.color == IPlayer.WHITE) {
                if (boardrecorder.blackCount == (boardrecorder.whiteckCount + 1)) {
                    boardrecorder.whiteckCount++;
                    boardrecorder.push(chessman);
                    chessboard[chessman.x][chessman.y] = chessman.color;

                    if(context instanceof ChessBoardView){
                        ((ChessBoardView)context).resourceManager.mediaPlayerPush.start();
                    }


                    checkIsGameOver();
                }
            }

        }
    }

    public boolean checkIsGameOver() {
        Evaluator evaluator = new Evaluator(chessboard);
        boolean gameOver = evaluator.isGameOver(playerA);

        if (gameOver) {
            isGmaeOver = true;
            if (playerA.getWinstate() == IPlayer.WIN) {
                showGameOver("Computer win!", IPlayer.X);
            } else if (playerA.getWinstate() == IPlayer.LOST) {
                showGameOver("You win!", IPlayer.O);
            } else {
                showGameOver("Draw !", IPlayer.X);
            }
        } else {
            isGmaeOver = false;
        }
        return isGmaeOver;
    }


    public void showGameOver(String msg, int winPayer) {
        if(context instanceof ChessBoardView){
            ((ChessBoardView)context).showGameOver(msg, winPayer);
        }
    }


    public Point findLocation(float x, float y) {
        Point location = new Point(-1, -1);
        // the array ndex and the real XY are inverse.
        location.y = Math.round((x - startx1) / sideLength);
        location.x = Math.round((y - starty1) / sideLength);
        return location;
    }


    public Boolean drawRedCircle(Canvas canvas, Paint paint, Chessman latestChessman) {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Point p = boardPoints[latestChessman.x][latestChessman.y];

        canvas.drawCircle(p.x, p.y, sideLength / 8, paint);
        return true;
    }


}