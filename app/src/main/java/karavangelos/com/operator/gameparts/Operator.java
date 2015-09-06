package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import karavangelos.com.operator.R;
import karavangelos.com.operator.objects.Player;

/**
 * Created by karavangelos on 3/7/15.
 */
//the Operator object is the rectangle that always stays between the intersection of the
    //player vertical and horizontal bars.  It interacts with the slider objects that move across the
    //canvas and consequences vary depending on the colors of the objects.
public class Operator extends View{

    private static final String TAG = "Operator";                                                   //logging tag
    private Paint operatorPaint;

    private Rect operatorRect;                                                                      //rectangle object

    private int operatorLeft;                                                                       //operator's left coordinate
    private int operatorTop;                                                                        //operator's top coordinate
    private int operatorRight;                                                                      //operator's right coordinate
    private int operatorBottom;                                                                     //operator's bottom coordinate
    private int operatorWidth;                                                                      //width of the operator
    private int operatorHeight;                                                                     //height of the operator
    private int paintKey;                                                                           //key that tells what color the operator object will be.

    private Context context;
    private Player player;
    private String difficultyStatus;
    private int totalNumberColors;

    //constructor sets the color of the operator and declares the operator rectangle
    public Operator(Context c, AttributeSet attrs){
        super (c, attrs);

        context = c;
        player = Player.newInstance(context);
        difficultyStatus = player.getDifficultyStatus();
        totalNumberColors = getTotalPaintNumber();

        paintKey = getRandomNumber(1, totalNumberColors);

        operatorPaint = new Paint();
        setOperatorPaintColor(paintKey);

        operatorRect = new Rect();
    }


    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getOperatorLeft() {
        return operatorLeft;
    }

    public void setOperatorLeft(int operatorLeft) {
        this.operatorLeft = operatorLeft;
    }

    public int getOperatorTop() {
        return operatorTop;
    }

    public void setOperatorTop(int operatorTop) {
        this.operatorTop = operatorTop;
    }

    public int getOperatorRight() {
        return operatorRight;
    }

    public void setOperatorRight(int operatorRight) {
        this.operatorRight = operatorRight;
    }

    public int getOperatorBottom() {
        return operatorBottom;
    }

    public void setOperatorBottom(int operatorBottom) {
        this.operatorBottom = operatorBottom;
    }

    public int getOperatorWidth() {
        return operatorWidth;
    }

    public void setOperatorWidth(int operatorWidth) {
        this.operatorWidth = operatorWidth;
    }

    public int getOperatorHeight() {
        return operatorHeight;
    }

    public void setOperatorHeight(int operatorHeight) {
        this.operatorHeight = operatorHeight;
    }

    public int getPaintKey() {
        return paintKey;
    }

    public void setPaintKey(int paintKey) {
        this.paintKey = paintKey;
    }

    public Paint getOperatorPaint() {
        return operatorPaint;
    }

    public void setOperatorPaint(Paint operatorPaint) {
        this.operatorPaint = operatorPaint;
    }

    public int getTotalNumberColors() {
        return totalNumberColors;
    }

    public void setTotalNumberColors(int totalNumberColors) {
        this.totalNumberColors = totalNumberColors;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //operator object methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //sets the starting position on the operator object.  This is determined by the number of marks
    //argument and the iterator, or distance between the lines on the canvas grid.  This method is used
    //in the player bars class on both of the setStartingBar methods.
    protected int getStarterPosition(int numMarks, int iterator){

        int starterPosition = 0;

        for(int i = 0; i < numMarks; i++){

            starterPosition += iterator;
        }

        return starterPosition;
    }


    //method used to obtain a random number between the min and max arguments.  Min being the lowest
    //number that can be obtained, and max being the highest number that can be obtained.  This method
    //is used for a number of contributing factors across the Slider class.
    protected int getRandomNumber(int min, int max){

        Random random = new Random();

        int randomNumber = random.nextInt(max) + min;

        //   Log.d(TAG, "The Random number between " + min + " and " + max + " is " + randomNumber);

        return randomNumber;
    }


    public int getTotalPaintNumber(){

        int totalPaintNumber = 0;

        if(difficultyStatus.equals(context.getString(R.string.difficulty_easy))){

            totalPaintNumber = 4;
        }

        if(difficultyStatus.equals(context.getString(R.string.difficulty_medium))){

            totalPaintNumber = 6;
        }

        if(difficultyStatus.equals(context.getString(R.string.difficulty_hard))){

            totalPaintNumber = 8;
        }


        return totalPaintNumber;
    }

    //convenience method that sets the paint color of the operator object based on the number key
    //passed in from the argument.
    //1 FOR RED
    //2 FOR GREEN
    //3 FOR BLUE
    //4 FOR PINK
    protected void setOperatorPaintColor(int paintColorCode){

        switch (paintColorCode){

            case 1:

                operatorPaint.setColor(getResources().getColor(R.color.red));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;


            case 2:

                operatorPaint.setColor(getResources().getColor(R.color.beige));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;



            case 3:

                operatorPaint.setColor(getResources().getColor(R.color.aqua));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;


            case 4:

                operatorPaint.setColor(getResources().getColor(R.color.pink));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;

            case 5:

                operatorPaint.setColor(getResources().getColor(R.color.dark_green));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;

            case 6:

                operatorPaint.setColor(getResources().getColor(R.color.orange));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;

            case 7:

                operatorPaint.setColor(getResources().getColor(R.color.purple));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;

            case 8:

                operatorPaint.setColor(getResources().getColor(R.color.sand));
                operatorPaint.setStyle(Paint.Style.FILL);
                break;

        }
    }


    //sets the operator rectangle's current coordinates and then draws it on the canvas.  Used in the
    //draw the bars method on the player bars class
    public void drawTheOperator(Canvas canvas){

        operatorRect.set(operatorLeft, operatorTop, operatorRight, operatorBottom);
        canvas.drawRect(operatorRect, operatorPaint);

    }


    //updates the operator rectangle's left and right positions so that it moves in unison
    //with the vertical bar when the vertical bar has been called.
    protected void moveWithTheVerticalBar(int vertBarRight, int vertBarLeft){

        setOperatorRight(vertBarRight);
        setOperatorLeft(vertBarLeft);
    }

    //updates the operator rectangle's top and bottom positions so that it moves in unison
    //with the horizontal bar when the horizontal bar is in motion.
    protected void moveWithHorizontalBar(int horzBarBottom, int horzBarTop){

        setOperatorBottom(horzBarBottom);
        setOperatorTop(horzBarTop);
    }

}
