package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/21/15.
 */
public class PlayerBars extends View {

    private static final String TAG = "PlayerBars";

    private Operator operator;

    private Context context;

    private Paint barPaint;


    private Rect horizontalBar;
    private Rect verticalBar;

    private int horzBarLeft;                                                                        //coordinates that maintain the horizontal gray bar
    public int horzBarTop;
    private int horzBarRight;
    public int horzBarBottom;
    private int horzBarDistanceTop;
    private int horzBarDistanceBottom;
    public int horzBarWidth;

    private int vertBarLeft;                                                                        //coordinates that maintain the vertical gray bar
    private int vertBarTop;
    private int vertBarRight;
    private int vertBarBottom;
    private int vertTouchDistanceLeft;
    private int vertTouchDistanceRight;
    private int vertBarWidth;

    private boolean starterBarsAreSet;                                                              //flag that determines if the player bars have been initialized or not.
    private boolean touchedTheVerticalBar;
    private boolean touchedTheHorizontalBar;



    //constructor sets the vertical and horizontal bars
    //and also sets flags that determine starting position
    //and movement.
    public PlayerBars(Context c, AttributeSet attrs){
        super(c, attrs);
        context = c;

        starterBarsAreSet = false;
        touchedTheVerticalBar = false;


        horizontalBar = new Rect();
        verticalBar = new Rect();

        operator = new Operator(c, attrs);

    }



    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isTouchedTheVerticalBar() {
        return touchedTheVerticalBar;
    }

    public void setTouchedTheVerticalBar(boolean touchedTheVerticalBar) {
        this.touchedTheVerticalBar = touchedTheVerticalBar;
    }

    public boolean isTouchedTheHorizontalBar() {
        return touchedTheHorizontalBar;
    }

    public void setTouchedTheHorizontalBar(boolean touchedTheHorizontalBar) {
        this.touchedTheHorizontalBar = touchedTheHorizontalBar;
    }

    public int getVertBarWidth() {
        return vertBarWidth;
    }

    public void setVertBarWidth(int vertBarWidth) {
        this.vertBarWidth = vertBarWidth;
    }

    public int getHorzBarWidth() {
        return horzBarWidth;
    }

    public void setHorzBarWidth(int horzBarWidth) {
        this.horzBarWidth = horzBarWidth;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //player bar class methods
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //dimensions of both the horizontal and vertical bars are set within the 2 methods
    //below.  member variables obtain measurements to allow flexibility of canvas
    //on any size device.
    private void setStartHorizontalBar(Canvas canvas, int gridBreaks){

        int startingIncrementer = getStartingIncrementor(gridBreaks);
        int canvasHeight = canvas.getHeight();
        int iterator = getIterator(canvasHeight, gridBreaks);
        int starterPosition = getStarterPosition(startingIncrementer, iterator);
        int right = canvas.getWidth();
        int bottom = (starterPosition + iterator) ;

        horzBarLeft = 0;
        horzBarTop = starterPosition;
        horzBarRight = right;
        horzBarBottom = bottom;
        horzBarWidth = getBarWidth(horzBarBottom, horzBarTop);

        operator.setOperatorHeight(horzBarWidth);
        operator.setOperatorTop(operator.getStarterPosition(5, horzBarWidth) );
        operator.setOperatorBottom( (operator.getOperatorTop() + horzBarWidth ) );


        Log.d(TAG, "canvas width = " + canvas.getWidth() );
        Log.d(TAG, "horizontal bar right = " + horzBarRight );

    }


    private void setStartingVerticalBar(Canvas canvas, int gridBreaks){

        int startingIncrementer = getStartingIncrementor(gridBreaks);
        int canvasWidth = canvas.getWidth();
        int iterator = getIterator(canvasWidth, gridBreaks);
        int starterPosition = getStarterPosition(startingIncrementer, iterator);
        int bottom = canvas.getHeight();
        int right = (starterPosition + iterator);

        vertBarLeft = starterPosition;
        vertBarTop = 0;
        vertBarRight = right;
        vertBarBottom = bottom;
        vertBarWidth = getBarWidth(vertBarRight, vertBarLeft);

        operator.setOperatorWidth(vertBarWidth);
        operator.setOperatorLeft(operator.getStarterPosition(3, vertBarWidth) );
        operator.setOperatorRight( (operator.getOperatorLeft() + vertBarWidth) );

    }


    private int getIterator(int widthOrHeight, int gridBreaks){

        int iterator = 0;
        int lWidthOrHeight = widthOrHeight;

        while ( (lWidthOrHeight % gridBreaks) != 0 ){

            lWidthOrHeight --;

        }

        iterator = (lWidthOrHeight / gridBreaks);

        return iterator;
    }


    //helper method that assists with initializing the horizontal
    //and vertical bars to be drawn at the center of the screen to
    //start the game by telling how many paces to the left
    //or bottom to move before initializing bar positions
    private int getStartingIncrementor(int gridBreaks){

        int startingIncrementer = 0;

        if( (gridBreaks % 2) == 0 ){

            startingIncrementer = (gridBreaks / 2);

        } else {

            startingIncrementer = ( (gridBreaks - 1) / 2 );

        }
        return startingIncrementer;

    }


    //returns the coordinate to set the left coordinate for the vertical bar
    //and the top coordinate for the horizontal bar,  Sets for both bars
    //to be initialized in the center sections of the canvas to start the game.
    private int getStarterPosition(int startingIncrementer, int iterator){

        int starterPosition = 0;

        for(int i = 0; i < startingIncrementer; i++) {

            starterPosition += iterator;

        }

        return starterPosition;

    }

    //sets the bars at their corresponding center position to start the game and then flips the
    //member flag to true to ensure the method is only run once.  Used in the onDraw method of the CanvasView
    protected void setUserBarStartingCoordinates(Canvas canvas, int horizontalGridBreaks, int verticalGridBreaks){

        if(!starterBarsAreSet) {

            setStartingVerticalBar(canvas, verticalGridBreaks);
            setStartHorizontalBar(canvas, horizontalGridBreaks);

            starterBarsAreSet = true;
        }
    }

    //draws the bars at their updated positions.  Used in the onDraw override of the canvasView
    protected void drawTheBars(Canvas canvas){

        horizontalBar.set(horzBarLeft, horzBarTop, horzBarRight, horzBarBottom );
        canvas.drawRect(horizontalBar, barPaint);

        verticalBar.set(vertBarLeft, vertBarTop, vertBarRight , vertBarBottom);
        canvas.drawRect(verticalBar, barPaint);

        operator.drawTheOperator(canvas);
    }

    //sets the member paint variable that colors in the player bars
    protected void setBarPaint(){

        barPaint = new Paint();
        barPaint.setColor(getResources().getColor(R.color.light_gray));
        barPaint.setStyle(Paint.Style.FILL);

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////

    //helper method that returns a flag if the player has touched within the boundaries
    //of one of the player bars.  Used within numerous methods across the player bars class.
    private boolean touchedTheCrossing(int position, int leftOrTop, int rightOrBottom){

        boolean touchedVerticalCrossing = false;

        if ( (position >= leftOrTop) && (position <= rightOrBottom)  ){

            touchedVerticalCrossing = true;
        }

        return touchedVerticalCrossing;
    }


    //returns a true or false flag depending on whether the player touches the exact intersections
    //between the vertical and horizontal bars.  Used in the onTouch method (ACTION_DOWN) in the
    //CanvasView
    public boolean touchedTheExactCenter(int xPos, int yPos){

        boolean touchedVerticalCrossing = touchedTheCrossing(xPos, vertBarLeft, vertBarRight);
        boolean touchedHorizontalCrossing = touchedTheCrossing(yPos, horzBarTop, horzBarBottom);
        boolean touchedTbeExactCenter = false;

        if(  touchedVerticalCrossing && touchedHorizontalCrossing){

            touchedTbeExactCenter = true;
        }

        return touchedTbeExactCenter;
    }


    //if the user has touched within the boundaries of the vertical bar,
    //the member variables that assist with drawing update movements on the
    //vertical bar are assembled.
    public void touchedTheVerticalBar (int xPos){

        boolean touchedVerticalCrossing = touchedTheCrossing(xPos, vertBarLeft, vertBarRight);

        if(touchedVerticalCrossing){

            setTouchedTheVerticalBar(true);
            vertTouchDistanceLeft = (xPos - vertBarLeft);
            vertTouchDistanceRight = (vertBarRight - xPos);
        }
    }


    //if the user has touched within the boundaries of the horizontal bar,
    //the member variables that assist with drawing update movements on the
    //horizontal bar are assembled.
    public void touchedTheHorizontalBar(int yPos){

        boolean touchedHorizontalCrossing = touchedTheCrossing(yPos, horzBarTop, horzBarBottom);

        if ( touchedHorizontalCrossing  ) {

            setTouchedTheHorizontalBar(true);
            horzBarDistanceTop = (yPos - horzBarTop);
            horzBarDistanceBottom = (horzBarBottom - yPos);
        }
    }

    //the 2 methods below assist with drawing the player bars if the
    //player has successfully touched the bars at the right location
    //and decides to drag them.  Used in the case ACTION_MOVE section
    //of the onTouch in the Canvas View

    //the horizontal bar movement requires an extra bit of calculation when it
    //hits the bottom of the canvas since the width of the bar does not
    //match up entirely evenly with the final horizontal row of blocks.
    public void moveTheHorizontalBar(int yPos, int horizontalGridBreaks){

        if(touchedTheHorizontalBar){

            horzBarTop = (yPos - horzBarDistanceTop);
            horzBarBottom = (yPos + horzBarDistanceBottom);

          if(horzBarTop < vertBarTop){

              horzBarTop = vertBarTop;
              horzBarBottom = horzBarWidth;

          }

          if(horzBarBottom > vertBarBottom){

              horzBarBottom = vertBarBottom;
              int horizontalBuffer = (horzBarWidth * horizontalGridBreaks);
              int remainingSpace = (horzBarBottom - horizontalBuffer);

              horzBarTop = (horzBarBottom - horzBarWidth) - remainingSpace;

              operator.setOperatorTop(horzBarTop);
              operator.setOperatorBottom( (operator.getOperatorTop() + operator.getOperatorHeight()) );

          }

            operator.setOperatorTop(horzBarTop);
            operator.setOperatorBottom( (operator.getOperatorTop() + operator.getOperatorHeight()) );

        }
    }

    public void moveTheVerticalBar(int xPos){


        if(touchedTheVerticalBar) {

            vertBarLeft = (xPos - vertTouchDistanceLeft);
            vertBarRight = (xPos + vertTouchDistanceRight);

            if(vertBarLeft < horzBarLeft){

                vertBarLeft = horzBarLeft;
                vertBarRight = vertBarWidth;
            }

            if(vertBarRight > horzBarRight){

                vertBarRight = horzBarRight;
                vertBarLeft = (vertBarRight - vertBarWidth);

            }


            operator.setOperatorLeft(vertBarLeft);
            operator.setOperatorRight( (operator.getOperatorLeft() + operator.getOperatorWidth()) );

        }
    }







    //helper method that gets the width of both the horizontal and vertical bars.
    //used when configuring the sizes of both the horizontal and vertical bars at run time.
    protected int getBarWidth(int rightOrBottom, int leftOrTop){

        return (rightOrBottom - leftOrTop);

    }


    //the logic below produces the actions of the vertical bar when the player releases their
    //finger from its hold.  All helper methods are explained in detail.
    protected void moveVerticalBarToRowActionUp(){

        int leftIteratorPosition = checkWhereTheVerticalBarIs();

        int rightIteratorPosition = (leftIteratorPosition + vertBarWidth);
        int distanceFromRight = (rightIteratorPosition - vertBarRight);
        int divide = (int) (vertBarWidth / 2);

        determineVerticalAlignmentOnRelease(distanceFromRight, divide);
        operator.moveWithTheVerticalBar(vertBarRight, vertBarLeft);
    }

    //helper methods that checks where the vertical bar's location is on the canvas after
    //the player has released their movement from it.
    private int checkWhereTheVerticalBarIs(){

        int leftIteratorPosition = vertBarWidth;

        while(leftIteratorPosition < vertBarLeft){

            leftIteratorPosition += vertBarWidth;

        }

        return leftIteratorPosition;
    }


    //checks whether the vertical bar should be shifted to the left or the right depending on
    //which set of vertical grid lines it is closest to.  The divide is half the distance of the
    //vertical bar as determined from the host method.  used in moveVerticalBarToRowActionUp
    private void determineVerticalAlignmentOnRelease(int distanceFromRight, int divide){

        int distanceToGo = 0;

        if(distanceFromRight > divide){

            distanceToGo = (vertBarWidth - distanceFromRight);
            alignVerticalBarWithGridLines(distanceToGo, getResources().getString(R.string.left));

        } else {

            distanceToGo = distanceFromRight;
            alignVerticalBarWithGridLines(distanceToGo, getResources().getString(R.string.right));
        }
    }


    //method that does all of the dirty work.  First checks if the vertical line is at the right end
    //of the canvas.  If it is, then the distance to go is set to zero and thus, nothing essentialy happens.
    //if the distance is greater than 0, then the vertical bar is shifted to either the left or the right
    //depending on which grid line it is closest to.  There is an additional conditional within the
    //right conditional logic that checks if the vertical bar is supposed to settle into the final (right)
    //column.  If it is, then some extra measurements are made to ensure that it fits within the
    //final column of the canvas.
    private void alignVerticalBarWithGridLines(int distanceToGo, String leftOrRight){

        int checkRightDistance = (horzBarRight - vertBarRight);

        if(vertBarRight == horzBarRight){

            distanceToGo = 0;
        }


        while(distanceToGo > 0){

            if(leftOrRight.equalsIgnoreCase(getResources().getString(R.string.left) ) ) {

                vertBarLeft -- ;
                vertBarRight -- ;

            }


            if(leftOrRight.equalsIgnoreCase(getResources().getString(R.string.right) ) ) {

                vertBarLeft ++;
                vertBarRight ++;

                if(checkRightDistance < vertBarWidth){

                   vertBarRight = horzBarRight;
                   vertBarLeft = (vertBarRight - vertBarWidth);
                   break;

                }
            }

            distanceToGo --;
        }
    }






    //settle down for this set.  The following logic moves the horizontal bar between the nearest
    //set of horizontal bars after they have released their finger hold on it.  Each method is explained
    //in detail as to it compliments the process.
    protected void moveHorizontalBarToRowActionUp(int horizontalGridBreaks){

        int topIteratorPosition = checkWhereHorizontalBarIs();

        int bottomIteratorPosition = (topIteratorPosition + horzBarWidth);
        int distanceFromBottom = (bottomIteratorPosition - horzBarBottom);
        int divide = (int) (horzBarWidth / 2);

        determineHorizontalAlignmentOnRelease(distanceFromBottom, divide, horizontalGridBreaks);

       // operator.setOperatorTop(horzBarTop);
       // operator.setOperatorBottom(horzBarBottom);
        operator.moveWithHorizontalBar(horzBarBottom, horzBarTop);

    }


    //this method returns an integer value that helps find where the horizontal bar is on the screen
    //It is used to help determine whether the bar should shift upwards or downwards depending on
    //which distance is closer.
    private int checkWhereHorizontalBarIs(){

        int topIteratorPosition = horzBarWidth;

        while(topIteratorPosition < horzBarTop){

            topIteratorPosition += horzBarWidth;

        }
        return topIteratorPosition;

    }


    //Delegates whether to push the horizontal bar upwards or downwards.  This is done using the divide
    //variable.  The divide is half of the size of the horizontal bar width.  Thus, it determines
    //whether the horizontal bar should travel up or down by measuring itself against the distanceFromBottom
    //variable, which is pulled in from the main host method (moveHorizontalBarToRowActionUp)
    private void determineHorizontalAlignmentOnRelease(int distanceFromBottom, int divide, int horizontalGridBreaks){

        int distanceToGo = 0;

        if(distanceFromBottom > divide){

            distanceToGo = (horzBarWidth - distanceFromBottom);
            alignHorizontalBarWithGridLines(distanceToGo, getResources().getString(R.string.top), horizontalGridBreaks);

        } else {

            distanceToGo = distanceFromBottom;
            alignHorizontalBarWithGridLines(distanceToGo, getResources().getString(R.string.bottom), horizontalGridBreaks);
        }

    }


    //moves the horizontal bar up or down depending on whether it's closer to the nearest horizontal
    //grid line above or below it.  This is configured in the makeTheAppropriateAdjustmentsOnTheHorizontalBar
    //method and counts down the distance to go on the positioning after every iteration to prevent an
    //infinite loop.  The method at the bottom aligns the horizontal bar with the bottom if it released within
    //the majority of the bottom row.
    private void alignHorizontalBarWithGridLines(int distanceToGo, String topOrBottom, int horizontalGridBreaks){

        int nextToLastHorizontalBarPos = (horzBarWidth * (horizontalGridBreaks - 1) );

        while(distanceToGo > 0){

            makeTheAppropriateAdjustmentsOnTheHorizontalBar(topOrBottom);
            distanceToGo --;
        }

        adjustHorizontalBarToTheBottomIfReleasedThere(nextToLastHorizontalBarPos, topOrBottom);
    }


    //convenience method that makes contains 2 helper methods shown below.
    private void makeTheAppropriateAdjustmentsOnTheHorizontalBar(String topOrBottom){

            resizeTheHorizontalBarIfOff();
            moveTheHorizontalBarIntoPosition(topOrBottom);

    }


    //helper method that adjusts the size of the horizontal bar so that it fits correctly within
    //the grid lines.  This method is here in case there is movement from the bottom row since the
    //bottom row is slightly larger than every other row due to calculations with the canvas height.
    private void resizeTheHorizontalBarIfOff(){

        if( ( (horzBarBottom - horzBarTop) > horzBarWidth) ){

            horzBarTop = horzBarBottom - horzBarWidth;

        }
    }

    //pushes the horizontal bar up or down depending on whether it's closer to the nearest top or bottom
    //gridline.  The top or bottom string variable is determined in the determineHorizontalAlignmentOnRelease
    //listed above.
    private void moveTheHorizontalBarIntoPosition(String topOrBottom){

        if(topOrBottom.equalsIgnoreCase(getResources().getString(R.string.top))) {

            horzBarTop -- ;
            horzBarBottom -- ;
        }

        if(topOrBottom.equalsIgnoreCase(getResources().getString(R.string.bottom))){

            horzBarTop ++;
            horzBarBottom ++;
        }

    }

    //aligns the horizontal bar with the bottom row.  This method is only used in the instance that the horizontal
    //bar is supposed to fit at the very bottom of the canvas.
    private void adjustHorizontalBarToTheBottomIfReleasedThere(int nextToLastHorizontalBarPos, String topOrBottom){

        if( (horzBarBottom > nextToLastHorizontalBarPos) &&  ( topOrBottom.equalsIgnoreCase(getResources().getString(R.string.bottom) ) ) ) {

            horzBarTop = nextToLastHorizontalBarPos;
            horzBarBottom = vertBarBottom;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    protected int obtainOperatorPosition(String whichCoordinate){

        int whichPosition = 0;

        if(whichCoordinate.equalsIgnoreCase(context.getString(R.string.left)) ){

            whichPosition = operator.getOperatorLeft();
        }

        if(whichCoordinate.equalsIgnoreCase(context.getString(R.string.top)) ){

            whichPosition = operator.getOperatorTop();
        }

        if(whichCoordinate.equalsIgnoreCase(context.getString(R.string.right)) ){

            whichPosition = operator.getOperatorRight();
        }

        if(whichCoordinate.equalsIgnoreCase(context.getString(R.string.bottom)) ){

            whichPosition = operator.getOperatorBottom();
        }

        return whichPosition;
    }

}
