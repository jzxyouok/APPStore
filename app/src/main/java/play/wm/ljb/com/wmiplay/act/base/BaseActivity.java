package play.wm.ljb.com.wmiplay.act.base;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.manager.SystemBarTintManager;

/**
 * Created by Ljb on 2015/11/19.
 */
public class BaseActivity extends FragmentActivity {

    public static  final String CLOSE_RECEIVER = "play.wm.ljb.com.wmiplay.act.CLOSE_RECEIVER";
    private CloseBroadcastReceiver mClose;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        immersion();
        registerCloseReceiver();
    }

    private void immersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.indicatorcolor);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterCloseReceiver();
    }

    private void unregisterCloseReceiver() {
        if(mClose!=null) {
            unregisterReceiver(mClose);
        }
    }

    private void registerCloseReceiver() {
        mClose = new CloseBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.xgo.cms.act.CLOSE");
        registerReceiver(mClose, filter);
    }


    class CloseBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isFinishing()) {
                finish();
            }
        }
    }
}
