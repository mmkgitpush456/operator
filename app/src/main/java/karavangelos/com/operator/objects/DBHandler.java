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
    private static final int DATABASE_VERSION = 1;                                                  //db version
    private static final String DATABASE_NAME = "scores.db";                                        //db name
    private static final String TABLE_SCORES = "SCORES";                                            //table name

    private static final String COLUMN_ID = "ID";                                                   //column id
    private static final String COLUMN_SCORE = "SCORE";                                             //column score
    private static final String COLUMN_LEVEL = "LEVEL";                                             //column level
    private static final String COLUMN_DATE = "DATE";                                               //column date

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
                + COLUMN_SCORE + " VARCHAR(10) NOT NULL, "
                + COLUMN_LEVEL + " VARCHAR(10) NOT NULL, "
                + COLUMN_DATE + " VARCHAR(25) NOT NULL);";

        db.execSQL(CREATE_REPORTS_TABLE);
    }


    //database is upgraded when the application version upgrades and is put on google play
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);

    }


    //dummy method that puts information in the DB
    public void insertRowIntoDB(String score, String level) {

       // String score = String.valueOf(player.getScore() );
       // String level = String.valueOf(player.getLevel() );

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();


        try {

            values.put(COLUMN_SCORE, score);
            values.put(COLUMN_LEVEL, level);
            values.put(COLUMN_DATE, "07/29/15");

            db.insert(TABLE_SCORES, null, values);


        } catch (Exception e) {

            Log.e(TAG, "error occured at " + e);

        }
    }


    //method that pulls a full row of information based on the specific archived date.
    //The size of the array and its contents are determined based on the currently selected device or devicePosition
    public void getStatsFromSelectedDate(String selectedDate){

        //Log.d(TAG, "date is " + archivedDate + " and position is " + devicePosition);

        String[] archivedStats = null;
        SQLiteDatabase db = getReadableDatabase();

        String QUERY = "SELECT * FROM " + TABLE_SCORES + " WHERE " + COLUMN_DATE + " ='"+selectedDate+"';";

        // Log.d(TAG, "query is " + QUERY);

        Cursor cursor = db.rawQuery(QUERY, null);

        if(cursor != null){

            cursor.moveToFirst();
        }

        //try block used for error checking
        try {

            Log.d(TAG, "The size of the cursor is " + cursor.getColumnCount());

            archivedStats = new String[3];


            archivedStats[0] = cursor.getString(1);         //metric weight
            archivedStats[1] = cursor.getString(2);         //body fat
            archivedStats[2] = cursor.getString(3);         //calories


            Log.d(TAG, "SCORE: " + archivedStats[0] + "\n"
            + "LEVEL: " + archivedStats[1] + "\n"
            + "DATE: " + archivedStats[2] );
                //end try block
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
          //  return null;
            }//end catch block for imperial statistics





      //  cursor.close();
        cursor = null;
      //  db.close();
        db = null;
      //  return archivedStats;

    }//end get StatsFromArchivedDate





}