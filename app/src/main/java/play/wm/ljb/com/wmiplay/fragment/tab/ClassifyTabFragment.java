package play.wm.ljb.com.wmiplay.fragment.tab;

import android.view.View;
import android.widget.ListView;

import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.adapter.ClassifyTabAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.moudle.ClassifyInfo;
import play.wm.ljb.com.wmiplay.protocol.ClassifyTabProtocol;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by ljb on 2015/11/2.
 */
public class ClassifyTabFragment extends BaseTabFragment<List<ClassifyInfo>> {

    private ClassifyTabProtocol mProtocol;
    private ListView mListView;

    @Override
    public View initContentView() {
        View view = View.inflate(getActivity(), R.layout.tab_fragment_classify, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setAdapter(new ClassifyTabAdapter(this,mData) );
        return view;
    }

    @Override
    public PageStateFrameLayout.LoadResult load() {
        mProtocol = new ClassifyTabProtocol();
        mData = mProtocol.load(0);
        return checkData(mData);
    }

    @Override
    public PageStateFrameLayout.LoadResult checkData(List<ClassifyInfo> data) {
        if (data == null) {
            return PageStateFrameLayout.LoadResult.error;
        } else if (data.size() == 0) {
            return PageStateFrameLayout.LoadResult.empty;
        } else {
            return PageStateFrameLayout.LoadResult.success;
        }
    }
}
