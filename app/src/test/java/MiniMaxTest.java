import com.seaguest.game.chessboard.Point;
import com.seaguest.game.player.IPlayer;
import com.seaguest.game.searchengine.MoveAndScore;
import com.seaguest.game.searchengine.QuickSort;
import com.seaguest.game.searchengine.SearchEngine;

import org.junit.Test;

/**
 * Created by tyhk1987 on 2015/7/18.
 */

public class MiniMaxTest {

    @Test
    public void testQuickSort() {
        MoveAndScore[] moves = new MoveAndScore[10];
        moves[0] = new MoveAndScore(10, new Point(-1, -1));
        moves[1] = new MoveAndScore(1, new Point(-1, -1));
        moves[2] = new MoveAndScore(5, new Point(-1, -1));
        moves[3] = new MoveAndScore(4, new Point(-1, -1));
        moves[4] = new MoveAndScore(8, new Point(-1, -1));
        moves[5] = new MoveAndScore(0, null);
        moves[6] = new MoveAndScore(0, null);
        moves[7] = new MoveAndScore(0, null);
        moves[8] = new MoveAndScore(0, null);
        moves[9] = new MoveAndScore(0, null);

        System.out.println("endIndex:" + QuickSort.findEndIndex(moves));


        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));

        for(MoveAndScore move: moves){
            System.out.println("Score:" + move.score);
        }

    }

    @Test
    public void testMinimax1() {

        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0},
        };

        SearchEngine board = new SearchEngine(array);
        board.displayBoard();
        board.searchBestMove();

        System.out.println("----------------------");
        MoveAndScore bestMove = board.bestMoveAndScore;
        board.makeMove(bestMove, IPlayer.X);
        board.displayBoard();


        System.out.println("bestMove is:" + bestMove.position.x + "-" + bestMove.position.y);
    }

//    @Test
    public void testMinimax2() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}
        };

        SearchEngine board = new SearchEngine(array);
        board.displayBoard();
        board.searchBestMove();

        System.out.println("----------------------");
        MoveAndScore bestMove = board.bestMoveAndScore;
        board.makeMove(bestMove, IPlayer.X);
        board.displayBoard();


        System.out.println("bestMove is:" + bestMove.position.x + "-" + bestMove.position.y);

    }

}
