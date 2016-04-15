package play.wm.ljb.com.wmiplay.holder;

import android.view.View;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.UserInfoActivity;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;

/**
 * Created by Ljb on 2015/11/24.
 */
public class BindHolder extends BaseAHolder<Object> {
    public BindHolder(UserInfoActivity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_bind, null);
        return view;
    }

    @Override
    protected void initData(Object data) {

    }
}
