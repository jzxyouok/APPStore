package play.wm.ljb.com.wmiplay.holder;

import android.app.Activity;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.LoginActivity;
import play.wm.ljb.com.wmiplay.db.UserTableContract;
import play.wm.ljb.com.wmiplay.db.WmiDao;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.manager.ThreadManager;
import play.wm.ljb.com.wmiplay.moudle.User;
import play.wm.ljb.com.wmiplay.utils.XgoSPUtils;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.spotsdialog.SpotsDialog;

/**
 * Created by Ljb on 2015/11/19.
 */
public class LoginHolder extends BaseAHolder implements View.OnClickListener {
    private ImageView iv_del;
    private EditText ed_username, ed_password;
    private TextView tv_forgot_pwd;
    private Button btn_login;
    private SpotsDialog mLogingDialog;
    private WmiDao dao;

    public LoginHolder(Activity activity) {
        super(activity);
        mLogingDialog = new SpotsDialog(activity ,activity.getString(R.string.loging_text));
        dao = new WmiDao(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_login, null);
        iv_del = (ImageView) view.findViewById(R.id.iv_del);
        ed_password = (EditText) view.findViewById(R.id.et_password);
        ed_username = (EditText) view.findViewById(R.id.et_username);
        tv_forgot_pwd = (TextView) view.findViewById(R.id.tv_forgot_pwd);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        ed_username.addTextChangedListener(new UserNameChangedListener());
        iv_del.setOnClickListener(this);
        tv_forgot_pwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData(Object data) {

    }

    private void changUI() {
        if (!TextUtils.isEmpty(ed_username.getText())) {
            if (iv_del.getVisibility() != View.VISIBLE) {
                iv_del.setVisibility(View.VISIBLE);
            }
        } else {
            iv_del.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del:
                ed_username.setText("");
                break;
            case R.id.tv_forgot_pwd:
                XgoUIUtils.showToast(R.string.forgot_pwd);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {

        final String username = ed_username.getText().toString().trim();
        final String password = ed_password.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            XgoUIUtils.showToast(R.string.username_pwd_null);
            return;
        }

        mLogingDialog.show();
        //模拟登录
        ThreadManager.getDefaultManager().getLongThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String cls = mActivity.getString(R.string.welfare_cls);
                if (cls.equals(username) && cls.equals(password)) {
                    //模拟用户信息
                    User user = new User(10001,  username , "Admin", User.GENDER_MM, User.UserType.TYPE_DEBUG, "" ,User.ON_LINE);
                    User findUser = dao.findUser(user.getUserId());
                    if(findUser!=null){
                        //该用户以前登陆过，刷新用户状态，并保存UserID
                        dao.updateUuser(user);
                    }else {
                        //添加新用户
                        dao.addUser(user);
                    }
                    //保存新的用户ID
                    XgoSPUtils.putInt(UserTableContract.UserTable.USER_ID , user.getUserId());

                    SystemClock.sleep(2000);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginSuccess();
                        }
                    });
                } else {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginFailure();
                        }
                    });
                }
            }
        });
    }

    /**登陆成功的回调*/
    private void loginSuccess() {
        mLogingDialog.dismiss();
        if(mActivity instanceof LoginActivity){
            ((LoginActivity)mActivity).back();
        }else {
            mActivity.finish();
        }
    }

    /**登录失败的回调*/
    private void loginFailure() {
        XgoUIUtils.showToast(R.string.login_error);
    }

    public void hacker() {
        ed_username.setText(R.string.welfare_cls);
        ed_password.setText(R.string.welfare_cls);
    }

    private class UserNameChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changUI();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }
}
