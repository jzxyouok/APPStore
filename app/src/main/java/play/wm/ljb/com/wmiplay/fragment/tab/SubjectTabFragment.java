package play.wm.ljb.com.wmiplay.fragment.tab;

import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.adapter.SubjectTabAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.manager.ThreadManager;
import play.wm.ljb.com.wmiplay.moudle.SubjectTabBean;
import play.wm.ljb.com.wmiplay.protocol.SubjectTabProtocol;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by ljb on 2015/11/2.
 */
public class SubjectTabFragment extends BaseTabFragment<SubjectTabBean> implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private SwipeRefreshLayout mSwipe_refresh;
    private SubjectTabProtocol mProtocol;
    private  SubjectTabAdapter mAdapter;

    @Override
    public View initContentView() {
        View view = View.inflate(getActivity(), R.layout.tab_fragment_subject, null);
        mListView = (ListView) view.findViewById(R.id.listview);

        mAdapter = new SubjectTabAdapter(this, mData.getList());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mSwipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        initRefresh();

        return view;
    }

    private void initRefresh() {
        mSwipe_refresh.setColorSchemeResources(R.color.indicatorcolor);
        mSwipe_refresh.setSize(SwipeRefreshLayout.LARGE);
//        mSwipe_refresh.setProgressViewEndTarget(true, 100);
        mSwipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ThreadManager.getDefaultManager().getLongThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        mData = mProtocol.load(0);
                        XgoUIUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setData(mData.getList());
                                mAdapter.notifyDataSetChanged();
                                mSwipe_refresh.setRefreshing(false);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public PageStateFrameLayout.LoadResult load() {
        mProtocol = new SubjectTabProtocol();
        mData = mProtocol.load(0);
        return checkData(mData);
    }

    @Override
    public PageStateFrameLayout.LoadResult checkData(SubjectTabBean data) {
        if (data == null) {
            return PageStateFrameLayout.LoadResult.error;
        } else if (data.getList().size() == 0) {
            return PageStateFrameLayout.LoadResult.empty;
        } else {
            return PageStateFrameLayout.LoadResult.success;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        XgoUIUtils.showToast(mData.getList().get(position).getDes());
    }
}
