package rffsystems.es.pillreminder;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 07/12/2014.
 */
public class DbManager {

    /*
        This class is our DAO. It maintains the database connection and
        supports adding new comments and fetching all comments.
     */

    // Database fields
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
        DbHelper.COLUMN_ID,
        DbHelper.COLUMN_NAME,
        DbHelper.COLUMN_TIME,
        DbHelper.COLUMN_DOSIS};


    public DbManager(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Pill insertPill(String name, String time, int dosis) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_NAME, name);
        values.put(DbHelper.COLUMN_TIME, time);
        values.put(DbHelper.COLUMN_DOSIS, dosis);

        long insertId = database.insert(DbHelper.TABLE_PILLS, null,
                values);

        Cursor cursor = database.query(DbHelper.TABLE_PILLS,
                allColumns, DbHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Pill newPill = cursorToPill(cursor);
        cursor.close();

        return newPill;
    }

    public void deletePill(Pill comment) {
        long id = comment.getId();
        System.out.println("Pill deleted with id: " + id);
        database.delete(DbHelper.TABLE_PILLS, DbHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Pill> getAllPills() {
        List<Pill> pill_list = new ArrayList<Pill>();

        Cursor cursor = database.query(DbHelper.TABLE_PILLS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pill pill_item = cursorToPill(cursor);
            pill_list.add(pill_item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return pill_list;
    }


    private Pill cursorToPill(Cursor cursor) {
        Pill comment = new Pill();
        comment.setId(cursor.getLong(0));
        comment.setPillName(cursor.getString(1));
        comment.setPillTime(cursor.getString(2));
        comment.setPillDosis(cursor.getInt(3));

        return comment;
    }
}
