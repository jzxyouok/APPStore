package play.wm.ljb.com.wmiplay.act;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;
import play.wm.ljb.com.wmiplay.fragment.ContentFragment;
import play.wm.ljb.com.wmiplay.fragment.LeftDrawerFragment;
import play.wm.ljb.com.wmiplay.manager.SystemBarTintManager;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.ShareDialog;


public class MainActivity extends ActionBarActivity {

    private static final String TAG_LEFT_MENU = "main_left_drawer";
    private static final String TAG_CONTENT = "main_content";
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager fm;
    private ContentFragment mContentFragment;
    private LeftDrawerFragment mLeftDrawerFragment;
    private ShareDialog mShareDialog;
    private FrameLayout fl_all;

    private long down_back1;
    private long down_time;
    private SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            init();
        initView();
        initActionBar();
        initFragment();
    }

    /**
     * activity相关的其他初始化数据
     */
    private void init() {
        //修改状态栏背景色
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


    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        fl_all = (FrameLayout) findViewById(R.id.fl_all);
//        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//        fl_all.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
    }


    /**
     * 初始化ActionBar
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initActionBar() {
        mActionBar = getSupportActionBar();
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);

        mToggle.syncState();
        mDrawerLayout.setDrawerListener(mToggle);

        showOverflowButton();
    }

    private void initFragment() {
        // 获取Fragment管理员
        fm = getSupportFragmentManager();
        // 由于变更UI可能是复杂操作,底层用事务控制
        FragmentTransaction ft = fm.beginTransaction();
        mContentFragment = new ContentFragment();
        mLeftDrawerFragment = new LeftDrawerFragment();
        // 替换原始布局中的FrameLayout
        ft.replace(R.id.fl_left_drawer, mLeftDrawerFragment, TAG_LEFT_MENU);
        ft.replace(R.id.fl_content, mContentFragment, TAG_CONTENT);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                menu_search();
                break;
            case R.id.action_share:
                menu_shareInfo();
                break;
        }
        return mToggle.onOptionsItemSelected(item) | super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

   /*
        appcompat-v7:22.x 以后的包不在调用 ， 转移到onPrepareOptionsMenu()方法中，强制显示图标
   @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        XgoLog.e("onMenuOpened");
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    */

    /**
     * 覆写手机back键功能
     */
    @Override
    public void onBackPressed() {
        if (down_back1 == 0) {
            down_back1 = System.currentTimeMillis();
            XgoUIUtils.showToast(R.string.exit_go_out, 0);
        } else {
            long down_back2 = System.currentTimeMillis();
            down_time = down_back2 - down_back1;
            if (down_time < 2000) {
                exit();
            } else {
                down_back1 = System.currentTimeMillis();
                XgoUIUtils.showToast(R.string.exit_go_out, 0);
            }
        }
    }

    /**
     * 设置当前显示的Tab页
     */
    public void setCurrentPage(int tabId) {
        if (mContentFragment != null) {
            mContentFragment.setCurrentPage(tabId);
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }


    /**
     * 使ActionBar始终显示overflowButton
     */
    private void showOverflowButton() {
        ViewConfiguration configuration = ViewConfiguration.get(this);
        try {
            Field sHasPermanentMenuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            sHasPermanentMenuKey.setAccessible(true);
            sHasPermanentMenuKey.setBoolean(configuration, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索
     */
    private void menu_search() {
        startActivity(new Intent(this, SearchActivity.class));
        overridePendingTransition(R.anim.scale_in, R.anim.not_thing_300);
    }

    /**
     * 分享给小伙伴
     */
    private void menu_shareInfo() {
        new ShareDialog(this)
                .setShareData(getString(R.string.share_title)
                        , getString(R.string.share_content)
                        , R.mipmap.icon
                        , getString(R.string.share_url))
                .show();
    }

    /**
     * 退出应用
     */
    public void exit() {
        sendBroadcast(new Intent(BaseActivity.CLOSE_RECEIVER));
        finish();
    }
}
