package karavangelos.com.operator.objects;

import android.content.Context;

import java.util.ArrayList;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 8/3/15.
 */
public class HighScoresModel {

    private static final String TAG = "HighScoresAdapter";
    private static HighScoresModel sScoresModel;
    private ArrayList<HighScore> scoresList;
    private Context context;


    public HighScoresModel(Context context){

        scoresList = new ArrayList<>();
        this.context = context;
        setTheScoresFromTheDB();
    }

    public static HighScoresModel newInstance(Context context){

        if(sScoresModel == null) {

            sScoresModel = new HighScoresModel(context);


        }

        return sScoresModel;
    }

    public void addToTheList(HighScore highScore){

        scoresList.add(highScore);
    }


    public void setDummyScores(){

        for(int i = 0; i < 50; i++){

            HighScore highScore = new HighScore();
            highScore.setScore("" + (i + 1));
            addToTheList(highScore);
        }
    }

    public ArrayList<HighScore> getTheScores(){

        return scoresList;
    }

    public void setTheScoresFromTheDB(){

        DBHandler dbHandler = new DBHandler(context);

        HighScore[] scores = dbHandler.getStatsFromSelectedDate();
        int[] gameColors = context.getResources().getIntArray(R.array.easy_colors_array);
        int numberOfColors = gameColors.length;
        int colorIterator = 0;

        scoresList.clear();

        for(int i = 0; i < scores.length; i++){

            scores[i].setColor(gameColors[colorIterator]);
            scoresList.add(scores[i]);
            colorIterator++;

            if(colorIterator == numberOfColors){
                colorIterator = 0;
            }
        }

    }


}
