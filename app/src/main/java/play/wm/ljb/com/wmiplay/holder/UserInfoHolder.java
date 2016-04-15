package play.wm.ljb.com.wmiplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.UserInfoActivity;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.moudle.User;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.CircleImageView;
import play.wm.ljb.com.wmiplay.view.WhiteBlockDialog;

/**
 * Created by Ljb on 2015/11/24.
 */
public class UserInfoHolder extends BaseAHolder<User> implements View.OnClickListener {

    private CircleImageView mHeadImage;
    private TextView mNicknameTextView, mUserNmaeTextView;
    private BitmapUtils bu;

    public UserInfoHolder(UserInfoActivity activity) {
        super(activity);
        bu = new BitmapUtils(activity);
        bu.configDefaultLoadingImage(R.drawable.user);
    }


    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_user_info, null);
        mHeadImage = (CircleImageView) view.findViewById(R.id.iv_head_image);
        mNicknameTextView = (TextView) view.findViewById(R.id.tv_nickname);
        mUserNmaeTextView = (TextView) view.findViewById(R.id.tv_username);

        view.findViewById(R.id.rl_head).setOnClickListener(this);
        view.findViewById(R.id.rl_nikename).setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData(User data) {
        if (data != null) {
            String headUrl = data.getHeadUrl();
            if (TextUtils.isEmpty(headUrl)) {
                mHeadImage.setImageResource(R.mipmap.icon);
            } else {
                bu.display(mHeadImage, headUrl);
            }
            mNicknameTextView.setText(data.getNickname());
            mUserNmaeTextView.setText(data.getUserName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head:
                updateHead();
                break;
            case R.id.rl_nikename:
                updateNikename();
                break;
        }
    }

    /**
     * 修改昵称
     */
    private void updateNikename() {
        XgoUIUtils.showToast("修改昵称");
    }

    /**
     * 修改头像
     */
    private void updateHead() {
        new WhiteBlockDialog(mActivity)
                .addItem(mActivity.getString(R.string.take_photo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mActivity instanceof UserInfoActivity) {
                            ((UserInfoActivity) mActivity).takePhoto();
                        }
                    }
                })
                .addItem(mActivity.getString(R.string.photo_lib), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mActivity instanceof UserInfoActivity) {
                            ((UserInfoActivity) mActivity).openPiclib();
                        }
                    }
                })
                .show();
    }
}
