package karavangelos.com.operator.objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by karavangelos on 7/29/15.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "DBHandler";

    private Context context;

    //set up Database attributes
    private static final int DATABASE_VERSION = 2;                                                  //db version
    private static final String DATABASE_NAME = "scores.db";                                        //db name
    private static final String TABLE_SCORES = "SCORES";                                            //table name

    private static final String COLUMN_ID = "ID";                                                   //column id
    private static final String COLUMN_SCORE = "SCORE";                                             //column score
    private static final String COLUMN_LEVEL = "LEVEL";                                             //column level
    private static final String COLUMN_DATE = "DATE";                                               //column date
    private static final String COLUMN_DIFFICULTY = "DIFFICULTY";                                   //column for difficulty

    //constructor sets the context into play.
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }


    //create the SQLite DB and table on initial application launch
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_REPORTS_TABLE = "CREATE TABLE " + TABLE_SCORES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_SCORE + " INTEGER NOT NULL, "
                + COLUMN_LEVEL + " INTEGER NOT NULL, "
                + COLUMN_DATE + " VARCHAR(25) NOT NULL, "
                + COLUMN_DIFFICULTY + " VARCHAR(25) NOT NULL);";

        db.execSQL(CREATE_REPORTS_TABLE);
    }


    //database is upgraded when the application version upgrades and is put on google play
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
       // onCreate(db);

        Log.d(TAG, "Current version of the DB: " + oldVersion);
        if (oldVersion < 2) {
            final String ALTER_TBL =
                    "ALTER TABLE " + TABLE_SCORES +
                            " ADD COLUMN "+ COLUMN_DIFFICULTY +" VARCHAR(25) NULL;";
            db.execSQL(ALTER_TBL);
        }

    }


    //dummy method that puts information in the DB
    public void insertRowIntoDB(int score, int level) {

       // String score = String.valueOf(player.getScore() );
       // String level = String.valueOf(player.getLevel() );

        Player player = Player.newInstance(this.context);

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();


        try {

            values.put(COLUMN_SCORE, score);
            values.put(COLUMN_LEVEL, level);
            values.put(COLUMN_DATE, player.getTodaysDate() );
            values.put(COLUMN_DIFFICULTY, player.getDifficultyString());
            db.insert(TABLE_SCORES, null, values);

            //Log.d(TAG, "successfully made entry into the database");

        } catch (Exception e) {

            Log.e(TAG, "error occured at " + e);

        }
    }


    //method that pulls a full row of information based on the specific archived date.
    //The size of the array and its contents are determined based on the currently selected device or devicePosition
    public HighScore[] getStatsFromSelectedDate(){

        //Log.d(TAG, "date is " + archivedDate + " and position is " + devicePosition);

        HighScore[] scores = null;
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(TABLE_SCORES, new String[]{"SCORE, LEVEL, DATE, DIFFICULTY"}, null, null, null, null, "SCORE DESC", "15");


        if(cursor != null){

            cursor.moveToFirst();
        }

        //try block used for error checking
        try {

           // Log.d(TAG, "The size of the cursor is " + cursor.getCount());
            scores = new HighScore[cursor.getCount()];

            int i = 0;
            while(!cursor.isAfterLast()){

                HighScore highScore = new HighScore();
                highScore.setScore(cursor.getString(0));
                highScore.setLevel(cursor.getString(1));
                highScore.setDateOfScore(cursor.getString(2));

                if(cursor.getString(3)!= null && !cursor.getString(3).isEmpty() ){

                    highScore.setDifficultyLevel(cursor.getString(3));

                } else {
                    highScore.setDifficultyLevel("Easy");
                }


                scores[i] = highScore;

                Log.d(TAG, "DIFFICULTY # " + (i + 1) + ": " + cursor.getString(3));

               // Log.d(TAG, "Row " + i +": SCORE : " + cursor.getString(0) + ", LEVEL: " + cursor.getString(1) + ", DATE: " + cursor.getString(2) );
                i++;
                cursor.moveToNext();
            }


            } catch(Exception e){

                //if the query returns nothing, it goes to the catch block.
                //the cursor and database are closed out and nothing is returned.
                //utilized in onActivity Result in InputData Fragment for list formatting
                //    Log.e(TAG, "error with query at " + e);

            Log.e(TAG, "error occured at " + e);
            cursor.close();
            cursor = null;
            db.close();
            db = null;
            return null;
            }//end catch block for imperial statistics

        cursor.close();
        cursor = null;
        db.close();
        db = null;
      //  return archivedStats;

        return scores;
    }//end get StatsFromArchivedDate








}