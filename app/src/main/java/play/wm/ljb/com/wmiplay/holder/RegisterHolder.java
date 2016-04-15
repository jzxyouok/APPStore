package play.wm.ljb.com.wmiplay.holder;

import android.app.Activity;
import android.view.View;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.utils.XgoLog;

/**
 * Created by Ljb on 2015/11/24.
 */
public class RegisterHolder extends BaseAHolder<Object> {

    public static final int TYPE_VIEW_WHILE = -1;
    public static final int TYPE_VIEW_REGISTER = 0;

    private View mWhiteView, mResgisterView;

    public RegisterHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_register, null);
        mWhiteView = view.findViewById(R.id.fl_plank);
        mResgisterView = view.findViewById(R.id.ll_register);
        mResgisterView.post(new Runnable() {
            @Override
            public void run() {
                XgoLog.e("mResgisterView::" + mResgisterView.getWidth() + ":" + mResgisterView.getMeasuredHeight());
            }
        });

        //mWhiteView.setLayoutParams(new FrameLayout.LayoutParams(mResgisterView.getMeasuredWidth() , mResgisterView.getMeasuredHeight()));
        return view;
    }


    @Override
    protected void initData(Object data) {

    }

    public void show(int viewType) {
        if (viewType == TYPE_VIEW_WHILE) {
            mWhiteView.setVisibility(View.VISIBLE);
            mResgisterView.setVisibility(View.GONE);
        } else if (viewType == TYPE_VIEW_REGISTER) {
            mWhiteView.setVisibility(View.GONE);
            mResgisterView.setVisibility(View.VISIBLE);
        }
    }
}
