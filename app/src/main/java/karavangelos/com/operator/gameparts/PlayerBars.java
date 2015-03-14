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
      //  Log.d(TAG, "bar width is " + horzBarWidth);

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



    //helper method that returns a flag if the player has touched within the boundaries
    //of one of the player bars.  Used within numerous methods across the player bars class.
    private boolean touchedTheCrossing(int position, int leftOrTop, int rightOrBottom){

        boolean touchedVerticalCrossing = false;

        if ( (position >= leftOrTop) && (position <= rightOrBottom)  ){

            touchedVerticalCrossing = true;
        }

        return touchedVerticalCrossing;
    }


    protected int getBarWidth(int rightOrBottom, int leftOrTop){

        return (rightOrBottom - leftOrTop);

    }

    protected void moveVerticalBarToRowActionUp(){

        int leftIteratorPosition = vertBarWidth;
        leftIteratorPosition = checkWhereTheVerticalBarIs(leftIteratorPosition);

        int rightIteratorPosition = (leftIteratorPosition + vertBarWidth);
        int distanceFromRight = (rightIteratorPosition - vertBarRight);
        int divide = (int) (vertBarWidth / 2);

        determineVerticalAlignmentOnRelease(distanceFromRight, divide);
        operator.moveWithTheVerticalBar(vertBarLeft);
    }


    private int checkWhereTheVerticalBarIs(int leftIteratorPosition){

        while(leftIteratorPosition < vertBarLeft){

            leftIteratorPosition += vertBarWidth;

        }

        return leftIteratorPosition;
    }

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


    protected void moveHorizontalBarToRowActionUp(int horizontalGridBreaks){

        int topIteratorPosition = horzBarWidth;
        topIteratorPosition = checkWhereHorizontalBarIs(topIteratorPosition);


        int rightIteratorPosition = (topIteratorPosition + horzBarWidth);
        int distanceFromBottom = (rightIteratorPosition - horzBarBottom);
        int divide = (int) (horzBarWidth / 2);
        int distanceToGo = 0;

        determineHorizontalAlignmentOnRelease(distanceFromBottom, divide, horizontalGridBreaks);

        if(horzBarBottom < vertBarBottom){

            operator.setOperatorTop(horzBarTop);
            operator.setOperatorBottom(horzBarBottom);

        } else {

            operator.setOperatorTop(horzBarTop);
            operator.setOperatorBottom(horzBarBottom);

        }
    }


    private int checkWhereHorizontalBarIs(int topIteratorPosition){

        while(topIteratorPosition < horzBarTop){

            topIteratorPosition += horzBarWidth;

        }

        return topIteratorPosition;

    }


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


    private void alignHorizontalBarWithGridLines(int distanceToGo, String topOrBottom, int horizontalGridBreaks){

        int checkBottomDistance = (vertBarBottom - horzBarBottom);
        int nextToLastHorizontalBarPos = (horzBarWidth * (horizontalGridBreaks - 1) );

     //   Log.d(TAG, "10th location is " + nextToLastHorizontalBarPos);

        if(horzBarBottom == vertBarBottom){

            distanceToGo = 0;
        }

        while(distanceToGo > 0){

            if(topOrBottom.equalsIgnoreCase(getResources().getString(R.string.top))) {


                if( ( (horzBarBottom - horzBarTop) > horzBarWidth) ){

                    horzBarTop = horzBarBottom - horzBarWidth;
                    Log.d(TAG, "bar re-sizing coming from the TOP");

                }


                horzBarTop -- ;
                horzBarBottom -- ;

            } else if(topOrBottom.equalsIgnoreCase(getResources().getString(R.string.bottom) ) ){

                if( ( (horzBarBottom - horzBarTop) > horzBarWidth) ){

                    horzBarTop = horzBarBottom - horzBarWidth;
                    Log.d(TAG, "bar re-sizing coming from the BOTTOM");

                }


                horzBarTop ++;
                horzBarBottom ++;
            }

            distanceToGo --;
        }

        if( (horzBarBottom > nextToLastHorizontalBarPos) &&  ( topOrBottom.equalsIgnoreCase(getResources().getString(R.string.bottom) ) ) ) {

            Log.d(TAG, "pushing to the final spot");

            horzBarTop = nextToLastHorizontalBarPos;
            horzBarBottom = vertBarBottom;


        }




      //  Log.d(TAG, "horizontal bar top is " + horzBarTop);
        Log.d(TAG, "horizontal bar bottom is " + horzBarBottom);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}
