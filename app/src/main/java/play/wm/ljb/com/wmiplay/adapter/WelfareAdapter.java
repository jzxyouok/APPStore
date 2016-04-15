package play.wm.ljb.com.wmiplay.adapter;

import android.support.v4.app.Fragment;

import java.util.List;

import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.holder.MoreHodler;
import play.wm.ljb.com.wmiplay.holder.WelfareItemHolder;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.WelfareInfo;

/**
 * Created by Ljb on 2015/11/17.
 */
public class WelfareAdapter extends BaseListFAdapter<WelfareInfo>{

    public WelfareAdapter(Fragment fragment, List<WelfareInfo> datas) {
        super(fragment, datas);
    }

    @Override
    protected BaseFHolder getFHolder(int position) {
        return new WelfareItemHolder(mFragment);
    }

    @Override
    protected int hasMoreData() {
        return MoreHodler.NOT_MORE;
    }
}
