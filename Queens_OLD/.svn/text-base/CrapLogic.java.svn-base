
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.JFactory;

public class CrapLogic {

    private int n = 0;
    private int[][] board;
    private int NODES = 2000000;
    private BDDFactory B;
    private BDD[][] vars;
    private BDD queens;

    public CrapLogic() {}

    public void initializeGame(int size) {
        this.n = size;
        this.board = new int[n][n];

        // initialize factory and diagram
        B = JFactory.init(NODES, NODES / 10);
        queens = B.one();

        // create one variable for each position
        B.setVarNum(n * n);
        vars = new BDD[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                vars[i][j] = B.ithVar(i * n + j);

        // setup rules, see
        // http://www.decisionoptimizationlab.dk/Joomla1515/images/stories/IAIP/literature/a97.pdf

        // queen on each row
        for (int i = 0; i < n; i++) {
            BDD e = B.zero();
            for (int j = 0; j < n; j++)
                e.orWith(vars[i][j].id());
            queens.andWith(e);
        }

        int k, l;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {

                // column
                for (l = 0; l < n; l++)
                    if (l != j)
                        queens.andWith(vars[i][j].and(vars[i][l]).not());

                // row
                for (k = 0; k < n; k++)
                    if (k != i)
                        queens.andWith(vars[i][j].and(vars[k][j]).not());

                // diagonal 1
                for (k = 0; k < n; k++) {
                    int ll = j + k - i;
                    if (ll >= 0 && ll < n && k != i)
                        queens.andWith(vars[i][j].and(vars[k][ll]).not());
                }

                // diagonal 2
                for (k = 0; k < n; k++) {
                    int ll = j + i - k;
                    if (ll >= 0 && ll < n && k != i)
                        queens.andWith(vars[i][j].and(vars[k][ll]).not());
                }
            }
    }

    public int[][] getGameBoard() {
        return board;
    }

    public boolean insertQueen(int column, int row) {

        if (board[column][row] == -1 || board[column][row] == 1)
            return true;

        board[column][row] = 1;
        queens = queens.and(vars[column][row]);

        // search board for forbidden positions
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (queens.and(vars[i][j]).isZero())
                    board[i][j] = -1;

        // reveal board if only one solution is remaining
        if (queens.satCount() < 2)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (board[i][j] == 0)
                        board[i][j] = 1;

        return true;
    }
}