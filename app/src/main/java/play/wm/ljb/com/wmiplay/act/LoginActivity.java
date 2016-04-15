package play.wm.ljb.com.wmiplay.act;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;
import play.wm.ljb.com.wmiplay.anim.RotateAnimation;
import play.wm.ljb.com.wmiplay.holder.LoginHolder;
import play.wm.ljb.com.wmiplay.holder.OtherLoginHolder;
import play.wm.ljb.com.wmiplay.holder.RegisterHolder;

/**
 * Created by Ljb on 2015/11/19.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout layout_login;
    private FrameLayout layout_other_login;
    private LoginHolder mLoginHolder;
    private ImageView iv_back;
    private ImageView iv_three_point;
    private TextView tv_title;
    private OtherLoginHolder mOtherLoginHolder;

    private boolean isLoginFlag;
    private RegisterHolder mRegisterHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_login);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_three_point = (ImageView) findViewById(R.id.iv_three_point);
        tv_title = (TextView) findViewById(R.id.tv_title);

        layout_login = (FrameLayout) findViewById(R.id.fl_login);
        mLoginHolder = new LoginHolder(this);
        mRegisterHolder = new RegisterHolder(this);
        layout_login.addView(mRegisterHolder.getView());
        layout_login.addView(mLoginHolder.getView());

        layout_other_login = (FrameLayout) findViewById(R.id.fl_other_login);
        mOtherLoginHolder = new OtherLoginHolder(this);
        layout_other_login.addView(mOtherLoginHolder.getView());

        tv_title.setText(R.string.user_login);
//        iv_three_point.setImageResource(R.drawable.register);
        mRegisterHolder.getView().setVisibility(View.GONE);
        isLoginFlag = true; // 首次进入为登录页

        tv_title.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_three_point.setVisibility(View.GONE);
//        iv_three_point.setOnClickListener(this);
    }

    private boolean animing = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                back();
                break;
            case R.id.tv_title:
                if (mLoginHolder != null) {
                    mLoginHolder.hacker();
                }
                break;
            case R.id.iv_three_point:
                if (!animing) {
                    if (isLoginFlag) {
                        applyRotation(0, 0, 90);
                        isLoginFlag = false;
                        iv_three_point.setImageResource(R.drawable.login);
                        tv_title.setText(R.string.user_register);
                    } else {
                        applyRotation(-1, 180, 90);
                        isLoginFlag = true;
                        iv_three_point.setImageResource(R.drawable.register);
                        tv_title.setText(R.string.user_login);
                    }
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        back();
    }

    public void back() {
        finish();
        overridePendingTransition(R.anim.scale_big_in, R.anim.tran_right_out);
    }

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start    the start angle at which the rotation must begin
     * @param end      the end angle of the rotation
     */
    private void applyRotation(int position, float start, float end) {
        // Find the center of the container
        final float centerX = layout_login.getWidth() / 2.0f;
        final float centerY = layout_login.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final RotateAnimation rotation = new RotateAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        layout_login.startAnimation(rotation);
    }

    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        public void onAnimationStart(Animation animation) {
            animing = true;
        }

        public void onAnimationEnd(Animation animation) {
            layout_login.post(new SwapViews(mPosition));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
            final float centerX = layout_login.getWidth() / 2.0f;
            final float centerY = layout_login.getHeight() / 2.0f;
            RotateAnimation rotation;

            if (mPosition > -1) {
                mLoginHolder.getView().setVisibility(View.GONE);
                mRegisterHolder.getView().setVisibility(View.VISIBLE);
                mRegisterHolder.show(RegisterHolder.TYPE_VIEW_WHILE);
                rotation = new RotateAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                mRegisterHolder.getView().setVisibility(View.GONE);
                mLoginHolder.getView().setVisibility(View.VISIBLE);
                rotation = new RotateAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(false);
            rotation.setInterpolator(new DecelerateInterpolator());
            rotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animing = false;
                    if (mPosition > -1) {
                        mRegisterHolder.show(RegisterHolder.TYPE_VIEW_REGISTER);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            layout_login.startAnimation(rotation);
        }
    }
}
