package play.wm.ljb.com.wmiplay.fragment.tab;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.AppDetailActivity;
import play.wm.ljb.com.wmiplay.adapter.HomeTabAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.holder.ImageRollHolder;
import play.wm.ljb.com.wmiplay.manager.ThreadManager;
import play.wm.ljb.com.wmiplay.moudle.HomeTabBean;
import play.wm.ljb.com.wmiplay.protocol.HomeTabProtocol;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by ljb on 2015/11/2.
 */
public class HomeTabFragment extends BaseTabFragment<HomeTabBean> implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private SwipeRefreshLayout mSwipe_refresh;
    private HomeTabProtocol mProtocol;
    private HomeTabAdapter mHomeTabAdapter;
    private ImageRollHolder headHolder;

    @Override
    public View initContentView() {
        View view = View.inflate(getActivity(), R.layout.tab_fragment_home, null);

        mSwipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        initRefresh();

        mListView = (ListView) view.findViewById(R.id.listview);
        //添加顶部广告滚动条
        headHolder = new ImageRollHolder(this);
        headHolder.setData(mData.getPicture());
        mListView.addHeaderView(headHolder.getView());
        //将HeadHolder需要的数据传入

        mHomeTabAdapter = new HomeTabAdapter(this, mData.getList());
        mListView.setAdapter(mHomeTabAdapter);
        mListView.setOnItemClickListener(this);

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
                                headHolder.setData(mData.getPicture());
                                mHomeTabAdapter.setData(mData.getList());
                                mHomeTabAdapter.notifyDataSetChanged();
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
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("index" , String.valueOf(0));
//        new HomeTabProtocol(HttpUrl.TAB_HOME, HttpRequest.HttpMethod.GET ,params).load();
        mProtocol = new HomeTabProtocol();
        mData = mProtocol.load(0);
        return checkData(mData);
    }

    @Override
    public PageStateFrameLayout.LoadResult checkData(HomeTabBean data) {
        if (data == null) {
            return PageStateFrameLayout.LoadResult.error;
        } else if (data.getList() == null || data.getList().size() == 0) {
            return PageStateFrameLayout.LoadResult.empty;
        } else {
            return PageStateFrameLayout.LoadResult.success;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        XgoUIUtils.showToast(mData.getList().get(position).getName());
        Intent intent = new Intent(getActivity(), AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.PACKAGENAME,mData.getList().get(position).getPackageName());
        getActivity().startActivity(intent);
    }
}
