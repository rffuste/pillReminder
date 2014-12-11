package rffsystems.es.pillreminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ruben on 07/12/2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_PILLS = "pills";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DOSIS = "dosis";

    private static final String DATABASE_NAME = "pills.sqlite";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    public static final String CREATE_TABLE_PILLS = "create table " + TABLE_PILLS + " ("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NAME + " text not null,"
            + COLUMN_TIME + " text not null,"
            + COLUMN_DOSIS + " int not null);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_PILLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILLS);
        onCreate(db);
    }

}
