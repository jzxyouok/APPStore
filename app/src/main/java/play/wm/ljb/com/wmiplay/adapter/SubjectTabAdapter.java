package play.wm.ljb.com.wmiplay.adapter;

import java.util.List;

import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.holder.SubjectItemHolder;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.SubjectInfo;
import play.wm.ljb.com.wmiplay.moudle.SubjectTabBean;
import play.wm.ljb.com.wmiplay.protocol.SubjectTabProtocol;


/**
 * Created by Ljb on 2015/11/6.
 */
public class SubjectTabAdapter extends BaseListFAdapter<SubjectInfo> {

    public SubjectTabAdapter(BaseTabFragment fragment, List<SubjectInfo> datas) {
        super(fragment, datas);
    }

    @Override
    protected BaseFHolder getFHolder(int position) {
        return new SubjectItemHolder(mFragment);
    }

    @Override
    protected List<SubjectInfo> onLoadMore() {
        SubjectTabBean bean = new SubjectTabProtocol().load(mDatas.size());
        return bean!=null?bean.getList():null;
    }

}
