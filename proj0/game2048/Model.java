package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @roark_alex
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */

    // t是side视角下board.tile(col, row)在North视角的tile
   private int nextPosi(int icol, int irow, Tile t){
        Tile temp;
        int col = icol;
        int row = irow + 1;
        int len = board.size();

        while(row < len){
            temp = board.tile(col, row);
            if(temp == null || temp.value() == t.value()) {
                row++;
            } else {
                break;
            }
        }
        return row - 1;
    }

    private int nextNullPosi(int icol, int irow, Tile t){
        Tile temp;
        int col = icol;
        int row = irow + 1;
        int len = board.size();

        while(row < len){
            temp = board.tile(col, row);
            if(temp == null) {
                row++;
            } else {
                break;
            }
        }
        return row - 1;
    }

    private boolean tiltCol(int icol){
        // 初始化标记符
        // mergeFlag==1标记上一行做了合并，mergeFlag==0标记上一行没有合并
        boolean changed = false;
        boolean mergeFlag = false;

        // 遍历side方向上，icol列中的行，方向从远到近
        int len = board.size();
        for(int irow = len - 2; irow >= 0; irow--){
            Tile t = board.tile(icol, irow);
            if(t == null) {
                continue;
            } else {
                int targetRow;
                // 如果上一行的tile有合并操作，那么这一行只移动不合并
                // 如果上一行的tile没有合并操作，那么这一行可以合并
                if(!mergeFlag) {
                    // 找到tile的下一个位置
                    targetRow = nextPosi(icol, irow, t);
                    // 执行merge，或者move，或者什么都不做，如果merge，设置flag
                    mergeFlag = board.move(icol, targetRow, t);
                    // 如果merge，改变score
                    if(mergeFlag)
                        score += board.tile(icol, targetRow).value();
                }else{
                    // 找到tile的下一个位置
                    targetRow = nextNullPosi(icol, irow, t);
                    // move，或者什么都不做
                    board.move(icol, targetRow, t);
                    // 设置flag
                    mergeFlag = false;
                }
                // 提示changed
                if(targetRow != irow)
                    changed = true;
            }
        }
        return changed;
    }

    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        // 设定方向
        board.setViewingPerspective(side);

        int len = board.size();


        // 遍历side方向上的列
        for(int icol = 0; icol < len; icol++){
            boolean changedCol = tiltCol(icol);
            if(changedCol)
                changed = true;
        }

        // 还原方向设定
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int len = b.size();
        for (int i = 0; i < len; i++)
            for(int j = 0; j < len; j++){
                Tile t = b.tile(i, j);
                if(t == null)
                    return true;
            }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int len = b.size();
        for(int i = 0; i< len; i++)
            for(int  j=0; j< len ;j++){
                Tile t = b.tile(i, j);
                if(t != null && t.value() == MAX_PIECE)
                    return true;
            }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */

    private static boolean adjacentSameTileExist(Board b){
        int len = b.size();
        for(int i = 0; i < len; i++)
            for(int j = 0; j < len; j++){
                Tile t = b.tile(j, i);
                if(j + 1 < len){
                    Tile belowt = b.tile(j+1, i);
                    if(t.value() == belowt.value())
                     return true;
                }
                if(i + 1 < len) {
                    Tile leftt = b.tile(j, i + 1);
                    if (t.value() == leftt.value())
                        return true;
                }
            }
        return false;
    }

    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if(emptySpaceExists(b)){
            return true;
        }else if(adjacentSameTileExist(b)){
            return true;
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
