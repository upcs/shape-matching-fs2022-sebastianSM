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

        // ****dummied up****
        // always "finds" a solution at row 3, column 4, with a 90-degree clockwise orientation
        //display(3, 4, Orientation.ROTATE_CLOCKWISE);


        boolean match = true;


        //iterate through entire shape array
        for(int row = 0; row < shape.length; row++){
            for(int col = 0; col < shape[0].length; col++){

                //if no match is found for ANY singular place, entire shape comparison is voided
                if(shape[row][col] != world[row][col]){
                    match = false;
                }
            }
        }

        //displays or undisplays depending on match
        if(match){
            display(0, 0, Orientation.ROTATE_NONE);
        } else {
            undisplay();
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
