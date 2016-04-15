package play.wm.ljb.com.wmiplay.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import play.wm.ljb.com.wmiplay.XgoApplication;

public class XgoUIUtils {

    /**
     * 全局上下文环境
     *
     * @return
     */
    public static Context getContext() {
        return XgoApplication.getApplication();
    }

    /**
     * 获取屏幕宽度
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static int getScreenWidth() {
        WindowManager wm = ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.x;
        } else {
            Display d = wm.getDefaultDisplay();
            return d.getWidth();
        }
    }

    /**
     * 获取屏幕高度
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static int getScreenHeight() {
        WindowManager wm = ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.y;
        } else {
            Display d = wm.getDefaultDisplay();
            return d.getHeight();
        }
    }

    /**
     * dp转px
     *
     * @param dip
     * @return
     */
    public static int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */

    public static int px2dip(float px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 吐司 String
     *
     * @param msg
     */
    public static void showToast(String msg, int longTimeType) {
        if (longTimeType == Toast.LENGTH_LONG) {
            Toast.makeText(getContext(), msg, longTimeType).show();
        } else {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(int resId) {
        showToast(resId, 0);
    }

    public static void showToast(String msg) {
        showToast(msg, 0);
    }


    /**
     * 吐司 ResourcesId
     *
     * @param resId
     */
    public static void showToast(int resId, int longTimeType) {
        if (longTimeType == Toast.LENGTH_LONG) {
            Toast.makeText(getContext(), getString(resId), longTimeType).show();
        } else {
            Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * getResources
     *
     * @return Resources
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 通过资源id获取对应String数组
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 通过资源id获取对应Int数组
     *
     * @param id
     * @return
     */
    public static int[] getIntegerArray(int id) {
        return getResources().getIntArray(id);
    }

    /**
     * 通过资源id获取对应String
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 通过资源id获取对应颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 保证代码在主线程中运行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isOnMainThread()) {
            runnable.run();
        } else {
            execute(runnable);
        }
    }

    /**
     * 获取主线程中的Handler
     */
    private static Handler getMainHandler() {
        return XgoApplication.getHandler();
    }

    public static void execute(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    public static void executeDelay(Runnable runnable, long delayTime) {
        getMainHandler().postDelayed(runnable, delayTime);
    }

    public static void cancelRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }

    /**
     * 判断程序是否在主线程运行
     */
    private static boolean isOnMainThread() {
        // 首先获取到主线程的tid == 再判断当前线程的tid
        return XgoApplication.getMyTid() == android.os.Process.myTid();
    }


    /**
     * 获取View的缩略图
     *
     * @param view
     * @return ImageView
     */
    public static ImageView getDrawingCacheView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(getContext());
        iv.setImageBitmap(cache);
        return iv;
    }

    // hide nav bar
    public static void hideNavbar(Activity act) {
        if (Build.VERSION.SDK_INT >= 14) {
            act.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    /**
     * 创建Lable shape资源
     */
    public static GradientDrawable createLableShape(int color) {
        GradientDrawable drawableShape  = new GradientDrawable();
        drawableShape.setColor(color);
        drawableShape.setCornerRadius(dip2px(5));
        drawableShape.setStroke(1,Color.TRANSPARENT);
        return drawableShape;
    }

    public static StateListDrawable crealeLableSelector(Drawable normalDrawable ,Drawable pressedDrawable){
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        selector.addState(new int[]{}, normalDrawable);
        return  selector;
    }
}
