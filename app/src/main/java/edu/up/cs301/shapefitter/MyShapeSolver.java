package edu.up.cs301.shapefitter;

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



        //along each row, compare shape array with world array
        for(int scanRow = 0; scanRow <= world.length - shape.length; scanRow++) {
            //initialize boolean match to true
            boolean match = true;

            //iterate through each square of shape array and respective world array slice
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[0].length; col++) {

                    //debug
                    //display(scanRow + row, col, Orientation.ROTATE_NONE);

                    //if no match is found for ANY singular place, entire shape comparison is voided
                    if (shape[row][col] != world[scanRow + row][col]) {
                        match = false;
                        //break, match is false so no need to keep comparing
                        break;
                    }
                }

                if(!match){
                    //break if not matching (computation optimization)
                    break;
                }
            }

            //displays depending on match
            if (match) {
                display(scanRow, 0, Orientation.ROTATE_NONE);
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
