package play.wm.ljb.com.wmiplay.holder;

import android.app.Activity;
import android.view.View;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;

/**
 * Created by Ljb on 2015/11/23.
 */
public class OtherLoginHolder  extends BaseAHolder<Object>{

    public OtherLoginHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View  view = View.inflate(mActivity , R.layout.holder_other_login , null);
        return view;
    }

    @Override
    protected void initData(Object data) {

    }
}
