package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.WelfareInfo;

/**
 * Created by Ljb on 2015/11/17.
 */
public class WelfareItemHolder extends BaseFHolder<WelfareInfo> {

    private ImageView mImgeView;
    private TextView mTextView;

    public WelfareItemHolder(Fragment mFragment) {
        super(mFragment);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mFragment.getActivity(), R.layout.holder_welfare_item, null);
        mImgeView = (ImageView) view.findViewById(R.id.item_icon);
        mTextView = (TextView) view.findViewById(R.id.item_txt);
        return view;
    }

    @Override
    protected void initData(WelfareInfo data) {
        mImgeView.setImageResource(data.getWelfareImageId());
        mTextView.setText(data.getWelfareName().trim());
    }
}
