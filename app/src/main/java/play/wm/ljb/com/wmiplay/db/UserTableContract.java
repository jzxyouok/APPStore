package play.wm.ljb.com.wmiplay.db;

import android.net.Uri;
import android.provider.BaseColumns;

import play.wm.ljb.com.wmiplay.XgoApplication;

/**
 * Created by Ljb on 2015/11/20.
 */
public class UserTableContract {

    public static final Uri URI_USER = Uri.parse("content://" + XgoApplication.getApplication().getPackageName() + ".dao." + UserTable.TABLE_NAME);

    public static final Uri URI_USER_ONLINE_CHANGED = Uri.withAppendedPath(URI_USER, "online_changed");

    public static abstract class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "tb_user";
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "username";
        public static final String NICKNAME  = "nickname";
        public static final String HEAD_URL = "headurl";
        public static final String USER_TYPE = "usertype";
        public static final String GENDER = "gender";
        public static final String ONLINE  = "online";
    }
}
