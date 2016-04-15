package play.wm.ljb.com.wmiplay.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ljb on 2015/11/20.
 */
public class WmiDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wmi_store.db";
    private static final int DB_VERSION = 1;

    //创建用户表
    private static final java.lang.String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + UserTableContract.UserTable.TABLE_NAME + " (" +
                    UserTableContract.UserTable._ID + " integer not null primary key autoincrement," +
                    UserTableContract.UserTable.USER_NAME + " text not null," +
                    UserTableContract.UserTable.USER_ID + " integer not null," +
                    UserTableContract.UserTable.NICKNAME + " text," +
                    UserTableContract.UserTable.HEAD_URL + " text," +
                    UserTableContract.UserTable.GENDER + " int not null," +
                    UserTableContract.UserTable.USER_TYPE + " int not null," +
                    UserTableContract.UserTable.ONLINE + " int not null"+
                    ");";
    //删除用户表
    private static final String SQL_DELETE_TABLE_USER = "DROP TABLE IF EXISTS" + UserTableContract.UserTable.TABLE_NAME;

    public WmiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_USER);
        onCreate(db);
    }
}
