package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.ClassifyInfo;

/**
 * Created by Ljb on 2015/11/13.
 */
public class ClassifyTitleHolder extends BaseFHolder<ClassifyInfo> {

    private TextView tv_title;

    public ClassifyTitleHolder(Fragment mFragment) {
        super(mFragment);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mFragment.getActivity(), R.layout.holder_classify_title, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    protected void initData(ClassifyInfo data) {
        if(data.isTitle()) {
            tv_title.setText(data.getTitle());
        }
    }
}
