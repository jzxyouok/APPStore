package play.wm.ljb.com.wmiplay.utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import play.wm.ljb.com.wmiplay.XgoApplication;

public class XgoSPUtils {

    public static final String SP_NAME = "sp_wmi_sotre";

    private static SharedPreferences getInstance() {
        return XgoApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        getInstance().edit().putString(key, value).commit();
    }

    /**
     * SP中读取key对应的boolean型value
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getInstance().getString(key, "");
    }

    public static void putBoolean(String key, boolean value) {
        getInstance().edit().putBoolean(key, value).commit();
    }

    /**
     * SP中读取key对应的boolean型value
     *
     * @param key
     * @return 没有返回false
     */
    public static boolean getBoolean(String key) {
        return getInstance().getBoolean(key, false);
    }

    public static void putInt(String key, int value) {
        getInstance().edit().putInt(key, value).commit();
    }

    /**
     * SP中读取key对应的整形value
     *
     * @param key
     * @return 没有返回-1
     */
    public static int getInt(String key) {
        return getInstance().getInt(key, -1);
    }

    public static void putFloat(String key, float value) {
        getInstance().edit().putFloat(key, value).commit();
    }

    /**
     * SP中读取key对应的整形value
     *
     * @param key
     * @return 没有返回-1
     */
    public static float getFloat(String key) {
        return getInstance().getFloat(key, -1);
    }

    public static void putStringSet(String key, Set<String> setValue) {
        getInstance().edit().putStringSet(key, setValue);
    }

    /**
     * SP中读取key对应的String类型set集合
     *
     * @param key
     * @return 没有返回null
     */
    public static Set<String> getStringSet(String key) {
        return getInstance().getStringSet(key, null);
    }
}
