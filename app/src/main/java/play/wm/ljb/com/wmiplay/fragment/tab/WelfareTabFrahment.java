package play.wm.ljb.com.wmiplay.fragment.tab;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.adapter.WelfareAdapter;
import com.ljb.game.torn.ClsActivity;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.moudle.WelfareInfo;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by Ljb on 2015/11/11.
 */
public class WelfareTabFrahment extends BaseTabFragment<List<WelfareInfo>> implements AdapterView.OnItemClickListener {

    private  ListView mListView;
    private WelfareAdapter mAdapter;

    @Override
    public View initContentView() {
        View view = View.inflate(getActivity(), R.layout.tab_fragment_welfare, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new WelfareAdapter(this , mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public PageStateFrameLayout.LoadResult load() {
        mData = new ArrayList<>();
        mData.add(new WelfareInfo(R.drawable.cls , getString(R.string. welfare_cls)));
        return  checkData(mData);
    }

    @Override
    public PageStateFrameLayout.LoadResult checkData(List<WelfareInfo> data) {
        if (data == null) {
            return PageStateFrameLayout.LoadResult.error;
        } else if (data.size() == 0) {
            return PageStateFrameLayout.LoadResult.empty;
        } else {
            return PageStateFrameLayout.LoadResult.success;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(getString(R.string.welfare_cls).equals(mData.get(position).getWelfareName())){
            startActivity(new Intent(getActivity() , ClsActivity.class));
        }
    }
}
