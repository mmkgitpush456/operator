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
 * Created by karavangelos on 3/7/15.
 */
public class Operator extends View{

    private static final String TAG = "Operator";
    private Paint operatorPaint;

    private Rect operatorRect;

    private int operatorLeft;
    private int operatorTop;
    private int operatorRight;
    private int operatorBottom;
    private int operatorWidth;
    private int operatorHeight;


    public Operator(Context c, AttributeSet attrs){
        super (c, attrs);

        operatorPaint = new Paint();
        operatorPaint.setStyle(Paint.Style.FILL);
        operatorPaint.setColor(getResources().getColor(R.color.red));

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
    ////////////////////////////////////////////////////////////////////////////////////////////////


    //operator object methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    protected int getStarterPosition(int numMarks, int iterator){

        int starterPosition = 0;

        for(int i = 0; i < numMarks; i++){

            starterPosition += iterator;
        }

        return starterPosition;
    }


    public void drawTheOperator(Canvas canvas){

        operatorRect.set(operatorLeft, operatorTop, operatorRight, operatorBottom);
        canvas.drawRect(operatorRect, operatorPaint);

     //   Log.d(TAG, "operator left is " + operatorLeft);
     //   Log.d(TAG, "operator top is " + operatorTop);
    }


    protected void moveWithTheVerticalBar(int vertBarLeft){

        setOperatorLeft(vertBarLeft);
        setOperatorRight( (operatorLeft + operatorWidth) );
    }

    protected void moveWithHorizontalBar(int horzBarTop){

        setOperatorTop(horzBarTop);
        setOperatorBottom( (operatorTop + operatorHeight) );
    }






}
