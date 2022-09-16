package edu.up.cs301.shapefitter;

import android.media.AudioRecord;
import android.util.Log;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author Sebastian Santos-Mendoza
 * @version **** put completion date here ****
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

        //along each row and column, compare shape array with world array
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

                        //debug
                        //display(scanRow + row, scanCol + col, Orientation.ROTATE_NONE);

                        //if no match is found for ANY singular place, entire shape comparison is voided
                        //No rotation


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

                //displays depending on match


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
                /*
                else if(clockwiseRotationMatch){
                    display(scanRow, scanCol, Orientation.ROTATE_COUNTERCLOCKWISE);
                }
                */
            }
        }

    }

    /**
     * Checks if the shape is well-formed: has at least one square, and has all squares connected.
     *
     * @return whether the shape is well-formed
     */
    public boolean check() {
        return Math.random() < 0.5;
    }

}
