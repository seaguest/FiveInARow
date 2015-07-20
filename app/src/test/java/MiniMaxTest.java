import android.graphics.Point;

import com.seaguest.game.Square;
import com.seaguest.game.minimax.Config;
import com.seaguest.game.minimax.MiniMaxBoard;

import org.junit.Test;

/**
 * Created by tyhk1987 on 2015/7/18.
 */

public class MiniMaxTest {

    public void testMinimax1() {

        char[][] array = {
                {'*', '*', '*', '*', '*', '*', '*'},
                {'*', 'O', 'X', '*', '*', '*', '*'},
                {'*', '*', 'O', '*', 'X', '*', '*'},
                {'*', '*', 'X', 'O', '*', '*', '*'},
                {'*', '*', '*', '*', 'O', '*', '*'},
                {'*', '*', '*', 'X', '*', 'O', '*'},
                {'*', '*', '*', '*', '*', '*', '*'},
        };

        MiniMaxBoard board = new MiniMaxBoard();
        board.setBoard(array);

        System.out.println("----------------------");
        board.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Config.X);
        Point bestMove = board.returnBestMove();

        System.out.println("bestMove is:" + bestMove.x + "-" + bestMove.y);
    }

    public void testMinimax2(){
        char[][] array ={
                {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'*', 'X', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'*', 'O', '*', '*', '*', 'X', '*', '*', '*', '*'},
                {'*', 'O', 'O', 'X', '*', '*', '*', '*', 'X', '*'},
                {'*', 'O', 'O', '*', 'X', 'O', '*', '*', '*', '*'},
                {'*', 'O', '*', '*', '*', 'O', '*', 'X', '*', '*'},
                {'*', 'X', '*', '*', '*', 'O', '*', '*', '*', '*'},
                {'*', '*', 'X', '*', '*', 'O', '*', '*', '*', '*'},
                {'*', '*', '*', '*', '*', '*', 'X', '*', '*', '*'},
                {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'}
        };

        MiniMaxBoard board = new MiniMaxBoard();
        board.setBoard(array);

        System.out.println("----------------------");
        board.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Config.X);
        Point bestMove = board.returnBestMove();

        System.out.println("bestMove is:" + bestMove.x + "-" + bestMove.y);

    }

    @Test
    public void testBoardView(){
        TestBoardView board = new TestBoardView();

        Square square = board.findCoverSquare();

        System.out.println("9999999999999999Start:x="+square.start.x+"-y:"+square.start.y+"-size:"+square.width);

    }
}
