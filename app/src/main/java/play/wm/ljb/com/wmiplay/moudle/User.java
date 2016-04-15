package play.wm.ljb.com.wmiplay.moudle;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Ljb on 2015/11/20.
 */
public class User {
    public static final int GENDER_GG = 1;
    public static final int GENDER_MM = 2;
    public static final int ON_LINE = 1;
    public static final int OFF_LINE = 0;

    public class UserType {
        public static final int TYPE_DEBUG = -1;
        public static final int TYPE_DEFAULT = 0;
        public static final int TYPE_QQ = 1;
        public static final int TYPE_WX = 2;
        public static final int TYPE_SINA = 3;
   }


    public int userId;
    public String userName;
    public String nickname;
    public int gender;
    public int userType;
    public String headUrl;
    /**
     * 0:未登录
     * 1：已登录
     * */
    public int onLine ;


    public static String getTypeText(int userType) {
        String typeText = XgoUIUtils.getString(R.string.unknow_user);
        switch (userType){
            case UserType.TYPE_DEBUG:
                typeText = XgoUIUtils.getString(R.string.type_user_debug);
                break;
            case UserType.TYPE_DEFAULT:
                typeText = XgoUIUtils.getString(R.string.type_user_wmi);
                break;
            case UserType.TYPE_QQ:
                typeText = XgoUIUtils.getString(R.string.type_user_qq);
                break;
            case UserType.TYPE_WX:
                typeText = XgoUIUtils.getString(R.string.type_user_wx);
                break;
            case UserType.TYPE_SINA:
                typeText = XgoUIUtils.getString(R.string.type_user_sina);
                break;
        }
        return typeText;
    }

    public User() {
    }

    public User(int userId, String userName, String nickname, int gender, int userType, String headUrl, int onLine) {
        this.userId = userId;
        this.userName = userName;
        this.nickname = nickname;
        this.gender = gender;
        this.userType = userType;
        this.headUrl = headUrl;
        this.onLine = onLine;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

}
