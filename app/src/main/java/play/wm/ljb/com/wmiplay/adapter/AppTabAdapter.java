package play.wm.ljb.com.wmiplay.adapter;

import java.util.List;

import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.holder.AppInfoItemHolder;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.moudle.AppTabBean;
import play.wm.ljb.com.wmiplay.protocol.AppTabProtocol;

/**
 * Created by Ljb on 2015/11/9.
 */
public class AppTabAdapter extends BaseListFAdapter<AppInfo> {

    public AppTabAdapter(BaseTabFragment fragment, List<AppInfo> datas) {
        super(fragment, datas);
    }

    @Override
    protected BaseFHolder getFHolder(int position) {
        return new AppInfoItemHolder(mFragment);
    }

    @Override
    protected List<AppInfo> onLoadMore() {
        AppTabBean bean = new AppTabProtocol().load(mDatas.size());
        return  bean!=null?bean.getList():null;
    }

}
