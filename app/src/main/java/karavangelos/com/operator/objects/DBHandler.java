package karavangelos.com.operator.objects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karavangelos on 7/29/15.
 */
public class DBHandler extends SQLiteOpenHelper{

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
                + COLUMN_SCORE + "VARCHAR(10) NOT NULL, "
                + COLUMN_LEVEL + "VARCHAR(10) NOT NULL"
                + COLUMN_DATE + " VARCHAR(25) NOT NULL);";

        db.execSQL(CREATE_REPORTS_TABLE);
    }


    //database is upgraded when the application version upgrades and is put on google play
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);

    }


    


}
