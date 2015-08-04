package karavangelos.com.operator.objects;

import java.util.ArrayList;

/**
 * Created by karavangelos on 8/3/15.
 */
public class HighScoresModel {

    private static final String TAG = "HighScoresAdapter";
    private static HighScoresModel sScoresModel;
    private ArrayList<HighScore> scoresList;


    public HighScoresModel(){

        scoresList = new ArrayList<>();
        setDummyScores();
    }

    public static HighScoresModel newInstance(){

        if(sScoresModel == null) {

            sScoresModel = new HighScoresModel();

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


}
