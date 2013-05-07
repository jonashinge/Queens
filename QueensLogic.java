/**
 * This class implements the logic behind the BDD for the n-queens problem
 * You should implement all the missing methods
 * 
 * @author Mark Gray (mgra), Jonas Hinge (jhin)
 *
 */

import java.util.*;
import net.sf.javabdd.*;

public class QueensLogic {

    private int size;
    private int[][] board;
    
    BDDFactory fact = JFactory.init(2000000,200000);
    
    
    BDD queensBDD[][]; //Used to hold variables for BDD and to update board.
    BDD mainBDD; //Our Binary Decision Diagram


    public QueensLogic() {
       //constructor
    }

    public void initializeGame(int size) {

        //Initialize variables
        this.size = size;
        this.board = new int[size][size]; //Shadow board used for GameBoard()
        this.fact.setVarNum(size*size);  //One variable per possible position
        this.queensBDD = new BDD[size][size]; 
        mainBDD = fact.one(); //Set mainBDD to true





        
        //Inistialize queensBDD with a variable per position
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++) 
                queensBDD[x][y] = fact.ithVar(x+size*y);

            
//      Iterates through all board variables to setup BDD
            for(int y = 0; y < size ; y++){
                for(int x = 0; x < size ; x++){

        /* 
            A queen can move horizontally, vertically and diagonally. We need to build the BDD
            with rules so that it is not possible to insert a queen if there is already one
            in the same path. 
        */        

        //Row     
            for(int compX = x+1; compX<size; compX++){
                    mainBDD.andWith(queensBDD[x][y].id().imp(queensBDD[compX][y].not().id())); //var(x,y) -> not var(compX,y)
                }
                

        //Columns
                for(int compY = y+1; compY<size; compY++){
                    mainBDD.andWith(queensBDD[x][y].id().imp(queensBDD[x][compY].not().id())); //var(x,y) -> not var(x,compY)
                }


        //Right diagonals

                int startx=0;
                int starty=0;

                if (x<y) {
                    startx = x - x;
                    starty = y - x;
                } else {
                    startx = x - y;
                    starty = y - y;
                }

                while (startx<size && starty<size) {
                    if (startx != x && starty != y)
                        mainBDD.andWith(queensBDD[x][y].imp(queensBDD[startx][starty].not().id())); //var(x,y) -> not var(startx,starty)
                    startx++;
                    starty++;                    
                }

            //Left diagonals
                // X
                startx = x - (size - 1 - y);
                if(startx < 0) startx = 0; // normalize
                // Y
                starty = y + x;
                if(starty > size-1) starty = size-1;
                

                while (startx < size && starty >= 0) {
                    if (startx != x && starty != y)
                        mainBDD.andWith(queensBDD[x][y].imp(queensBDD[startx][starty].not().id())); //var(x,y) -> not var(startx,starty)
                    startx++;
                    starty--;                    
                }






            }
        }

    BDD ColumnBDD;
    for(int x = 0; x < size ; x++){
        ColumnBDD = fact.zero();
        for(int y = 0; y < size ; y++){
            ColumnBDD.xorWith(queensBDD[x][y].id());
        }
        mainBDD.andWith(ColumnBDD);
    }        

    }


    public int[][] getGameBoard() {
        return board;
    }

    public boolean insertQueen(int column, int row) {


        // You cannot put a queen if the space is occupied
        if (board[column][row] == -1 || board[column][row] == 1) {
            return false;
        }
        
        // We update the BDD and the board with the position of the new queen    
        mainBDD.restrictWith(queensBDD[column][row].id());
        board[column][row] = 1;

        //Loop trough our BDD to find positions where inserting a queen will 
        //render the BDD to false.
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                if (mainBDD.restrict(this.queensBDD[x][y].id()).isZero())
                    board[x][y] = -1;
            }
        }
        
        return true;
    }
}

