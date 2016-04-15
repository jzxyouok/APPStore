package play.wm.ljb.com.wmiplay.fragment.tab;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.AppDetailActivity;
import play.wm.ljb.com.wmiplay.adapter.AppTabAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.manager.ThreadManager;
import play.wm.ljb.com.wmiplay.moudle.AppTabBean;
import play.wm.ljb.com.wmiplay.protocol.AppTabProtocol;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by ljb on 2015/11/2.
 */
public class AppTabFragment extends BaseTabFragment<AppTabBean> implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private SwipeRefreshLayout mSwipe_refresh;
    private  AppTabProtocol mProtocol;;
    private  AppTabAdapter mAdapter;

    @Override
    public View initContentView() {
        View view = View.inflate(getActivity(), R.layout.tab_fragment_app,null);
        mSwipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new AppTabAdapter(this, mData.getList());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        initRefresh();

        return view;
    }

    private void initRefresh() {
        mSwipe_refresh.setColorSchemeResources(R.color.indicatorcolor);
        mSwipe_refresh.setSize(SwipeRefreshLayout.LARGE);
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
        mProtocol = new AppTabProtocol();
        mData = mProtocol.load(0);
        return checkData(mData);
    }

    public PageStateFrameLayout.LoadResult checkData(AppTabBean data) {
        if(data==null){
            return PageStateFrameLayout.LoadResult.error;
        }else if(data.getList()==null||data.getList().size()==0){
            return PageStateFrameLayout.LoadResult.empty;
        }
        return PageStateFrameLayout.LoadResult.success;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        XgoUIUtils.showToast(mData.getList().get(position).getName());
        Intent intent = new Intent(getActivity(), AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.PACKAGENAME, mData.getList().get(position).getPackageName());
        getActivity().startActivity(intent);
    }
}
