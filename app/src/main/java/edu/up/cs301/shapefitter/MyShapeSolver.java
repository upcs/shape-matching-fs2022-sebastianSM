package edu.up.cs301.shapefitter;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author Sebastian Santos-Mendoza
 * @version 9/16/2022
 */

public class MyShapeSolver extends ShapeSolver {

    /**
     * Creates a solver for a particular problem.
     *
     * @param parmShape the shape to fit
     * @param parmWorld the world to fit it into
     * @param acc to send notification messages to
     */
    public MyShapeSolver(boolean[][] parmShape, boolean[][] parmWorld, ShapeSolutionAcceptor acc) {
        // invoke superclass constructor
        super(parmShape, parmWorld, acc);
    }

    /**
     * Solves the problem by finding a fit, if possible. The last call to display tells where
     * the fit is. If there is no fit, no call to display should be made--alternatively, a call to
     * undisplay can be made.
     */
    public void solve() {

        //initialization
        undisplay();

        //along each row and column of world array, compare shape array with world array
        for(int scanRow = 0; scanRow <= world.length - shape.length; scanRow++) {
            for(int scanCol = 0; scanCol <= world.length - shape.length; scanCol++) {

                //initialize boolean match to true
                boolean standardMatch = true;
                boolean CCwiseMatch = true;
                boolean clockwiseMatch = true;
                boolean oneEightyMatch = true;
                boolean mirrorMatch = true;
                boolean clockMirrorMatch = true;
                boolean oneEightyMirrorMatch = true;
                boolean CCMirror = true;

                //iterate through each square of shape array and respective world array slice
                for (int row = 0; row < shape.length; row++) {
                    for (int col = 0; col < shape[0].length; col++) {

                        //for each of the orientations, looks for any discrepancies that nullify if the shape is found in
                        // the respective orientation
                        for (Orientation or : Orientation.values()){
                            if(or == Orientation.ROTATE_NONE){
                                if (shape[row][col] == true && world[scanRow + row][scanCol + col] == false) {
                                    standardMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_COUNTERCLOCKWISE) {
                                if (shape[col][shape.length - row - 1] == true && world[scanRow + row][scanCol + col] == false) {
                                    CCwiseMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_CLOCKWISE){
                                if (shape[shape.length - col - 1][row] == true && world[scanRow + row][scanCol + col] == false) {
                                    clockwiseMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_180){
                                if (shape[shape.length - row - 1][shape.length - col - 1] == true && world[scanRow + row][scanCol + col] == false) {
                                    oneEightyMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_NONE_REV){
                                if (shape[row][shape.length - col - 1] == true && world[scanRow + row][scanCol + col] == false) {
                                    mirrorMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_CLOCKWISE_REV){
                                if (shape[col][row] == true && world[scanRow + row][scanCol + col] == false) {
                                    clockMirrorMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_180_REV){
                                if (shape[shape.length - row - 1][col] == true && world[scanRow + row][scanCol + col] == false) {
                                    oneEightyMirrorMatch = false;
                                }
                            } else if(or == Orientation.ROTATE_COUNTERCLOCKWISE_REV){
                                if (shape[shape.length - col - 1][shape.length - row - 1] == true && world[scanRow + row][scanCol + col] == false) {
                                    CCMirror = false;
                                }
                            }
                        }
                    }
                }

                //displays depending on type of match
                if(standardMatch) {
                    display(scanRow, scanCol, Orientation.ROTATE_NONE);
                    return;
                } else if(CCwiseMatch) {
                    display(scanRow, scanCol, Orientation.ROTATE_COUNTERCLOCKWISE);
                    return;
                } else if(clockwiseMatch){
                    display(scanRow, scanCol, Orientation.ROTATE_CLOCKWISE);
                    return;
                } else if(oneEightyMatch){
                    display(scanRow, scanCol, Orientation.ROTATE_180);
                    return;
                } else if(mirrorMatch){
                    display(scanRow, scanCol, Orientation.ROTATE_NONE_REV);
                    return;
                } else if(clockMirrorMatch){
                    display(scanRow, scanCol, Orientation.ROTATE_CLOCKWISE_REV);
                    return;
                } else if(oneEightyMirrorMatch){
                    display(scanRow, scanCol, Orientation.ROTATE_180_REV);
                    return;
                } else if(CCMirror){
                    display(scanRow, scanCol, Orientation.ROTATE_COUNTERCLOCKWISE_REV);
                    return;
                }
            }
        }

    }

    /**
     * Checks if the shape is well-formed: has at least one square, and has all squares connected.
     *
     * @return whether the shape is well-formed
     */
    //this array is for keeping track of what square has been inspected already with check()
    boolean[][] checked = new boolean[shape.length][shape.length];

    public boolean check() {
        //initialized such that no modification indicates failure
        int sr = -1;
        int sc = -1;

        //count for how many true squares are in shape
        int count = 0;

        //iterate through shape, assigning sr and sc with location of last true square
        //also counts number of true squares
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                if(shape[row][col] == true){

                    //sr and sc are set to rightmost, then bottom most true square
                    sr = row;
                    sc = col;

                    count++;
                }
            }
        }

        //debug
        //Log.d("","" + sr + "" + sc);

        //if < 0, no true squares found
        if(sr < 0 || sc < 0){
            return false;
        }

        //initialize checker array to be all false
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                checked[row][col] = false;
            }
        }

        //represents how many squares are connected using recursive inspect method
        int conn = inspect(sr, sc);

        //compares connected with how many true squares in shape to see if all squares are part of connected
        if(conn == count){
            return true;
        }

        return false;
    }

    //checks how many squares are adjacent to a given square
    private int inspect(int r, int c){
        checked[r][c] = true;
        int count = 1;

        //recursive inspection

        //check above
        if (r - 1 >= 0 && !checked[r - 1][c]){
            if(shape[r - 1][c] == true) {
                count += inspect(r - 1, c);
            }
        }

        //check left
        if (c - 1 >= 0 && !checked[r][c - 1]){
            if(shape[r][c - 1] == true) {
                count += inspect(r, c - 1);;
            }
        }

        //check below
        if (r + 1 < shape.length && !checked[r + 1][c]){
            if(shape[r + 1][c] == true) {
                count += inspect(r + 1, c);
            }
        }

        //check right
        if (c + 1 < shape.length && !checked[r][c + 1]){
            if(shape[r][c + 1] == true) {
                count += inspect(r, c + 1);;
            }
        }

        return count;
    }
}
