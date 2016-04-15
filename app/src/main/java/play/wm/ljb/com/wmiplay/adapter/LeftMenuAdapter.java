package play.wm.ljb.com.wmiplay.adapter;

import android.support.v4.app.Fragment;

import java.util.List;

import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.holder.LeftMenuItemHolder;
import play.wm.ljb.com.wmiplay.holder.MoreHodler;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.MenuBean;

/**
 * Created by Ljb on 2015/11/10.
 */
public class LeftMenuAdapter extends BaseListFAdapter<MenuBean> {

    public LeftMenuAdapter(Fragment fragment, List<MenuBean> datas) {
        super(fragment, datas);
    }

    @Override
    protected BaseFHolder getFHolder(int position) {
        return new LeftMenuItemHolder(mFragment);
    }

    //无更多数据
    @Override
    protected int hasMoreData() {
        return MoreHodler.NOT_MORE;
    }
}
