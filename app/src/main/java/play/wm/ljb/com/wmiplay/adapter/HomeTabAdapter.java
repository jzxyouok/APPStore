package play.wm.ljb.com.wmiplay.adapter;

import java.util.List;

import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.holder.AppInfoItemHolder;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.moudle.HomeTabBean;
import play.wm.ljb.com.wmiplay.protocol.HomeTabProtocol;

/**
 * Created by Ljb on 2015/11/6.
 */
public class HomeTabAdapter extends BaseListFAdapter<AppInfo> {

    public HomeTabAdapter(BaseTabFragment fragment, List<AppInfo> datas) {
        super(fragment, datas);
    }

    @Override
    protected BaseFHolder getFHolder(int position) {
        return new AppInfoItemHolder(mFragment);
    }

    @Override
    protected List<AppInfo> onLoadMore() {
        HomeTabBean bean = new HomeTabProtocol().load(mDatas.size());
        return bean!=null?bean.getList():null;
    }
}
