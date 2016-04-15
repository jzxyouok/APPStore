package play.wm.ljb.com.wmiplay.holder;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.ShareDialog;

/**
 * Created by Ljb on 2015/11/12.
 */
public class AppDetailBottomHolder extends BaseAHolder<AppInfo> implements View.OnClickListener {
    private Button bottom_favorites, bottom_share, progress_btn;
    private AppInfo mData;

    public AppDetailBottomHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_appdetail_bottom, null);
        bottom_favorites = (Button) view.findViewById(R.id.bottom_favorites);
        bottom_share = (Button) view.findViewById(R.id.bottom_share);
        progress_btn = (Button) view.findViewById(R.id.progress_btn);

        bottom_favorites.setOnClickListener(this);
        bottom_share.setOnClickListener(this);
        bottom_share.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData(AppInfo data) {
        this.mData = data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_favorites:
                XgoUIUtils.showToast("收藏");
                break;
            case R.id.bottom_share:
                shareApp();
                break;
            case R.id.progress_btn:
                XgoUIUtils.showToast("下载");
                break;
        }
    }

    /**
     * 分享
     */
    private void shareApp() {
        new ShareDialog(mActivity)
                .setShareData(mActivity.getString(R.string.app_share_title), mData.getDes(), mData.getIconUrl(), mActivity.getString(R.string.share_url))
                .show();
    }
}
