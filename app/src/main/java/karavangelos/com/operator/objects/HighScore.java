package karavangelos.com.operator.objects;

/**
 * Created by karavangelos on 8/3/15.
 */

//The HighScore class is a convenience class that assists with the maintenance and presentation
    //of player high scores.  It is used primarily for the list view that displays the top high
    //scores from the current time.
public class HighScore {

    private static final String TAG = "HighScore";


    private String score;                                                                           //score from the particular game
    private String level;                                                                           //level reached when game is over
    private String dateOfScore;                                                                     //date that the player played the game


    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public String getDateOfScore() {
        return dateOfScore;
    }

    public void setDateOfScore(String dateOfScore) {
        this.dateOfScore = dateOfScore;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}
