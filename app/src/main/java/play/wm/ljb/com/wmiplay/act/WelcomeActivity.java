package play.wm.ljb.com.wmiplay.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Lei on 2015/11/7.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener {

    private ImageView icon_start;
    private ImageView icon_end;
    private ImageView iv_show;
    private ImageView desc;
    private boolean needGoHome = true;

    private static final int MSG_GO_HOME = -1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GO_HOME:
                    goHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XgoUIUtils.hideNavbar(this);
        initView();
        initAnim();
    }

    private void initView() {
        setContentView(R.layout.activity_welcome);
        icon_start = (ImageView) findViewById(R.id.welcome_icon);
        icon_end = (ImageView) findViewById(R.id.after_icon);
        iv_show = (ImageView) findViewById(R.id.welcome_show);
        desc = (ImageView) findViewById(R.id.welcome_desc);
        iv_show.setOnClickListener(this);
    }

    /**
     * 初始化欢迎界面 渐变 和 移动动画
     */
    private void initAnim() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this,
                R.anim.icon_move);
        loadAnimation.setAnimationListener(new IconDownMoveAnimListener());
        icon_start.setAnimation(loadAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_show:
                openWeb();
                break;
        }
    }

    /**
     * 进入主页
     */
    private void goHome() {
        if(needGoHome) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
    }

    /**
     * 打开广告浏览器
     */
    private void openWeb() {
        needGoHome = false;
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.TAG_URL, "http://m.xgo.com.cn");
        intent.putExtra(WebViewActivity.TAG_TYPE, WebViewActivity.TYPE_FROM_WELCOME);
        startActivity(intent);
        finish();
    }

    /**
     * icon移动动画监听
     */
    class IconDownMoveAnimListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // 右下角描述动画
            Animation desAnim = AnimationUtils.loadAnimation(WelcomeActivity.this,
                    R.anim.des_move);
            desAnim.setAnimationListener(new DesAnimListener());
            desc.setAnimation(desAnim); // 开启右下角动画

            // 开启背景动画
            iv_show.setVisibility(View.VISIBLE);
            iv_show.setAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this,
                    R.anim.image_show));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * 右下角描述信息动画监听
     */
    class DesAnimListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            // icon向左移动动画
            Animation afterIcon = AnimationUtils.loadAnimation(
                    WelcomeActivity.this, R.anim.after_icon_move);
            afterIcon.setAnimationListener(new IconLeftAnimListener());
            icon_end.setAnimation(afterIcon);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            desc.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * 紧接下方Icon左移动画
     */
    class IconLeftAnimListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            icon_start.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            icon_end.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(MSG_GO_HOME, 1500);
        }
    }
}
