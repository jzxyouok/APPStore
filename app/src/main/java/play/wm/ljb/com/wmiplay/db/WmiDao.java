package play.wm.ljb.com.wmiplay.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import play.wm.ljb.com.wmiplay.moudle.User;
import play.wm.ljb.com.wmiplay.utils.XgoLog;

/**
 * Created by Ljb on 2015/11/20.
 */
public class WmiDao {

    private WmiDbHelper mDbHelper;
    private Context mContext;

    public WmiDao(Context context) {
        this.mContext = context;
        this.mDbHelper = new WmiDbHelper(context);
    }

    public long addUser(User user){
        XgoLog.d("添加新用户");
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId;
        ContentValues values = new ContentValues();
        values.put(UserTableContract.UserTable.USER_ID , user.getUserId());
        values.put(UserTableContract.UserTable.HEAD_URL, user.getHeadUrl());
        values.put(UserTableContract.UserTable.USER_NAME, user.getUserName());
        values.put(UserTableContract.UserTable.NICKNAME, user.getNickname());
        values.put(UserTableContract.UserTable.GENDER, user.getGender());
        values.put(UserTableContract.UserTable.USER_TYPE , user.getUserType());
        values.put(UserTableContract.UserTable.ONLINE, user.getOnLine());
        newRowId = db.insert(UserTableContract.UserTable.TABLE_NAME, null, values);
        db.close();

        //新用户登录，通知监听器
        mContext.getContentResolver().notifyChange(UserTableContract.URI_USER_ONLINE_CHANGED, null);
        return newRowId;
    }


    public User findUser(int userId){
        XgoLog.d("ID查找用户：：" + userId);
        User user = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(UserTableContract.UserTable.TABLE_NAME, null, UserTableContract.UserTable.USER_ID+"=?;",
                new String[]{String.valueOf(userId)}, null, null, null);

        if(c.moveToNext()){
            int uid = c.getInt(c.getColumnIndex(UserTableContract.UserTable.USER_ID));
            String name = c.getString(c.getColumnIndex(UserTableContract.UserTable.USER_NAME));
            String nickname = c.getString(c.getColumnIndex(UserTableContract.UserTable.NICKNAME));
            int gender = c.getInt(c.getColumnIndex(UserTableContract.UserTable.GENDER));
            String headUrl = c.getString(c.getColumnIndex(UserTableContract.UserTable.HEAD_URL));
            int userType = c.getInt(c.getColumnIndex(UserTableContract.UserTable.USER_TYPE));
            int onLine = c.getInt(c.getColumnIndex(UserTableContract.UserTable.ONLINE));
            user = new User(uid , name,nickname, gender ,userType,headUrl , onLine);
        }

        c.close();
        db.close();
        return user;
    }

    public int updateUuser(User user){
        XgoLog.d("修改用户");
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTableContract.UserTable.USER_ID, user.getUserId());
        values.put(UserTableContract.UserTable.USER_NAME, user.getUserName());
        values.put(UserTableContract.UserTable.NICKNAME, user.getNickname());
        values.put(UserTableContract.UserTable.GENDER, user.getGender());
        values.put(UserTableContract.UserTable.HEAD_URL, user.getHeadUrl());
        values.put(UserTableContract.UserTable.USER_TYPE, user.getUserType());
        values.put(UserTableContract.UserTable.ONLINE, user.getOnLine());
        int update = db.update(UserTableContract.UserTable.TABLE_NAME, values, UserTableContract.UserTable.USER_ID + "=?", new String[]{String.valueOf(user.getUserId())});
        db.close();
        //用户状态发生改变，通知监听器
        mContext.getContentResolver().notifyChange(UserTableContract.URI_USER_ONLINE_CHANGED, null);
        return update;
    }

    /*public User findOnLineUser(){
        User user = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(UserTableContract.UserTable.TABLE_NAME, null, UserTableContract.UserTable.ONLINE+"=?",
                new String[]{String.valueOf(User.ON_LINE)}, null, null, null);

        if(c.moveToNext()){
            int uid = c.getInt(c.getColumnIndex(UserTableContract.UserTable.USER_ID));
            String name = c.getString(c.getColumnIndex(UserTableContract.UserTable.USER_NAME));
            String nickname = c.getString(c.getColumnIndex(UserTableContract.UserTable.NICKNAME));
            int gender = c.getInt(c.getColumnIndex(UserTableContract.UserTable.GENDER));
            String headUrl = c.getString(c.getColumnIndex(UserTableContract.UserTable.HEAD_URL));
            int userType = c.getInt(c.getColumnIndex(UserTableContract.UserTable.USER_TYPE));
            int onLine = c.getInt(c.getColumnIndex(UserTableContract.UserTable.ONLINE));
            user = new User(uid , name,nickname, gender ,userType,headUrl , onLine);
        }

        c.close();
        db.close();
        return user;
    }*/

    public int delCollect(int userId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int n = db.delete(UserTableContract.UserTable.TABLE_NAME, UserTableContract.UserTable.USER_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
        return n;
    }

}
